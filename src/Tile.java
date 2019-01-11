public class Tile {
    private int temperature = -5;
    private boolean isPrimed = false;

    public Tile() {
        this(-5);
    }

    public Tile(int temperature) {
        this.temperature = temperature;
    }

    /* methods */
    public void increaseTemperature() {
        if (temperature <= 0)
            temperature += 1;
        else
            temperature = 1;
    }

    public void decreaseTemperature() {
        if (temperature > -10)
            temperature -= 1;
        else
            temperature = -10;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public boolean isPrimed() {
        return isPrimed;
    }

    public void setPrimed(boolean isPrimed) {
        this.isPrimed = isPrimed;
    }

    @Override
    public String toString() {
        return "[" + temperature + "]";
    }
}
