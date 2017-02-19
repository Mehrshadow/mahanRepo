package ir.aspacrm.my.app.mahan.G.events;


import java.util.List;

import ir.aspacrm.my.app.mahan.G.model.Connection;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetConnectionResponse {
    List<Connection> getConnectionsResponses;
    public EventOnGetConnectionResponse(List<Connection> getConnectionsResponses) {
        this.getConnectionsResponses = getConnectionsResponses;
    }
    public List<Connection> getGetConnectionsResponses() {
        return getConnectionsResponses;
    }
}
