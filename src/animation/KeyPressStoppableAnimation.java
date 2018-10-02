package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

public class KeyPressStoppableAnimation implements Animation {

    // the variables
    private KeyboardSensor keyBoardSensor;
    private String stopKey;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;


    /**
     * the constructor method of the class.
     * @param sensor - the keyboard sensor.
     * @param key - the key we are looking for.
     * @param animation - the animation we want to run.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.keyBoardSensor = sensor;
        this.animation = animation;
        this.stopKey = key;
        this.isAlreadyPressed = true;
    }



    /**
     * the function draws one frame onto the drawing surface.
     * the function waits for input "space key" to continue.
     * @param d - the drawing surface.
     * @param dt - the time passed since last frame.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        //running one frame of the animation.
        this.animation.doOneFrame(d, dt);

        //checking if the key we are looking for got pressed.
        if (this.keyBoardSensor.isPressed(this.stopKey) && !this.isAlreadyPressed) {
            this.stop = true;
        }

        //making sure the key isn't already pressed from the animation before.
        if (!this.keyBoardSensor.isPressed(this.stopKey)) {
            this.isAlreadyPressed = false;
        }
    }

    /**
     * the function checks if the animation should stop.
     * @return - false.
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}
