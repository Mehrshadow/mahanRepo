package ir.aspacrm.my.app.mahan.G.events;


import java.util.List;

import ir.aspacrm.my.app.mahan.G.gson.ChargeOnlineGroupPackage;

/**
 * Created by Microsoft on 3/30/2016.
 */
public class EventOnGetChargeOnlineForLoadPackages {
    List<ChargeOnlineGroupPackage> chargeOnlineGroupPackages;
    public EventOnGetChargeOnlineForLoadPackages(List<ChargeOnlineGroupPackage> chargeOnlineGroupPackages) {
        this.chargeOnlineGroupPackages = chargeOnlineGroupPackages;
    }
    public List<ChargeOnlineGroupPackage> getChargeOnlineGroupPackages() {
        return chargeOnlineGroupPackages;
    }
}
