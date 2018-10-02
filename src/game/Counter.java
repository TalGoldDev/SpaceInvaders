package game;

public class Counter {
    //the variables.
    private int value;

    /**
     * the constructor.
     * @param value - the new value of the counter.
     */
    public Counter(int value) {
        this.value = value;
    }

    /**
     * add number to current count.
     * @param number - the number we want to add.
     */
    public void increase(int number) {
        this.value += number;
    }

    /**
     * subtract number from current count.
     * @param number - the number we want to subtract.
     */
    public void decrease(int number) {
        this.value -= number;
    }

    /**
     * get current count.
     * @return - the value of the counter.
     */
    public int getValue() {
        return this.value;
    }
}