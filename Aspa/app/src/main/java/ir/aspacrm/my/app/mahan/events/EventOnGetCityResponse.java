package ir.aspacrm.my.app.mahan.events;

import ir.aspacrm.my.app.mahan.gson.CityResponse;

/**
 * Created by Microsoft on 4/24/2016.
 */
public class EventOnGetCityResponse {
    CityResponse[] cityResponses;
    public EventOnGetCityResponse(CityResponse[] cityResponses) {
        this.cityResponses = cityResponses;
    }
    public CityResponse[] getCityResponses() {
        return cityResponses;
    }
}
