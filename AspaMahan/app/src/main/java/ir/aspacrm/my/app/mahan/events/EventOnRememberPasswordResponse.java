package ir.aspacrm.my.app.mahan.events;

/**
 * Created by Microsoft on 3/10/2016.
 */
public class EventOnRememberPasswordResponse {
    boolean status;
    String Message = "";

    public EventOnRememberPasswordResponse(boolean status, String message) {
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
