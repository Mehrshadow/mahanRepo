package ir.aspacrm.my.app.mahan.G.events;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetAddTicketResponse {
    boolean status;
    long code;
    public EventOnGetAddTicketResponse(boolean status, long code) {
        this.status = status;
        this.code = code;
    }
    public long getCode() {
        return code;
    }
    public boolean getStatus(){
        return status;
    }
}
