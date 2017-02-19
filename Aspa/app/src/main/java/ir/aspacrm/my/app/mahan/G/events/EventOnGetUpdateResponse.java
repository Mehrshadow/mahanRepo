package ir.aspacrm.my.app.mahan.G.events;


import ir.aspacrm.my.app.mahan.G.gson.GetUpdateResponse;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetUpdateResponse {
    GetUpdateResponse updateResponse;
    public EventOnGetUpdateResponse(GetUpdateResponse getUpdateResponse) {
        this.updateResponse = getUpdateResponse;
    }
    public GetUpdateResponse getUpdateResponse() {
        return updateResponse;
    }
}
