package ir.aspacrm.my.app.mahan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.adapter.AdapterChargeOnlineGroupPackage;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.classes.U;
import ir.aspacrm.my.app.mahan.classes.WebService;
import ir.aspacrm.my.app.mahan.events.EventOnClickedChargeOnlineGroupPackage;
import ir.aspacrm.my.app.mahan.events.EventOnGetChargeOnlineForLoadPackages;
import ir.aspacrm.my.app.mahan.events.EventOnGetChargeOnlineForSelectPackage;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorChargeOnlineForLoadPackages;
import ir.aspacrm.my.app.mahan.gson.ChargeOnlineGroupPackage;

import java.util.ArrayList;
import java.util.List;

public class FragmentChargeOnlineGroupPackage extends Fragment {

    @Bind(R.id.lstPackage) RecyclerView lstPackage;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.txtShowMessage) TextView txtShowMessage;
    @Bind(R.id.layLoading) LinearLayout layLoading;

    @Bind(R.id.layBtnClose) LinearLayout layBtnClose;
    @Bind(R.id.txtPageTitle) TextView txtPageTitle;
    @Bind(R.id.imgToolbar)
    ImageView imgToolbar;

    AdapterChargeOnlineGroupPackage adapterChargeOnlineGroupPackage;
    LinearLayoutManager linearLayoutManager;
    List<ChargeOnlineGroupPackage> packages = new ArrayList<>();

    int isClub;
    int whichMenuItem;
    long groupCode;
    public static FragmentChargeOnlineGroupPackage newInstance(int isClub, int whichMenuItem, long groupCode) {
        Bundle args = new Bundle();
        args.putInt("WHICH_MENU_ITEM", whichMenuItem);
        args.putInt("IS_CLUB", isClub);
        args.putLong("GROUP_CODE", groupCode);
        FragmentChargeOnlineGroupPackage fragment = new FragmentChargeOnlineGroupPackage();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("FragmentChargeOnlineGroupPackage : onCreate()");
        EventBus.getDefault().register(this);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charge_online_group_package, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d("FragmentChargeOnlineGroupPackage : onActivityCreated()");

        layLoading.setVisibility(View.GONE);
        isClub = getArguments().getInt("IS_CLUB");
        whichMenuItem = getArguments().getInt("WHICH_MENU_ITEM");
        groupCode = getArguments().getLong("GROUP_CODE");

        txtPageTitle.setText(U.getMenuItemName(whichMenuItem));
        U.getMenuItemIcon(imgToolbar,whichMenuItem);


        adapterChargeOnlineGroupPackage = new AdapterChargeOnlineGroupPackage(packages,isClub);
        linearLayoutManager = new LinearLayoutManager(G.context);
        lstPackage.setLayoutManager(linearLayoutManager);
        lstPackage.setHasFixedSize(true);
        lstPackage.setAdapter(adapterChargeOnlineGroupPackage);
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
        WebService.sendChargeOnlineForLoadPackagesRequest(isClub, groupCode, whichMenuItem);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("FragmentChargeOnlineGroupPackage : swipeRefreshLayout onRefresh()");
                WebService.sendChargeOnlineForLoadPackagesRequest(isClub, groupCode, whichMenuItem);
            }
        });

        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }
    public void onEventMainThread(EventOnGetChargeOnlineForLoadPackages event){
        Logger.d("FragmentChargeOnlineGroupPackage : EventOnGetChargeOnlineForLoadPackages is raised");
        swipeRefreshLayout.setRefreshing(false);
        packages = event.getChargeOnlineGroupPackages();
        if(packages.size() == 0 ){
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("موردی یافت نشد.");
        }else{
            adapterChargeOnlineGroupPackage.updateList(packages);
        }
    }
    public void onEventMainThread(EventOnGetErrorChargeOnlineForLoadPackages event){
        Logger.d("FragmentChargeOnlineGroupPackage : EventOnGetErrorChargeOnlineForLoadPackages is raised");
        swipeRefreshLayout.setRefreshing(false);
        packages = new ArrayList<>();
        adapterChargeOnlineGroupPackage.updateList(packages);
        txtShowMessage.setVisibility(View.VISIBLE);
        txtShowMessage.setText("خطا در دریافت اطلاعات از سرور لطفا دوباره تلاش کنید.");
    }
    public void onEventMainThread(EventOnClickedChargeOnlineGroupPackage event){
        Logger.d("FragmentChargeOnlineGroupPackage : EventOnClickedChargeOnlineGroupPackage is raised");
        long packageCode = event.getPackageCode();
        layLoading.setVisibility(View.VISIBLE);
        WebService.sendChargeOnlineForSelectPackageRequest(isClub,packageCode);
    }
    public void onEventMainThread(EventOnGetChargeOnlineForSelectPackage event){
        Logger.d("FragmentChargeOnlineGroupPackage : EventOnGetChargeOnlineForSelectPackage is raised");
        /** just for hide loading */
        layLoading.setVisibility(View.GONE);
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
