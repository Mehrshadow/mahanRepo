package ir.aspacrm.my.app.mahan;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.activeandroid.query.Select;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.adapter.AdapterConnection;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.classes.WebService;
import ir.aspacrm.my.app.mahan.events.EventOnGetConnectionResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorGetConnections;
import ir.aspacrm.my.app.mahan.events.EventOnNoAccessServerResponse;
import ir.aspacrm.my.app.mahan.model.Connection;

import java.util.ArrayList;
import java.util.List;

public class ActivityShowConnections extends AppCompatActivity{


    @Bind(R.id.lstConnection) RecyclerView lstConnection;
    @Bind(R.id.layBtnClose) LinearLayout layBtnClose;
    @Bind(R.id.txtShowMessage) TextView txtShowMessage;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;


    AdapterConnection adapterConnection;
    LinearLayoutManager linearLayoutManager;
    List<Connection> connections = new ArrayList<>();
    private String current = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_connections);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);


        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context,R.color.dark_dark_grey));

        adapterConnection = new AdapterConnection(connections);
        linearLayoutManager = new LinearLayoutManager(this);
        lstConnection.setLayoutManager(linearLayoutManager);
        lstConnection.setAdapter(adapterConnection);

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

        WebService.sendGetConnectionsRequest();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowFactors : swipeRefreshLayout onRefresh()");
                WebService.sendGetConnectionsRequest();
            }
        });

        layBtnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }
    public void onEventMainThread(EventOnGetConnectionResponse event){
        Logger.d("ActivityShowConnections : EventOnGetConnectionResponse is raised");
        swipeRefreshLayout.setRefreshing(false);
        connections = event.getGetConnectionsResponses();
        adapterConnection.updateList(connections);
        if ( connections.size() == 0 ){
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("موردی یافت نشد.");
        }else {
            txtShowMessage.setVisibility(View.GONE);
        }
    }
    public void onEventMainThread(EventOnGetErrorGetConnections event){
        Logger.d("ActivityShowConnections : EventOnGetErrorGetConnections is raised");
        swipeRefreshLayout.setRefreshing(false);
        txtShowMessage.setVisibility(View.VISIBLE);
        txtShowMessage.setText("خطا در دریافت اطلاعات از سرور لطفا دوباره تلاش کنید.");
    }
    public void onEventMainThread(EventOnNoAccessServerResponse event){
        Logger.d("ActivityShowConnections : EventOnNoAccessServerResponse is raised");
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getConnectionsFromDB();
    }
    private void getConnectionsFromDB() {
        connections = new Select().from(Connection.class).where("UserId = ? ", G.currentUser.userId).execute();
        adapterConnection.updateList(connections);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    /*-------------------------------------------------------------------------------------------------------------*/

}
