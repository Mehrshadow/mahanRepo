package ir.aspacrm.my.app.mahan.G.events;

import ir.aspacrm.my.app.mahan.G.G.gson.TicketResponse;

import java.util.List;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetTicketResponse {
    List<TicketResponse> ticketResponses;
    public EventOnGetTicketResponse(List<TicketResponse> ticketResponses) {
        this.ticketResponses = ticketResponses;
    }
    public List<TicketResponse> getTicketResponses() {
        return ticketResponses;
    }
}
