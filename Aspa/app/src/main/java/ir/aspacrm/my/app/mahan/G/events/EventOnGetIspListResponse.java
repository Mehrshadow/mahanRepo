package ir.aspacrm.my.app.mahan.G.events;


import java.util.List;

import ir.aspacrm.my.app.mahan.G.gson.SearchISPResponse;

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
