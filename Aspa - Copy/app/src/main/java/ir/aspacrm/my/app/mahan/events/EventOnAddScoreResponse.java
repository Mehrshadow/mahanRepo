package ir.aspacrm.my.app.mahan.events;

import ir.aspacrm.my.app.mahan.gson.AddScoreResponse;

/**
 * Created by HaMiD on 3/6/2017.
 */

public class EventOnAddScoreResponse {
    AddScoreResponse response;

    public EventOnAddScoreResponse(AddScoreResponse response) {
        this.response = response;
    }

    public AddScoreResponse getResponse() {
        return response;
    }
}
