package ir.aspacrm.my.app.mahan.events;

import ir.aspacrm.my.app.mahan.gson.ChargeOnlineGroupPackage;

import java.util.List;

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
