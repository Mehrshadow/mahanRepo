package ir.aspacrm.my.app.mahan.events;

public class EventOnGetErrorGetBankList {
    int errorType;
    public EventOnGetErrorGetBankList(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
