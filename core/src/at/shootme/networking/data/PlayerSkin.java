package at.shootme.networking.data;

/**
 * Created by Nicole on 18.06.2017.
 */
public enum PlayerSkin {
    PLAYER_1("assets/playersprite1.png"),
    PLAYER_2("assets/playersprite2.png"),
    PLAYER_3("assets/playersprite3.png"),
    PLAYER_4("assets/playersprite4.png"),
    ;

    private String textureFilePath;

    PlayerSkin(String textureFilePath) {
        this.textureFilePath = textureFilePath;
    }

    public String getTextureFilePath() {
        return textureFilePath;
    }
}
