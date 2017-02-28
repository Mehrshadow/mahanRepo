package ir.aspacrm.my.app.mahan.events;

import ir.aspacrm.my.app.mahan.gson.SelectFactorResponse;

/**
 * Created by Microsoft on 3/29/2016.
 */
public class EventOnGetSelectFactorResponse {
    SelectFactorResponse selectFactorResponse;
    public EventOnGetSelectFactorResponse(SelectFactorResponse selectFactorResponse) {
        this.selectFactorResponse = selectFactorResponse;
    }
    public SelectFactorResponse getSelectFactorResponse() {
        return selectFactorResponse;
    }
}
