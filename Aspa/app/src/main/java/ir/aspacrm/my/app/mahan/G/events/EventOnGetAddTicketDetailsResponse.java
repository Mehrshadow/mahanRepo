package ir.aspacrm.my.app.mahan.G.events;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetAddTicketDetailsResponse {
    boolean status;
    String message;
    public EventOnGetAddTicketDetailsResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public boolean getStatus(){
        return status;
    }
}
