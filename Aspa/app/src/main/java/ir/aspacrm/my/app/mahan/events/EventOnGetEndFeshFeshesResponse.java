package ir.aspacrm.my.app.mahan.events;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetEndFeshFeshesResponse {
    boolean status;
    String message = "";
    public EventOnGetEndFeshFeshesResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
    public boolean getStatus(){
        return status;
    }
    public String getMessage() {
        return message;
    }
}
