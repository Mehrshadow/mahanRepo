package ir.aspacrm.my.app.mahan.events;

import ir.aspacrm.my.app.mahan.gson.AddScoreResponse;
import ir.aspacrm.my.app.mahan.model.Locations;

/**
 * Created by HaMiD on 3/6/2017.
 */

public class EventOnAddScoreResponse {
    AddScoreResponse response;
    Locations locations;

    public EventOnAddScoreResponse(AddScoreResponse response,Locations locations) {
        this.response = response;
        this.locations = locations;
    }

    public AddScoreResponse getResponse() {
        return response;
    }

    public Locations getLocations() {
        return locations;
    }
}
