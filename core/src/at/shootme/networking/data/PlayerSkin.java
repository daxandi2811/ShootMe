package at.shootme.networking.data;

/**
 * Created by Nicole on 18.06.2017.
 */
public enum PlayerSkin {
    PLAYER_1("1"),
    PLAYER_2("2"),
    PLAYER_3("3"),
    PLAYER_4("4");

    private String textureFilePath;

    PlayerSkin(String textureFilePath) {
        this.textureFilePath = textureFilePath;
    }

    public String getTextureFilePath() {
        return textureFilePath;
    }
}
