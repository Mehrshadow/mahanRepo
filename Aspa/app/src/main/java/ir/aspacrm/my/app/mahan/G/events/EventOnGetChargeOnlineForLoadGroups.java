package ir.aspacrm.my.app.mahan.G.events;


import java.util.List;

import ir.aspacrm.my.app.mahan.G.gson.ChargeOnlineGroup;

public class EventOnGetChargeOnlineForLoadGroups {
    List<ChargeOnlineGroup> chargeOnlineGroups;
    public EventOnGetChargeOnlineForLoadGroups(List<ChargeOnlineGroup> chargeOnlineGroups) {
        this.chargeOnlineGroups = chargeOnlineGroups;
    }
    public List<ChargeOnlineGroup> getChargeOnlineGroups() {
        return chargeOnlineGroups;
    }
}
