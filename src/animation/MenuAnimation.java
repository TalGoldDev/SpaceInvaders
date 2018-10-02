package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.Task;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuAnimation<T> implements Menu<T> {

    // the variables
    private HashMap<String, Task<Void>> hashMap = new HashMap<String, Task<Void>>();
    private List<String> name = new ArrayList<String>();
    private List<String> keyList = new ArrayList<String>();
    private KeyboardSensor keypressed;
    private HashMap<String, Menu<Task<Void>>> hashMapMenu = new HashMap<String, Menu<Task<Void>>>();
    private AnimationRunner runner;
    private String title;

    /**
     * the class constructor function.
     *
     * @param k - the key that represents the screen.
     * @param r - the animation Runner.
     * @param t - the title.
     */
    public MenuAnimation(String t, KeyboardSensor k, AnimationRunner r) {
        this.title = t;
        this.runner = r;
        this.keypressed = k;
    }

    /**
     * the function runs one frames and draws it to the scree.
     * @param d - the Surface to draw on.
     * @param dt - the time passed since last frame.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.BLUE);
        d.drawText(275, 60, title, 50);
        d.setColor(Color.BLACK);
        d.drawText(170, 85, "Please choose one of the Following options:", 25);

        d.setColor(Color.RED);
        for (int i = 0; i < keyList.size(); i++) {
            d.drawText(190, (i * 50) + 130, '(' + keyList.get(i) + ')', 25);
            d.drawText(230, (i * 50) + 130, name.get(i), 25);
        }
    }

    /**
     * the method checks if the animation should stop.
     * @return - true or false.
     */
    @Override
    public boolean shouldStop() {
        for (String string : keyList) {
            if (keypressed.isPressed(string)) {
                return true;
            }
        }
        return false;
    }

    /**
     * the method adds a task to the menu.
     * @param key - the key we are lookin for.
     * @param message - to display.
     * @param returnVal - the return value.
     */
    @Override
    public void addSelection(String key, String message, Task<Void> returnVal) {
        hashMap.put(key, returnVal);
        name.add(message);
        keyList.add(key);
    }

    /**
     * the function checks for the status of the task.
     * @return - null.
     */
    @Override
    public Task<Void> getStatus() {

        for (String key : keyList) {
            if (keypressed.isPressed(key)) {
                if (hashMap.containsKey(key)) {
                    return hashMap.get(key);
                } else if (hashMapMenu.containsKey(key)) {
                    runner.run(hashMapMenu.get(key));
                    return hashMapMenu.get(key).getStatus();
                }
            }
        }
        return null;
    }

    /**
     * adding a sub menu to the current menu.
     * @param key - the key we are checking for.
     * @param message - the message we want to display.
     * @param subMenu - the sub menu to insert.
     */
    @Override
    public void addSubMenu(String key, String message, Menu<Task<Void>> subMenu) {
        hashMapMenu.put(key, subMenu);
        name.add(message);
        keyList.add(key);
    }

}
