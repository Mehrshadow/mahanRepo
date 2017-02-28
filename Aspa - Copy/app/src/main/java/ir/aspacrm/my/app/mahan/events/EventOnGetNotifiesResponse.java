package ir.aspacrm.my.app.mahan.events;

import ir.aspacrm.my.app.mahan.gson.NotifyResponse;

import java.util.List;

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
