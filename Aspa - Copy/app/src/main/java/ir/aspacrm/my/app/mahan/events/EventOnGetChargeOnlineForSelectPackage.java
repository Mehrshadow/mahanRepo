package ir.aspacrm.my.app.mahan.events;

/**
 * Created by Microsoft on 3/29/2016.
 */
public class EventOnGetChargeOnlineForSelectPackage {
    boolean result;
    String message = "";
    long factorCode;
    public EventOnGetChargeOnlineForSelectPackage(boolean result, String message, long factorCode) {
        this.result = result;
        this.message = message;
        this.factorCode = factorCode;
    }
    public long getFactorCode() {
        return factorCode;
    }
    public String getMessage() {
        return message;
    }
    public boolean isResult() {
        return result;
    }
}
