package ir.aspacrm.my.app.mahan.events;

import ir.aspacrm.my.app.mahan.model.Factor;

import java.util.List;

public class EventOnGetFactorResponse {
    List<Factor> factorResponses;
    public EventOnGetFactorResponse(List<Factor> factorResponses) {
        this.factorResponses = factorResponses;
    }
    public List<Factor> getFactorResponses() {
        return factorResponses;
    }
}
