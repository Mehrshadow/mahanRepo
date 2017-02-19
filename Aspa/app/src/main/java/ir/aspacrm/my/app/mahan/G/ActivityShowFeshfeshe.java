package ir.aspacrm.my.app.mahan.G;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.activeandroid.query.Select;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.G.adapter.AdapterFeshfeshe;
import ir.aspacrm.my.app.mahan.G.classes.DialogClass;
import ir.aspacrm.my.app.mahan.G.classes.Logger;
import ir.aspacrm.my.app.mahan.G.classes.WebService;
import ir.aspacrm.my.app.mahan.G.events.EventOnClickedEndFeshfeshe;
import ir.aspacrm.my.app.mahan.G.events.EventOnClickedStartFeshfeshe;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetCurrentFeshFesheResponse;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetEndFeshFeshesResponse;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetErrorEndFeshFeshes;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetErrorLoadFeshFeshes;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetErrorStartFeshFeshes;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetLoadFeshFeshesResponse;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetStartFeshFeshesResponse;
import ir.aspacrm.my.app.mahan.G.events.EventOnNoAccessServerResponse;
import ir.aspacrm.my.app.mahan.G.model.Feshfeshe;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Microsoft on 4/2/2016.
 */
public class ActivityShowFeshfeshe extends AppCompatActivity {
    @Bind(ir.aspacrm.my.app.mahan.G.R.id.layBtnClose) LinearLayout layBtnClose;
    @Bind(ir.aspacrm.my.app.mahan.G.R.id.lstFeshfeshe) RecyclerView lstFeshfeshe;
    @Bind(ir.aspacrm.my.app.mahan.G.R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(ir.aspacrm.my.app.mahan.G.R.id.txtShowMessage) TextView txtShowMessage;
    @Bind(ir.aspacrm.my.app.mahan.G.R.id.txtCurrentFeshfesheExpireDate) TextView txtCurrentFeshfesheExpireDate;
    @Bind(ir.aspacrm.my.app.mahan.G.R.id.txtCurrentFeshfesheTraffic) TextView txtCurrentFeshfesheTraffic;
    @Bind(ir.aspacrm.my.app.mahan.G.R.id.layCurrentFeshfeshe) LinearLayout layCurrentFeshfeshe;
    @Bind(ir.aspacrm.my.app.mahan.G.R.id.imgEndCurrentFeshfesheRequest) ImageView imgEndCurrentFeshfesheRequest;


    AdapterFeshfeshe adapterFeshfeshe;
    List<Feshfeshe> feshfeshes = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    DialogClass dlgWaiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ir.aspacrm.my.app.mahan.G.R.layout.activity_show_feshfeshe);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, ir.aspacrm.my.app.mahan.G.R.color.dark_dark_grey));

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

        layBtnClose.setOnClickListener(new View.OnClickListener() {
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
