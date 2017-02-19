package ir.aspacrm.my.app.mahan.G.events;

/**
 * Created by Microsoft on 3/3/2016.
 */
public class EventOnGetErrorGetNotifies {
    int errorType;
    public EventOnGetErrorGetNotifies(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
