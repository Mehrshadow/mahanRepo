package ir.aspacrm.my.app.mahan.G.events;


import java.util.List;

import ir.aspacrm.my.app.mahan.G.gson.GetAdvsResponse;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetAdvsResponse {
    List<GetAdvsResponse> advsResponse;
    public EventOnGetAdvsResponse(List<GetAdvsResponse> getAdvsResponse) {
        this.advsResponse = getAdvsResponse;
    }
    public List<GetAdvsResponse> getAdvsResponse() {
        return advsResponse;
    }
}
