package ir.aspacrm.my.app.mahan.events;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetVisitMobileResponse {
    boolean status;
    public EventOnGetVisitMobileResponse(boolean status) {
        this.status = status;
    }
    public boolean getStatus(){
        return status;
    }
}
