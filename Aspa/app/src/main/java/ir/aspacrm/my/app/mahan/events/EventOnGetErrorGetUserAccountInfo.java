package ir.aspacrm.my.app.mahan.events;
public class EventOnGetErrorGetUserAccountInfo {
    int errorType;
    public EventOnGetErrorGetUserAccountInfo(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
