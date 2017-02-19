package ir.aspacrm.my.app.mahan.G.events;

/**
 * Created by Microsoft on 4/24/2016.
 */
public class EventOnGetErrorGetCityGroups {

    int errorType;
    public EventOnGetErrorGetCityGroups(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
