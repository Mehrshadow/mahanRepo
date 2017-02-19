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
import ir.aspacrm.my.app.mahan.G.adapter.AdapterNews;
import ir.aspacrm.my.app.mahan.G.classes.Logger;
import ir.aspacrm.my.app.mahan.G.classes.U;
import ir.aspacrm.my.app.mahan.G.classes.WebService;
import ir.aspacrm.my.app.mahan.G.enums.EnumInternetErrorType;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetErrorGetNews;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetNewsResponse;
import ir.aspacrm.my.app.mahan.G.model.News;


import java.util.ArrayList;
import java.util.List;


public class ActivityShowNews extends AppCompatActivity {
    @Bind(ir.aspacrm.my.app.mahan.G.R.id.layBtnClose) LinearLayout layBtnClose;
    @Bind(R.id.lstNews) RecyclerView lstNews;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(ir.aspacrm.my.app.mahan.R.id.txtShowMessage) TextView txtShowMessage;

    AdapterNews adapterNews;
    LinearLayoutManager linearLayoutManager;
    List<News> newses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, ir.aspacrm.my.app.mahan.R.color.dark_dark_grey));

        adapterNews = new AdapterNews(newses);
        linearLayoutManager = new LinearLayoutManager(this);
        lstNews.setLayoutManager(linearLayoutManager);
        lstNews.setAdapter(adapterNews);

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
        sendRequestGetNewNews();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowPayments : swipeRefreshLayout onRefresh()");
                sendRequestGetNewNews();
            }
        });
        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void sendRequestGetNewNews() {
        News lastNews = new Select()
                .from(News.class)
                .where("UserId = ? " , G.currentUser.userId)
                .orderBy("NewsID desc")
                .limit(1)
                .executeSingle();
        if(lastNews == null) {
            WebService.sendGetNewsRequest(0);
        }else{
            WebService.sendGetNewsRequest(lastNews.newsID);
        }
    }
    public void onEventMainThread(EventOnGetNewsResponse event){
        Logger.d("ActivityShowNews : EventOnGetNewsResponse is raised.");
        getNewsFromDB();
        swipeRefreshLayout.setRefreshing(false);
    }
    public void onEventMainThread(EventOnGetErrorGetNews event){
        Logger.d("ActivityShowNews : EventOnGetErrorGetNews is raised.");
        getNewsFromDB();
        swipeRefreshLayout.setRefreshing(false);

        if(event.getErrorType() == EnumInternetErrorType.NO_INTERNET_ACCESS){
            U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
        }

    }
    private void getNewsFromDB() {
        newses = new Select()
                .from(News.class)
                .where("UserId = ?", G.currentUser.userId)
                .orderBy("NewsID desc")
                .execute();
        adapterNews.updateList(newses);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
