package ir.aspacrm.my.app.mahan.G.events;

/**
 * Created by Microsoft on 3/5/2016.
 */
public class EventOnGetErrorGetNews {
    int errorType;
    public EventOnGetErrorGetNews(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
