package at.shootme.logic;

/**
 * Created by Nicole on 28.05.2017.
 */
public interface StepListener {
    /**
     * called at the start of the step
     * @param timeStep
     */
    default void beforeStep(float timeStep) {
    }

    /**
     * called before the physics processing
     * @param timeStep
     */
    default void beforeWorldStep(float timeStep) {
    }

    /**
     * called after the physics processing
     * @param timeStep
     */
    default void afterWorldStep(float timeStep) {
    }

    /**
     * called at the end of the step, only for cleanup purposes
     */
    default void cleanup() {
    }
}
