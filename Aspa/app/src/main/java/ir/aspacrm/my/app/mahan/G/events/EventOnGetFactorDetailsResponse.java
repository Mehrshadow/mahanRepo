package ir.aspacrm.my.app.mahan.G.events;

import ir.aspacrm.my.app.mahan.G.gson.FactorDetailResponse;

import java.util.ArrayList;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetFactorDetailsResponse {
    ArrayList<FactorDetailResponse> factorDetailsResponse;
    public EventOnGetFactorDetailsResponse(ArrayList<FactorDetailResponse> factorDetailsResponse) {
        this.factorDetailsResponse = factorDetailsResponse;
    }
    public ArrayList<FactorDetailResponse> getFactorDetailResponse() {
        return factorDetailsResponse;
    }
}
