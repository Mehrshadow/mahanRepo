package ir.aspacrm.my.app.mahan.events;

import ir.aspacrm.my.app.mahan.gson.SearchISPResponse;

import java.util.List;

/**
 * Created by Microsoft on 3/2/2016.
 */
public class EventOnGetIspListResponse {
    List<SearchISPResponse> isps;
    public EventOnGetIspListResponse(List<SearchISPResponse> isps) {
        this.isps = isps;
    }
    public List<SearchISPResponse> getIsps() {
        return isps;
    }
}
