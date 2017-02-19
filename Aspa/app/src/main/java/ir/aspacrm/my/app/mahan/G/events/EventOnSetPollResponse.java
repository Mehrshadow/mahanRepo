package ir.aspacrm.my.app.mahan.G.events;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnSetPollResponse {
    boolean status;
    public EventOnSetPollResponse(boolean status) {
        this.status = status;
    }
    public boolean getStatus(){
        return status;
    }
}
