package ir.aspacrm.my.app.mahan.G;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.activeandroid.query.Select;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.G.adapter.AdapterClubScore;
import ir.aspacrm.my.app.mahan.G.classes.Logger;
import ir.aspacrm.my.app.mahan.G.classes.WebService;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetClubScoreResponse;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetClubScoresResponse;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetErrorClubScores;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetErrorGetClubScore;
import ir.aspacrm.my.app.mahan.G.events.EventOnNoAccessServerResponse;
import ir.aspacrm.my.app.mahan.G.model.ClubScore;


import java.util.ArrayList;
import java.util.List;

public class ActivityShowClubScores extends AppCompatActivity {

    @Bind(ir.aspacrm.my.app.mahan.R.id.layBtnClose) LinearLayout layBtnClose;
    @Bind(ir.aspacrm.my.app.mahan.R.id.lstClubScore) RecyclerView lstClubScore;
    @Bind(ir.aspacrm.my.app.mahan.R.id.txtTotalClubScore) TextView txtTotalClubScore;
    @Bind(ir.aspacrm.my.app.mahan.R.id.layTotalClubScore)  LinearLayout layTotalClubScore;
    @Bind(ir.aspacrm.my.app.mahan.R.id.txtShowMessage) TextView txtShowMessage;
    @Bind(ir.aspacrm.my.app.mahan.R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;

    AdapterClubScore adapterClubScore;
    List<ClubScore> scores = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ir.aspacrm.my.app.mahan.R.layout.activity_show_club_scores);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);


        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, ir.aspacrm.my.app.mahan.R.color.dark_dark_grey));

        layTotalClubScore.setVisibility(View.GONE);

        adapterClubScore = new AdapterClubScore(scores);
        linearLayoutManager = new LinearLayoutManager(G.context);
        lstClubScore.setLayoutManager(linearLayoutManager);
        lstClubScore.setAdapter(adapterClubScore);
        /** be dalil inke dar fragment
         *  swipeRefreshLayout.setRefreshing(true);
         *  dar ebteda kar nemikonad ke listener
         *  swipeRefreshLayout.setOnRefreshListener ra seda bezanad be surate dasti aan ra seda mizanim.*/
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        WebService.sendGetClubScoreRequest();
        WebService.sendClubScoresRequest();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowFeshfeshe : swipeRefreshLayout onRefresh()");
                WebService.sendClubScoresRequest();
            }
        });

        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void onEventMainThread(EventOnGetClubScoreResponse event) {
        Logger.d("ActivityShowClubScores : EventOnGetClubScoreResponse is raised.");
        boolean result = event.isResult();
        if(result){
            layTotalClubScore.setVisibility(View.VISIBLE);
            int score = event.getScore();
            if( score == 0 )
                txtTotalClubScore.setText("مجموع امتیازات : " + "0");
            else if ( score > 0 )
                txtTotalClubScore.setText("مجموع امتیازات : " + score + "+");
            else
                txtTotalClubScore.setText("مجموع امتیازات : " + score);
        }else{
            layTotalClubScore.setVisibility(View.GONE);
        }
    }
    public void onEventMainThread(EventOnGetErrorGetClubScore event) {
        Logger.d("ActivityShowClubScores : EventOnGetErrorGetClubScore is raised.");
    }
    public void onEventMainThread(EventOnGetClubScoresResponse event) {
        Logger.d("ActivityShowClubScores : EventOnGetClubScoresResponse is raised.");
        swipeRefreshLayout.setRefreshing(false);
        scores = event.getClubScoresResponse();
        if(scores.size() == 0 ){
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("موردی یافت نشد.");
            scores = new ArrayList<>();
            adapterClubScore.updateList(scores);
        }else {
            scores = event.getClubScoresResponse();
            adapterClubScore.updateList(scores);
        }
    }
    public void onEventMainThread(EventOnGetErrorClubScores event) {
        Logger.d("ActivityShowClubScores : EventOnGetErrorClubScores is raised.");
        swipeRefreshLayout.setRefreshing(false);
        getClubScoresFormDB();
    }
    public void onEventMainThread(EventOnNoAccessServerResponse event) {
        Logger.d("ActivityShowClubScores : EventOnNoAccessServerResponse is raised.");
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getClubScoresFormDB();
    }
    private void getClubScoresFormDB() {
        scores = new Select().from(ClubScore.class).where("UserId = ? " , G.currentUser.userId).execute();
        adapterClubScore.updateList(scores);
    }
    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
