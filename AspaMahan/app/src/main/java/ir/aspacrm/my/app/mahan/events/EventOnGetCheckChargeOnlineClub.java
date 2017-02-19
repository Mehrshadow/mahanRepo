package ir.aspacrm.my.app.mahan.events;

/**
 * Created by Microsoft on 3/28/2016.
 */
public class EventOnGetCheckChargeOnlineClub {
    boolean status;
    boolean isClub;
    int whichMenuItem;
    public EventOnGetCheckChargeOnlineClub(boolean status, boolean isClub,int whichMenuItem) {
        this.status = status;
        this.isClub = isClub;
        this.whichMenuItem = whichMenuItem;
    }
    public boolean getStatus(){
        return status;
    }
    public boolean getIsClub(){
        return isClub;
    }
    public int getWhichMenuItem() {
        return whichMenuItem;
    }
}
