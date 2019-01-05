package ndw.eugene.model;

public class Statistics {

    private int counter;

    public Statistics() {
        counter = 0;
    }

    public int getCounter() {
        return counter;
    }

    public void increase(){
        counter++;
    }

    @Override
    public String toString() {
        return "counter = " + counter;
    }

}
