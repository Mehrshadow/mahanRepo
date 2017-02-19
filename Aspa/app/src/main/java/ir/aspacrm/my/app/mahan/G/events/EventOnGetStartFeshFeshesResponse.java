package ir.aspacrm.my.app.mahan.G.events;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetStartFeshFeshesResponse {
    boolean status;
    String message = "";
    public EventOnGetStartFeshFeshesResponse(boolean status, String message) {
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
