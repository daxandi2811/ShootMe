package at.shootme.entity.player;

import at.shootme.SM;
import at.shootme.ShootMeConstants;
import at.shootme.beans.HorizontalMovementState;
import at.shootme.beans.ViewDirection;
import at.shootme.entity.EntityCategory;
import at.shootme.entity.general.SimpleDrawableEntity;
import at.shootme.entity.level.Platform;
import at.shootme.entity.pickups.PickupType;
import at.shootme.entity.shot.StandardShot;
import at.shootme.networking.data.entity.EntityCreationMessage;
import at.shootme.state.data.GameMode;
import at.shootme.util.vectors.Vector2Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.TemporalUnit;

import static at.shootme.ShootMeConstants.PIXELS_TO_METERS;

/**
 * Created by Alexander Dietrich on 01.05.2017.
 */
public class Player extends SimpleDrawableEntity {

    private static final int JUMP_SPEED = 30;
    public static final int AVAILABLE_LIVES = 3;

    private HorizontalMovementState horizontalMovementState = HorizontalMovementState.STOPPED;
    private ViewDirection viewDirection = ViewDirection.LEFT;

    private String texturepath;

    private Texture left;
    private Texture right;
    private Texture rightWalk;
    private Texture leftWalk;
    private LocalTime changeTime = LocalTime.now();

    private int maxJumps = 2;
    private int availableJumps = maxJumps;
    private int score = 0;
    private int health = 100;
    private float speed = 1;
    private String name;
    private float lastDeathTimeInGameSeconds;
    private float lastStatsUpAcquiredInGameSeconds;
    private Vector2 size;
    private PickupType currentPickup;
    private float lastTimeShot;
    private int remainingLives = AVAILABLE_LIVES;

    private Sound jumpSound = Gdx.audio.newSound(Gdx.files.internal("assets/jump.wav"));

    public Player() {
    }

