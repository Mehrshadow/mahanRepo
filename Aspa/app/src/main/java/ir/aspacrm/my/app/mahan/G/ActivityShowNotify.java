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

import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;

import java.util.ArrayList;
import java.util.List;

public class ActivityShowNotify extends AppCompatActivity {
    @Bind(ir.aspacrm.my.app.mahan.R.id.layBtnClose) LinearLayout layBtnClose;
    @Bind(ir.aspacrm.my.app.mahan.R.id.lstNotify) RecyclerView lstNotify;
    @Bind(ir.aspacrm.my.app.mahan.R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(ir.aspacrm.my.app.mahan.R.id.txtShowMessage) TextView txtShowMessage;

    AdapterNotify adapterNotify;
    LinearLayoutManager linearLayoutManager;
    List<Notify> notifies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ir.aspacrm.my.app.mahan.R.layout.activity_show_notify);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);


        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, ir.aspacrm.my.app.mahan.R.color.dark_dark_grey));

        adapterNotify = new AdapterNotify(notifies);
        linearLayoutManager = new LinearLayoutManager(this);
        lstNotify.setLayoutManager(linearLayoutManager);
        lstNotify.setAdapter(adapterNotify);

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
        sendRequestGetNewNotify();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowNotify : swipeRefreshLayout onRefresh()");
                sendRequestGetNewNotify();
            }
        });

        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void sendRequestGetNewNotify() {
        Notify lastNotify = new Select()
                .from(Notify.class)
                .where("UserId = ? " , G.currentUser.userId)
                .orderBy("NotifyCode desc")
                .limit(1)
                .executeSingle();
        if(lastNotify == null) {
            WebService.sendGetNotifiesRequest(0,false);
        }else{
            WebService.sendGetNotifiesRequest(lastNotify.notifyCode,false);
        }
    }
    public void onEventMainThread(EventOnGetNotifiesResponse event){
        Logger.d("ActivityShowNotify : EventOnGetNotifiesResponse is raised.");
        getNotifyFromDB();
        swipeRefreshLayout.setRefreshing(false);
    }
    public void onEventMainThread(EventOnGetErrorGetNotifies event){
        Logger.d("ActivityShowNotify : EventOnGetErrorGetNotifies is raised.");
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getNotifyFromDB();

        if(event.getErrorType() == EnumInternetErrorType.NO_INTERNET_ACCESS){
            U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
        }
    }
    private void getNotifyFromDB() {
        notifies = new Select()
                .from(Notify.class)
                .where("UserId = ?", G.currentUser.userId)
                .orderBy("NotifyCode desc")
                .execute();
        adapterNotify.updateList(notifies);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            ShortcutBadger.removeCountOrThrow(G.context);
        } catch (ShortcutBadgeException e) {
            //e.printStackTrace();
            Logger.d("ActivityShowNotify : " + e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
