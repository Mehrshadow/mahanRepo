package ir.aspacrm.my.app.mahan.events;

import ir.aspacrm.my.app.mahan.gson.ChargeOnlineGroup;

import java.util.List;

public class EventOnGetChargeOnlineForLoadGroups {
    List<ChargeOnlineGroup> chargeOnlineGroups;
    public EventOnGetChargeOnlineForLoadGroups(List<ChargeOnlineGroup> chargeOnlineGroups) {
        this.chargeOnlineGroups = chargeOnlineGroups;
    }
    public List<ChargeOnlineGroup> getChargeOnlineGroups() {
        return chargeOnlineGroups;
    }
}
