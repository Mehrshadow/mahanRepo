package ir.aspacrm.my.app.mahan.G.events;

import ir.aspacrm.my.app.mahan.G.G.gson.TicketDetailsResponse;

import java.util.List;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetTicketDetailsResponse {
    List<TicketDetailsResponse> ticketDetails;
    public EventOnGetTicketDetailsResponse(List<TicketDetailsResponse> ticketDetails) {
        this.ticketDetails = ticketDetails;
    }
    public List<TicketDetailsResponse> getTicketDetails() {
        return ticketDetails;
    }
}
