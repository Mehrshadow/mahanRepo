package ir.aspacrm.my.app.mahan.events;

import ir.aspacrm.my.app.mahan.gson.GetUnitsResponse;

import java.util.List;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetUnitResponse {
    List<GetUnitsResponse> units;
    public EventOnGetUnitResponse(List<GetUnitsResponse> units) {
        this.units = units;
    }
    public List<GetUnitsResponse> getUnits() {
        return units;
    }
}
