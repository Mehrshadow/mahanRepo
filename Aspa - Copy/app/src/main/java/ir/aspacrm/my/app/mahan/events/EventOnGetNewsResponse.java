package ir.aspacrm.my.app.mahan.events;

import ir.aspacrm.my.app.mahan.gson.NewsResponse;

import java.util.List;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetNewsResponse {
    List<NewsResponse> newses ;
    public EventOnGetNewsResponse(List<NewsResponse> newses) {
        this.newses = newses;
    }
    public List<NewsResponse> getNewses() {
        return newses;
    }
}
