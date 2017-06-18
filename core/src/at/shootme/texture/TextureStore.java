package at.shootme.texture;

import at.shootme.SM;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nicole on 17.06.2017.
 */
public class TextureStore {

    private Map<String, Texture> textureByFilePath = new HashMap<>();

    public TextureStore() {
    }

    public Texture getOrLoadTexture(String filePath) {
        Texture texture = getTextureInternal(filePath);
        if (texture != null) {
            return texture;
        }
        return load(filePath);
    }

    /**
     * @param filePath
     * @return
     * @throws IllegalArgumentException if texture is not loaded
     */
    public Texture getTexture(String filePath) {
        Texture texture = getTextureInternal(filePath);
        if (texture == null) {
            throw new IllegalArgumentException("texture " + filePath + " not yet loaded");
        }
        return texture;
    }

    private Texture getTextureInternal(String filePath) {
        return textureByFilePath.get(filePath);
    }

    public Texture load(String filePath) {
        Texture texture = new Texture(filePath);
        textureByFilePath.put(filePath, texture);
        return texture;
    }
}
