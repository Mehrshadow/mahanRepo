package ir.aspacrm.my.app.mahan.G.events;


import java.util.List;

import ir.aspacrm.my.app.mahan.G.gson.NotifyResponse;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetNotifiesResponse {
    List<NotifyResponse> notifyResponse;
    public EventOnGetNotifiesResponse(List<NotifyResponse> notifyResponse) {
        this.notifyResponse = notifyResponse;
    }
    public List<NotifyResponse> getNotifyResponse() {
        return notifyResponse;
    }
}
