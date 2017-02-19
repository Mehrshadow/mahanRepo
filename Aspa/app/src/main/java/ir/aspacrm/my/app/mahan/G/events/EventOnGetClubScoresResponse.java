package ir.aspacrm.my.app.mahan.G.events;


import java.util.List;

import ir.aspacrm.my.app.mahan.G.model.ClubScore;

public class EventOnGetClubScoresResponse {
    List<ClubScore> clubScoresResponse;
    public EventOnGetClubScoresResponse(List<ClubScore> clubScoresResponse) {
        this.clubScoresResponse = clubScoresResponse;
    }
    public List<ClubScore> getClubScoresResponse() {
        return clubScoresResponse;
    }
}
