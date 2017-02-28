package ir.aspacrm.my.app.mahan.events;

import ir.aspacrm.my.app.mahan.model.Connection;

import java.util.List;

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
