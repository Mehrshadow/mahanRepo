package ir.aspacrm.my.app.mahan.events;

/**
 * Created by Microsoft on 3/10/2016.
 */
public class EventOnChangePasswordResponse {
    boolean status;
    String Message = "";

    public EventOnChangePasswordResponse(boolean status, String message) {
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
