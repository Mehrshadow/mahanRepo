package ir.aspacrm.my.app.mahan.G.events;


import java.util.List;

import ir.aspacrm.my.app.mahan.G.gson.CurrentFeshFesheResponse;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetCurrentFeshFesheResponse {
    List<CurrentFeshFesheResponse> currentFeshFesheResponse;
    public EventOnGetCurrentFeshFesheResponse(List<CurrentFeshFesheResponse> currentFeshFesheResponses) {
        this.currentFeshFesheResponse = currentFeshFesheResponses;
    }
    public List<CurrentFeshFesheResponse> getCurrentFeshFesheResponse() {
        return currentFeshFesheResponse;
    }
}
