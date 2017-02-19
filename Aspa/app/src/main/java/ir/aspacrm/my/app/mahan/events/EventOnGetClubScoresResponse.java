package ir.aspacrm.my.app.mahan.events;

import ir.aspacrm.my.app.mahan.model.ClubScore;

import java.util.List;
public class EventOnGetClubScoresResponse {
    List<ClubScore> clubScoresResponse;
    public EventOnGetClubScoresResponse(List<ClubScore> clubScoresResponse) {
        this.clubScoresResponse = clubScoresResponse;
    }
    public List<ClubScore> getClubScoresResponse() {
        return clubScoresResponse;
    }
}
