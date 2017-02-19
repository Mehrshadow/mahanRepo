package ir.aspacrm.my.app.mahan.G.events;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetStartFactorResponse {
    boolean status;
    String Message = "";

    public EventOnGetStartFactorResponse(boolean status, String message) {
        this.status = status;
        Message = message;
    }
    public boolean getStatus(){
        return status;
    }
    public String getMessage() {
        return Message;
    }
}
