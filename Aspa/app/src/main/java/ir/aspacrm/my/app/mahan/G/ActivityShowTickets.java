package ir.aspacrm.my.app.mahan.G;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import ir.aspacrm.my.app.mahan.G.adapter.AdapterTicket;
import ir.aspacrm.my.app.mahan.G.classes.DialogClass;
import ir.aspacrm.my.app.mahan.G.classes.Logger;
import ir.aspacrm.my.app.mahan.G.classes.U;
import ir.aspacrm.my.app.mahan.G.classes.WebService;
import ir.aspacrm.my.app.mahan.G.events.EventOnClickedTicketItem;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetAddTicketResponse;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetErrorAddTicket;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetErrorGetTickets;
import ir.aspacrm.my.app.mahan.G.events.EventOnGetTicketResponse;
import ir.aspacrm.my.app.mahan.G.events.EventOnSendTicketRequest;
import ir.aspacrm.my.app.mahan.G.gson.TicketResponse;
import ir.aspacrm.my.app.mahan.G.model.Ticket;


import java.util.ArrayList;
import java.util.List;

public class ActivityShowTickets extends AppCompatActivity implements View.OnClickListener {

    @Bind(ir.aspacrm.my.app.mahan.R.id.lstTicket) RecyclerView lstTicket;
    @Bind(ir.aspacrm.my.app.mahan.R.id.layBtnClose) LinearLayout layBtnClose;
    @Bind(ir.aspacrm.my.app.mahan.R.id.txtShowMessage) TextView txtShowMessage;
    @Bind(ir.aspacrm.my.app.mahan.R.id.txtShowErrorMessage) TextView txtShowErrorMessage;
    @Bind(ir.aspacrm.my.app.mahan.R.id.swipeRefreshLayout)  SwipeRefreshLayout swipeRefreshLayout;
    @Bind(ir.aspacrm.my.app.mahan.R.id.actionBtnAddTicket) FloatingActionButton actionBtnAddTicket;
    @Bind(ir.aspacrm.my.app.mahan.R.id.layExpandTicket) LinearLayout layExpandTicket;

    AdapterTicket adapterTicket;
    LinearLayoutManager linearLayoutManager;
    List<TicketResponse> tickets = new ArrayList<>();
    DialogClass dlgWaiting;
    /** baraye inke bedunim range dokme bayad chejuri bashad **/
    boolean isCloseButtonShow = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ir.aspacrm.my.app.mahan.R.layout.activity_show_tickets);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);


        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, ir.aspacrm.my.app.mahan.R.color.dark_dark_grey));


        actionBtnAddTicket.setOnClickListener(this);
        adapterTicket = new AdapterTicket(tickets);
        linearLayoutManager = new LinearLayoutManager(this);
        lstTicket.setLayoutManager(linearLayoutManager);
        lstTicket.setAdapter(adapterTicket);
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
        WebService.sendGetTicketsRequest();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowTickets : swipeRefreshLayout onRefresh()");
                WebService.sendGetTicketsRequest();
            }
        });

        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case ir.aspacrm.my.app.mahan.R.id.actionBtnAddTicket :
                if (!layExpandTicket.isShown()) {
                    U.expand(layExpandTicket);
                    actionBtnAddTicket.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(G.currentActivity, ir.aspacrm.my.app.mahan.R.color.red)));
                    actionBtnAddTicket.setRippleColor(ContextCompat.getColor(G.currentActivity, ir.aspacrm.my.app.mahan.R.color.red));
                    actionBtnAddTicket.setImageResource(ir.aspacrm.my.app.mahan.R.drawable.ic_close_white_36dp);
                    isCloseButtonShow = true;
                } else {
                    U.collapse(layExpandTicket);
                    actionBtnAddTicket.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(G.currentActivity, ir.aspacrm.my.app.mahan.R.color.colorAccent)));
                    actionBtnAddTicket.setRippleColor(ContextCompat.getColor(G.currentActivity, ir.aspacrm.my.app.mahan.R.color.colorAccent));
                    actionBtnAddTicket.setImageResource(ir.aspacrm.my.app.mahan.R.drawable.sv_plus_white);
                    isCloseButtonShow = false;
                }
                break;
        }
    }
    public void onEventMainThread(EventOnGetTicketResponse event){
        Logger.d("ActivityShowTickets : EventOnGetTicketResponse is raised.");
        swipeRefreshLayout.setRefreshing(false);
        tickets = event.getTicketResponses();
        adapterTicket.updateList(tickets);
    }
    public void onEventMainThread(EventOnGetErrorGetTickets event){
        Logger.d("ActivityShowTickets : EventOnGetErrorGetTickets is raised.");
        getTicketFromDB();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void onEventMainThread(EventOnSendTicketRequest event){
        Logger.d("ActivityShowTickets : EventOnSendTicketRequest is raised.");
        dlgWaiting = new DialogClass();
        dlgWaiting.DialogWaiting();
    }
    public void onEventMainThread(EventOnGetAddTicketResponse event){
        Logger.d("ActivityShowTickets : EventOnGetAddTicketResponse is raised.");
        if(dlgWaiting != null)
            dlgWaiting.DialogWaitingClose();
        if(event.getStatus()){

            U.collapse(layExpandTicket);
            actionBtnAddTicket.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(G.currentActivity, ir.aspacrm.my.app.mahan.R.color.colorAccent)));
            actionBtnAddTicket.setRippleColor(ContextCompat.getColor(G.currentActivity, ir.aspacrm.my.app.mahan.R.color.colorAccent));
            actionBtnAddTicket.setImageResource(ir.aspacrm.my.app.mahan.R.drawable.sv_plus_white);
            isCloseButtonShow = false;

            swipeRefreshLayout.setRefreshing(true);
            WebService.sendGetTicketsRequest();
        }
    }
    public void onEventMainThread(EventOnGetErrorAddTicket event){
        Logger.d("ActivityShowTickets : EventOnGetErrorAddTicket is raised.");
        if(dlgWaiting != null)
            dlgWaiting.DialogWaitingClose();
    }
    private void getTicketFromDB() {
        List<Ticket> ticketsInDB = new Select()
                .from( Ticket.class)
                .where("userId = ? ", G.currentUser.userId)
                .execute();
        tickets.clear();
        for (Ticket t : ticketsInDB){
            TicketResponse ticket = new TicketResponse();
            ticket.Priority = t.priority;
            ticket.State = t.state;
            ticket.Date = t.date;
            ticket.Title = t.title;
            ticket.Code = t.code;
            ticket.Open = t.open;
            tickets.add(ticket);
        }
        adapterTicket.updateList(tickets);
    }
    public void onEventMainThread(EventOnClickedTicketItem event){
        Logger.d("ActivityShowTickets : EventOnClickedTicketItem is raised.");
        Intent intent = new Intent(G.context,ActivityShowTicketDetails.class);
        intent.putExtra("TICKET_CODE",event.getCode());
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
        if (isCloseButtonShow) {
            actionBtnAddTicket.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(G.currentActivity, ir.aspacrm.my.app.mahan.R.color.red)));
            actionBtnAddTicket.setRippleColor(ContextCompat.getColor(G.currentActivity, ir.aspacrm.my.app.mahan.R.color.red));
        }else{
            actionBtnAddTicket.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(G.currentActivity, ir.aspacrm.my.app.mahan.R.color.colorAccent)));
            actionBtnAddTicket.setRippleColor(ContextCompat.getColor(G.currentActivity, ir.aspacrm.my.app.mahan.R.color.colorAccent));
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
