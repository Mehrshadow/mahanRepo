package ir.aspacrm.my.app.mahan.events;

/**
 * Created by Microsoft on 4/2/2016.
 */
public class EventOnGetClubScoreResponse {
    boolean result;
    int score;
    public EventOnGetClubScoreResponse(boolean result, int score) {
        this.result = result;
        this.score = score;
    }
    public boolean isResult() {
        return result;
    }
    public int getScore() {
        return score;
    }
}
