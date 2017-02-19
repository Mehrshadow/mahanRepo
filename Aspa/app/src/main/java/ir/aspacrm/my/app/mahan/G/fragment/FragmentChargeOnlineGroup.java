package ir.aspacrm.my.app.mahan.G.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.G.G;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.G.G.adapter.AdapterChargeOnlineGroup;
import ir.aspacrm.my.app.mahan.G.G.classes.Logger;
import ir.aspacrm.my.app.mahan.G.G.classes.U;
import ir.aspacrm.my.app.mahan.G.G.classes.WebService;
import ir.aspacrm.my.app.mahan.G.G.enums.EnumGridType;
import ir.aspacrm.my.app.mahan.G.G.events.EventOnGetChargeOnlineForLoadGroups;
import ir.aspacrm.my.app.mahan.G.G.events.EventOnGetErrorChargeOnlineForLoadGroups;
import ir.aspacrm.my.app.mahan.G.G.gson.ChargeOnlineGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentChargeOnlineGroup extends Fragment {

    @Bind(R.id.lstGroup) RecyclerView lstGroup;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.txtShowMessage) TextView txtShowMessage;

    @Bind(R.id.layBtnClose) LinearLayout layBtnClose;
    @Bind(R.id.txtPageTitle) TextView txtPageTitle;

    AdapterChargeOnlineGroup adapterChargeOnlineGroup;
    LinearLayoutManager linearLayoutManager;
    List<ChargeOnlineGroup> groups = new ArrayList<>();

    int isClub;
    int whichMenuItem;
    int columnsOfGrid = 2;
    int typeOfGrid = 1;

    public static FragmentChargeOnlineGroup newInstance(int isClub,int whichMenuItem) {
        Bundle args = new Bundle();
        args.putInt("IS_CLUB",isClub);
        args.putInt("WHICH_MENU_ITEM",whichMenuItem);
        FragmentChargeOnlineGroup fragment = new FragmentChargeOnlineGroup();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("FragmentChargeOnlineGroup : onCreate()");
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charge_online_group, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d("FragmentChargeOnlineGroup : onActivityCreated()");


        isClub = getArguments().getInt("IS_CLUB");
        whichMenuItem = getArguments().getInt("WHICH_MENU_ITEM");


        txtPageTitle.setText(U.getMenuItemName(whichMenuItem));

        adapterChargeOnlineGroup = new AdapterChargeOnlineGroup(groups,isClub,whichMenuItem);
        /** baraye anke moshakhas konim grid
         * ma be surate list namayesh dade sahvad ya grid chand setune*/
        typeOfGrid = G.localMemory.getInt("GRID_TYPE",1);
        if(typeOfGrid == EnumGridType.GRID){
            columnsOfGrid = getResources().getInteger(R.integer.grid_columns);
        }else{
            columnsOfGrid = getResources().getInteger(R.integer.grid_list_columns);
        }
        linearLayoutManager = new GridLayoutManager(G.context,columnsOfGrid);
        lstGroup.setLayoutManager(linearLayoutManager);
        lstGroup.setHasFixedSize(true);
        lstGroup.setAdapter(adapterChargeOnlineGroup);

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

        WebService.sendChargeOnlineForLoadGroupsRequest(isClub);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowFactors : swipeRefreshLayout onRefresh()");
                WebService.sendChargeOnlineForLoadGroupsRequest(isClub);
            }
        });

        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


    }
    public void onEventMainThread(EventOnGetChargeOnlineForLoadGroups event){
        Logger.d("FragmentChargeOnlineGroup : EventOnGetChargeOnlineForLoadGroups is raised");
        swipeRefreshLayout.setRefreshing(false);
        groups = event.getChargeOnlineGroups();
        if(groups.size() == 0 ){
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("موردی یافت نشد.");
        }else{
            adapterChargeOnlineGroup.updateList(groups);
        }
    }
    public void onEventMainThread(EventOnGetErrorChargeOnlineForLoadGroups  event){
        Logger.d("FragmentChargeOnlineGroup : EventOnGetErrorChargeOnlineForLoadGroups is raised");
        swipeRefreshLayout.setRefreshing(false);
        groups = new ArrayList<>();
        adapterChargeOnlineGroup.updateList(groups);
        txtShowMessage.setVisibility(View.VISIBLE);
        txtShowMessage.setText("خطا در دریافت اطلاعات از سرور لطفا دوباره تلاش کنید.");
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
