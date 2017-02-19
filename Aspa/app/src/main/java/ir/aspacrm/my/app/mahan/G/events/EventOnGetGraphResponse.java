package ir.aspacrm.my.app.mahan.G.events;

import ir.aspacrm.my.app.mahan.G.model.Graph;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetGraphResponse {
    Graph graph;
    public EventOnGetGraphResponse(Graph graph) {
        this.graph = graph;
    }
    public Graph getGraph() {
        return graph;
    }
}
