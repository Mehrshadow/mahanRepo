package ir.aspacrm.my.app.mahan.events;

import ir.aspacrm.my.app.mahan.gson.CurrentFeshFesheResponse;

import java.util.List;

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
