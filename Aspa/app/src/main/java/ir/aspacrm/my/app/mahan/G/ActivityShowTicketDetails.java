package ir.aspacrm.my.app.mahan.G;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.activeandroid.query.Select;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.G.adapter.AdapterTicketDetails;
import ir.aspacrm.my.app.mahan.G.classes.DialogClass;
import ir.aspacrm.my.app.mahan.G.classes.Logger;
import ir.aspacrm.my.app.mahan.G.classes.WebService;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetAddTicketDetailsResponse;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetErrorGetTicketDetails;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetTicketDetailsResponse;
import ir.aspacrm.my.app.mahan.G.events.EventOnNoAccessServerResponse;
import ir.aspacrm.my.app.mahan.G.model.TicketDetail;
import ir.aspacrm.my.app.mahan.G.model.Unit;


import java.util.ArrayList;
import java.util.List;

public class ActivityShowTicketDetails extends AppCompatActivity{

    @Bind(ir.aspacrm.my.app.mahan.R.id.lstTicketDetail) RecyclerView lstTicketDetail;
    @Bind(ir.aspacrm.my.app.mahan.R.id.layBtnClose) LinearLayout layBtnClose;
    @Bind(ir.aspacrm.my.app.mahan.R.id.txtShowMessage) TextView txtShowMessage;
    @Bind(ir.aspacrm.my.app.mahan.R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(ir.aspacrm.my.app.mahan.R.id.laySendChatMessage) LinearLayout laySendChatMessage;
    @Bind(ir.aspacrm.my.app.mahan.R.id.edtTicketReplay) EditText edtTicketReplay;

    AdapterTicketDetails adapterTicketDetail;
    LinearLayoutManager linearLayoutManager;
    List<TicketDetail> tickets = new ArrayList<>();


    long ticketCode = 0;
    Unit ticketUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ir.aspacrm.my.app.mahan.R.layout.activity_show_ticket_details);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);




        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, ir.aspacrm.my.app.mahan.R.color.dark_dark_grey));


        ticketCode = getIntent().getExtras().getLong("TICKET_CODE");

        adapterTicketDetail = new AdapterTicketDetails(tickets);
        linearLayoutManager = new LinearLayoutManager(this);
        lstTicketDetail.setLayoutManager(linearLayoutManager);
        lstTicketDetail.setAdapter(adapterTicketDetail);

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
        WebService.sendGetTicketDetailsRequest(ticketCode);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowTickets : swipeRefreshLayout onRefresh()");
                WebService.sendGetTicketDetailsRequest(ticketCode);
            }
        });
        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        laySendChatMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edtTicketReplay.getText().toString().trim();
                if(message.length() == 0){
                    return;
                }else{
                    WebService.sendAddTicketDetailRequest(ticketCode, ticketUnit.code, message);
                    laySendChatMessage.setOnClickListener(null);
                }
            }
        });
    }
    public void onEventMainThread(EventOnGetTicketDetailsResponse event){
        Logger.d("ActivityShowTicketDetails : EventOnGetTicketDetailsResponse is rasied");
//        tickets = event.getTicketDetails();
//        adapterTicketDetail.updateList(tickets);
        getTicketDetailsFromDB();
        swipeRefreshLayout.setRefreshing(false);
    }
    public void onEventMainThread(EventOnGetErrorGetTicketDetails event){
        Logger.d("ActivityShowTicketDetails : EventOnGetErrorGetTicketDetails is rasied");
        getTicketDetailsFromDB();
        swipeRefreshLayout.setRefreshing(false);
    }
    public void onEventMainThread(EventOnGetAddTicketDetailsResponse event){
        Logger.d("ActivityShowTicketDetails : EventOnGetAddTicketDetailsResponse is rasied");
        swipeRefreshLayout.setRefreshing(true);
        WebService.sendGetTicketDetailsRequest(ticketCode);
        laySendChatMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edtTicketReplay.getText().toString().trim();
                if (message.length() == 0) {
                    return;
                } else {
                    WebService.sendAddTicketDetailRequest(ticketCode, Integer.parseInt(tickets.get(0).unit), message);
                    laySendChatMessage.setOnClickListener(null);
                }
            }
        });
        if(event.getStatus()){
            edtTicketReplay.setText("");
        }else{
            DialogClass dlgMessage = new DialogClass();
            dlgMessage.showMessageDialog("خطا","خطایی در دریافت تیکت بوجود آمده است.");
        }
    }
    public void onEventMainThread(EventOnNoAccessServerResponse event){
        Logger.d("ActivityShowTicketDetails : EventOnNoAccessServerResponse is rasied");
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getTicketDetailsFromDB();
    }
    private void getTicketDetailsFromDB() {
        tickets = new Select()
                .from( TicketDetail.class)
                .where( "parentCode = ? AND userId = ? ", ticketCode , G.currentUser.userId )
                .orderBy("Id desc")
                .execute();
        adapterTicketDetail.updateList(tickets);
        ticketUnit = (new Select().from(Unit.class).where("UserId = ? AND Name = ? ",G.currentUser.userId,tickets.get(0).unit).executeSingle());
        lstTicketDetail.post(new Runnable() {
            @Override
            public void run() {
                lstTicketDetail.scrollToPosition(tickets.size());
            }
        });
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