    public void init(Vector2 position, World world) {
        left = SM.textureStore.getOrLoadTexture(Gdx.files.internal("assets/playersprite" + texturepath + "_left.png").path());
        right = SM.textureStore.getOrLoadTexture(Gdx.files.internal("assets/playersprite" + texturepath + "_right.png").path());
        rightWalk = SM.textureStore.getOrLoadTexture(Gdx.files.internal("assets/playersprite" + texturepath + "_right_walk.png").path());
        leftWalk = SM.textureStore.getOrLoadTexture(Gdx.files.internal("assets/playersprite" + texturepath + "_left_walk.png").path());

        sprite = new Sprite(left);

        sprite.setSize(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setOriginCenter();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearDamping = 3f;
        bodyDef.position.set(position);

        body = world.createBody(bodyDef);
        body.setUserData(this);
        body.setFixedRotation(true);

        PolygonShape shape = new PolygonShape();

        //The minus 3 makes the polygon slightly smaller than the sprite so there are no visible gaps between the world and the player
        float width = (sprite.getWidth() - 3) * PIXELS_TO_METERS;
        float height = (sprite.getHeight() - 3) * PIXELS_TO_METERS;
        shape.setAsBox(width / 2, height / 2);
        this.size = new Vector2(width, height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.6f;
        fixtureDef.density = 1000f;

        fixture = body.createFixture(fixtureDef);
        shape.dispose();
    }

    public HorizontalMovementState getHorizontalMovementState() {
        return horizontalMovementState;
    }

    public void setHorizontalMovementState(HorizontalMovementState horizontalMovementState) {
        if (isDead()) {
            horizontalMovementState = HorizontalMovementState.STOPPING;
        }
        this.horizontalMovementState = horizontalMovementState;
    }

    /**
     * This method is called during rendering to move the sprite to the new position determined by the physics engine
     */
    public void move() {
        Vector2 velocity = body.getLinearVelocity();

        //Calculating horizontal movement
        if (horizontalMovementState == HorizontalMovementState.STOPPING && Math.abs(velocity.x) < 0.001f) {
            horizontalMovementState = HorizontalMovementState.STOPPED;
        }
        float desiredHorizontalVelocity = 0;
        switch (horizontalMovementState) {
            case LEFT:
                //desiredHorizontalVelocity = Math.max(velocity.x - 5f, -14f);
                desiredHorizontalVelocity = Math.max(velocity.x - 5f * speed, -14f * speed);
                if (viewDirection != ViewDirection.LEFT) {
                    viewDirection = ViewDirection.LEFT;
                    sprite.setTexture(left);
                }
                if (Duration.between(changeTime, LocalTime.now()).getNano() / 1000000 >= 150) {
                    if (sprite.getTexture() == left) {
                        sprite.setTexture(leftWalk);
                    } else {
                        sprite.setTexture(left);
                    }
                    changeTime = LocalTime.now();
                }
                break;
            case STOPPING:
                desiredHorizontalVelocity = velocity.x;
                break;
            case RIGHT:
                desiredHorizontalVelocity = Math.min(velocity.x + 5f * speed, 14f * speed);
                if (viewDirection != ViewDirection.RIGHT) {
                    viewDirection = ViewDirection.RIGHT;
                    sprite.setTexture(right);
                }
                if (Duration.between(changeTime, LocalTime.now()).getNano() / 1000000 >= 150) {
                    if (sprite.getTexture() == right) {
                        sprite.setTexture(rightWalk);
                    } else {
                        sprite.setTexture(right);
                    }
                    changeTime = LocalTime.now();
                }
                break;
            case STOPPED:
                desiredHorizontalVelocity = 0;
                if (horizontalMovementState == HorizontalMovementState.LEFT) {
                    sprite.setTexture(left);
                } else if (horizontalMovementState == HorizontalMovementState.RIGHT) {
                    sprite.setTexture(right);
                }
        }

        float horizontalVelocityChange = desiredHorizontalVelocity - velocity.x;
        float horizontalForce = body.getMass() * horizontalVelocityChange;

        body.applyLinearImpulse(new Vector2(horizontalForce, 0), body.getWorldCenter(), true);
//        System.out.println(body.getLinearVelocity().x + "  " + body.getLinearVelocity().y);
    }

    public StandardShot shootAt(Vector2 clickPosition) {
        if (isDead()) {
            return null;
        }
        if (lastTimeShot + 0.2 > SM.gameScreen.getGameDurationSeconds()) {
            return null;
        }
        lastTimeShot = SM.gameScreen.getGameDurationSeconds();

        Vector2 playerPosition = body.getPosition();

        float angle = Vector2Util.getAngleFromAToB(playerPosition, clickPosition);
        Vector2 directionVector = Vector2Util.degreeToVector2(angle);

        float distance = playerPosition.dst(clickPosition);
        int initialShotSpeed = (int) (Math.min(Math.max(distance * 8.5, 18), 65.0));
        System.out.println(initialShotSpeed);
        Vector2 initialShotVelocity = directionVector.scl(initialShotSpeed);

        StandardShot shot = new StandardShot(playerPosition, initialShotVelocity, this, getWorld());
//        System.out.println("playerPosition: "+ playerPosition + " --- " + "clickPosition: "+ clickPosition + " --- " + "initialShotVelocity: "+ initialShotVelocity);
//        System.out.println("directionVector: "+ directionVector + " --- " + "angle: "+ angle + " --- ");
        return shot;
    }

    public void receiveScore(int score) {
        this.score += score;
    }

    public void receiveDamage(int damage) {
        if (isDead()) {
            return; // ignore, already ded
        }
        this.health = Math.max(health - damage, 0);
        if (isDead()) {
            onDeath();
        }
    }

    private void onDeath() {
        lastDeathTimeInGameSeconds = SM.gameScreen.getGameDurationSeconds();
        setHorizontalMovementState(HorizontalMovementState.STOPPING);
        if (SM.state.getGameMode() == GameMode.LAST_MAN_STANDING) {
            if (remainingLives > 0) {
                remainingLives--;
            }
        }
    }

    public boolean isDead() {
        return health == 0;
    }

    private World getWorld() {
        return body.getWorld();
    }

    /**
     * see, if double jump is available
     */
    public void jumpIfPossible() {
        if (!isDead()) {
            if (availableJumps > 0) {
                jump();
            }
        }
    }

    /**
     * jump method
     */
    private void jump() {
        availableJumps--;
        jumpSound.play(0.75f);
        body.setLinearVelocity(body.getLinearVelocity().x, JUMP_SPEED);
    }

    public void resetAllStats() {
        maxJumps = 2;
        speed = 1;
    }

    /**
     * resets double jump when touching a ground-platform
     *
     * @param platform
     */
    public void hitGround(Platform platform) {
        availableJumps = maxJumps;
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setRotation(isDead() ? -90 : 0);
        float verticalShift = isDead() ? -sprite.getHeight() / 3 : 0;
        sprite.setPosition(body.getPosition().x * ShootMeConstants.METERS_TO_PIXELS - sprite.getWidth() / 2, body.getPosition().y * ShootMeConstants.METERS_TO_PIXELS - sprite.getHeight() / 2 + verticalShift);
        sprite.draw(batch);
        BitmapFont textFont = SM.gameScreen.getMediumFont();
        Color previousColor = textFont.getColor();
        Color color = Color.GREEN;
        if (health <= 70) {
            color = Color.ORANGE;
        }
        if (health <= 40) {
            color = Color.RED;
        }
        textFont.setColor(color);
        int horizontalShiftDueToTextSize = 4 * name.length();
        textFont.draw(batch, name + " - " + health, sprite.getX() + sprite.getWidth() / 2 - 50 - horizontalShiftDueToTextSize, sprite.getY() + sprite.getHeight() + 20);
        textFont.setColor(previousColor);
    }

    @Override
    public EntityCategory getCategory() {
        return EntityCategory.PLAYER;
    }

    @Override
    public Body getBody() {
        return body;
    }

    public float getLastStatsUpAcquiredInGameSeconds() {
        return lastStatsUpAcquiredInGameSeconds;
    }

    public void setLastStatsUpAcquiredInGameSeconds(float lastStatsUpAcquiredInGameSeconds) {
        this.lastStatsUpAcquiredInGameSeconds = lastStatsUpAcquiredInGameSeconds;
    }

    public int getMaxJumps() {
        return maxJumps;
    }

    public void setMaxJumps(int maxJumps) {
        this.maxJumps = maxJumps;
    }

    public int getAvailableJumps() {
        return availableJumps;
    }

    public void setAvailableJumps(int availableJumps) {
        this.availableJumps = availableJumps;
    }

    public String getTexturepath() {
        return texturepath;
    }

    public void setTexturepath(String texturepath) {
        this.texturepath = texturepath;
    }

    public ViewDirection getViewDirection() {
        return viewDirection;
    }

    public void setViewDirection(ViewDirection viewDirection) {
        this.viewDirection = viewDirection;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getLastDeathTimeInGameSeconds() {
        return lastDeathTimeInGameSeconds;
    }

    public Vector2 getSize() {
        return size;
    }

    public PickupType getCurrentPickup() {
        return currentPickup;
    }

    public void setCurrentPickup(PickupType currentPickup) {
        this.currentPickup = currentPickup;
    }

    public int getRemainingLives() {
        return remainingLives;
    }

    public void setRemainingLives(int remainingLives) {
        this.remainingLives = remainingLives;
    }

    public static class PlayerCreationMessage extends EntityCreationMessage {

        private int availableJumps;
        private String name;
        private String texturepath;
        private int score;
        private int health;

        public int getAvailableJumps() {
            return availableJumps;
        }

        public void setAvailableJumps(int availableJumps) {
            this.availableJumps = availableJumps;
        }

        public String getTexturepath() {
            return texturepath;
        }

        public void setTexturepath(String texturepath) {
            this.texturepath = texturepath;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getHealth() {
            return health;
        }

        public void setHealth(int health) {
            this.health = health;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        jumpSound.dispose();
        super.finalize();
    }
}
