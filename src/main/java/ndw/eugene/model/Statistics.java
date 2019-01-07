package ndw.eugene.model;

public class Statistics {

    private int viewsCounter;

    public Statistics() {
        viewsCounter = 0;
    }

    public int getViewsCounter() {
        return viewsCounter;
    }

    public void increaseViewCounter(){
        viewsCounter++;
    }
}
