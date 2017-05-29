package at.shootme.util.vectors;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Nicole on 07.05.2017.
 */
public class Vector2UtilTest {

    @Test
    public void getAngleFromAToB_1() throws Exception {
        Vector2 a = new Vector2(10, 10);
        Vector2 b = new Vector2(20, 20);
        assertThat(Vector2Util.getAngleFromAToB(a, b), is(45.0f));
    }

    @Test
    public void getAngleFromAToB_2() throws Exception {
        Vector2 a = new Vector2(10, 10);
        Vector2 b = new Vector2(-10, 10);
        assertThat(Vector2Util.getAngleFromAToB(a, b), is(180f));
    }
}