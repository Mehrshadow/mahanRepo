package ir.aspacrm.my.app.mahan.G.events;

public class EventOnGetErrorPayFactorFromCredit {
    int errorType;
    public EventOnGetErrorPayFactorFromCredit(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
