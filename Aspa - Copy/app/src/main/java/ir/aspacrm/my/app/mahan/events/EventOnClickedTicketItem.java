package ir.aspacrm.my.app.mahan.events;

/**
 * Created by Microsoft on 3/17/2016.
 */
public class EventOnClickedTicketItem {
    long code;
    public EventOnClickedTicketItem(long code) {
        this.code = code;
    }
    public long getCode() {
        return code;
    }
}
