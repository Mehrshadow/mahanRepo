package ir.aspacrm.my.app.mahan.G.events;

public class EventOnClickedChargeOnlineGroup {
    long groupCode;
    int isClub;
    int whichMenuItem;
    public EventOnClickedChargeOnlineGroup(long groupCode, int isClub, int whichMenuItem) {
        this.groupCode = groupCode;
        this.isClub = isClub;
        this.whichMenuItem = whichMenuItem;
    }
    public int getWhichMenuItem() {
        return whichMenuItem;
    }
    public int getIsClub() {
        return isClub;
    }
    public long getGroupCode() {
        return groupCode;
    }
}
