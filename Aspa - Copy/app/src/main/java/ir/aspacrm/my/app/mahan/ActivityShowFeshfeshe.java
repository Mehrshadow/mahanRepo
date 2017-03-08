package ir.aspacrm.my.app.mahan;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.adapter.AdapterFeshfeshe;
import ir.aspacrm.my.app.mahan.classes.DialogClass;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.classes.WebService;
import ir.aspacrm.my.app.mahan.events.EventOnAddScoreResponse;
import ir.aspacrm.my.app.mahan.events.EventOnClickedEndFeshfeshe;
import ir.aspacrm.my.app.mahan.events.EventOnClickedStartFeshfeshe;
import ir.aspacrm.my.app.mahan.events.EventOnGetCurrentFeshFesheResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetEndFeshFeshesResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorEndFeshFeshes;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorLoadFeshFeshes;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorStartFeshFeshes;
import ir.aspacrm.my.app.mahan.events.EventOnGetLoadFeshFeshesResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetStartFeshFeshesResponse;
import ir.aspacrm.my.app.mahan.events.EventOnNoAccessServerResponse;
import ir.aspacrm.my.app.mahan.model.Feshfeshe;

/**
 * Created by Microsoft on 4/2/2016.
 */
public class ActivityShowFeshfeshe extends AppCompatActivity {
    @Bind(R.id.layBtnBack) LinearLayout layBtnBack;
    @Bind(R.id.lstFeshfeshe) RecyclerView lstFeshfeshe;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.txtShowMessage) TextView txtShowMessage;
    @Bind(R.id.txtCurrentFeshfesheExpireDate) TextView txtCurrentFeshfesheExpireDate;
    @Bind(R.id.txtCurrentFeshfesheTraffic) TextView txtCurrentFeshfesheTraffic;
    @Bind(R.id.layCurrentFeshfeshe) LinearLayout layCurrentFeshfeshe;
    @Bind(R.id.imgEndCurrentFeshfesheRequest) CardView imgEndCurrentFeshfesheRequest;


    AdapterFeshfeshe adapterFeshfeshe;
    List<Feshfeshe> feshfeshes = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    DialogClass dlgWaiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_feshfeshe);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        layCurrentFeshfeshe.setVisibility(View.GONE);

        adapterFeshfeshe = new AdapterFeshfeshe(feshfeshes);
        linearLayoutManager = new LinearLayoutManager(G.context);
        lstFeshfeshe.setLayoutManager(linearLayoutManager);
        lstFeshfeshe.setAdapter(adapterFeshfeshe);

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
        WebService.sendGetCurrentFeshFeshesRequest();
        WebService.sendLoadFeshFeshesRequest();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowFeshfeshe : swipeRefreshLayout onRefresh()");
                WebService.sendLoadFeshFeshesRequest();
                WebService.sendGetCurrentFeshFeshesRequest();
            }
        });

        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgEndCurrentFeshfesheRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogClass dlgClass = new DialogClass();
                dlgClass.showQuestionForEndFeshfesheDialog();
            }
        });
    }
    public void onEventMainThread(EventOnGetLoadFeshFeshesResponse event) {
        Logger.d("ActivityShowFeshfeshe : EventOnGetLoadFeshFeshesResponse is raised.");
        swipeRefreshLayout.setRefreshing(false);
        if (event.getFeshfesheList().size() == 0) {
            txtShowMessage.setVisibility(View.VISIBLE);
            feshfeshes = new ArrayList<>();
            adapterFeshfeshe.updateList(feshfeshes);
            txtShowMessage.setText("موردی یافت نشد.");
        } else {
            txtShowMessage.setVisibility(View.GONE);
            feshfeshes = event.getFeshfesheList();
            adapterFeshfeshe.updateList(feshfeshes);
        }
    }
    public void onEventMainThread(EventOnGetErrorLoadFeshFeshes event) {
        Logger.d("ActivityShowFeshfeshe : EventOnGetErrorLoadFeshFeshes is raised.");
        swipeRefreshLayout.setRefreshing(false);
        getFeshfesheFromDB();
    }
    public void onEventMainThread(EventOnClickedStartFeshfeshe event) {
        Logger.d("ActivityShowFeshfeshe : EventOnClickedStartFeshfeshe is raised.");
        dlgWaiting = new DialogClass();
        dlgWaiting.DialogWaiting();
        WebService.sendStartFeshFeshesRequest(event.getFeshfesheCode());
    }
    public void onEventMainThread(EventOnClickedEndFeshfeshe event) {
        Logger.d("ActivityShowFeshfeshe : EventOnClickedEndFeshfeshe is raised.");
        dlgWaiting = new DialogClass();
        dlgWaiting.DialogWaiting();
        WebService.sendGetEndFeshFeshesRequest();
    }
    public void onEventMainThread(EventOnGetStartFeshFeshesResponse event) {
        Logger.d("ActivityShowFeshfeshe : EventOnGetStartFeshFeshesResponse is raised.");
        if (dlgWaiting != null)
            dlgWaiting.DialogWaitingClose();
        if(!event.getStatus()){
            DialogClass dlgMessage = new DialogClass();
            dlgMessage.showMessageDialog("خطا",event.getMessage());
        }else{
            swipeRefreshLayout.setRefreshing(true);
            WebService.sendLoadFeshFeshesRequest();
            WebService.sendGetCurrentFeshFeshesRequest();
        }
    }
    public void onEventMainThread(EventOnGetEndFeshFeshesResponse event) {
        Logger.d("ActivityShowFeshfeshe : EventOnGetEndFeshFeshesResponse is raised.");
        if (dlgWaiting != null)
            dlgWaiting.DialogWaitingClose();

        swipeRefreshLayout.setRefreshing(true);
        WebService.sendLoadFeshFeshesRequest();
        WebService.sendGetCurrentFeshFeshesRequest();

    }
    public void onEventMainThread(EventOnGetErrorStartFeshFeshes event) {
        Logger.d("ActivityShowFeshfeshe : EventOnGetErrorStartFeshFeshes is raised.");
        if (dlgWaiting != null)
            dlgWaiting.DialogWaitingClose();
    }
    public void onEventMainThread(EventOnGetCurrentFeshFesheResponse event){
        Logger.d("ActivityShowFeshfeshe : EventOnGetCurrentFeshFesheResponse is raised.");
        if(event.getCurrentFeshFesheResponse().size() == 0){
            layCurrentFeshfeshe.setVisibility(View.GONE);
        }else{
            layCurrentFeshfeshe.setVisibility(View.VISIBLE);
            txtCurrentFeshfesheExpireDate.setText("تاریخ پایان : " + event.getCurrentFeshFesheResponse().get(0).Expire );
            txtCurrentFeshfesheTraffic.setText("ترافیک باقیمانده : " + event.getCurrentFeshFesheResponse().get(0).Traffic + "مگابایت ");
        }
    }
    public void onEventMainThread(EventOnGetErrorEndFeshFeshes event) {
        Logger.d("ActivityShowFeshfeshe : EventOnGetErrorEndFeshFeshes is raised.");
        if (dlgWaiting != null)
            dlgWaiting.DialogWaitingClose();
    }
    public void onEventMainThread(EventOnNoAccessServerResponse event) {
        Logger.d("ActivityShowFeshfeshe : EventOnNoAccessServerResponse is raised.");
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        if (dlgWaiting != null)
            dlgWaiting.DialogWaitingClose();
        swipeRefreshLayout.setRefreshing(false);
        getFeshfesheFromDB();
    }
    private void getFeshfesheFromDB() {
        feshfeshes = new Select().from(Feshfeshe.class).where("UserId = ? ", G.currentUser.userId).execute();
        adapterFeshfeshe.updateList(feshfeshes);
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
