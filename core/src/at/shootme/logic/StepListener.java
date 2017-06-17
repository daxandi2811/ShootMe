package at.shootme.logic;

/**
 * Created by Nicole on 28.05.2017.
 */
public interface StepListener {
    default void beforeStep(float timeStep) {
    }

    default void beforeWorldStep(float timeStep) {
    }

    default void afterWorldStep(float timeStep) {
    }

    default void cleanup() {
    }
}
