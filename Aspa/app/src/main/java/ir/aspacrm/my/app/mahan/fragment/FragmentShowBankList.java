package ir.aspacrm.my.app.mahan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.adapter.AdapterBank;
import ir.aspacrm.my.app.mahan.classes.DialogClass;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.classes.WebService;
import ir.aspacrm.my.app.mahan.enums.EnumGridType;
import ir.aspacrm.my.app.mahan.events.EventOnBankSelected;
import ir.aspacrm.my.app.mahan.events.EventOnGetBankListResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorGetBankList;
import ir.aspacrm.my.app.mahan.gson.LoadBanksResponse;

public class FragmentShowBankList extends Fragment {

    @Bind(R.id.lstBankList) RecyclerView lstBankList;
    @Bind(R.id.layLoading) LinearLayout layLoading;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.txtShowMessage) TextView txtShowMessage;

    @Bind(R.id.layBtnClose) LinearLayout layBtnClose;
    @Bind(R.id.txtPageTitle) TextView txtPageTitle;

    AdapterBank adapterBank;
    LoadBanksResponse[] banks = new LoadBanksResponse[0];

    int typeOfGrid = EnumGridType.GRID;
    int columnsOfGrid = 0;

    long factorCode;
    int money;

    public static FragmentShowBankList newInstance(long factorCode, int money) {
        Bundle args = new Bundle();
        args.putLong("FACTOR_CODE",factorCode);
        args.putInt("MONEY",money);
        FragmentShowBankList fragment = new FragmentShowBankList();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("FragmentShowBankList : onCreate()");
        EventBus.getDefault().register(this);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_bank_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d("FragmentShowBankList : onActivityCreated()");

        layLoading.setVisibility(View.GONE);

        factorCode = getArguments().getLong("FACTOR_CODE");
        money = getArguments().getInt("MONEY");

        adapterBank = new AdapterBank(banks);
        /** baraye anke moshakhas konim grid
         * ma be surate list namayesh dade sahvad ya grid chand setune*/
        typeOfGrid = G.localMemory.getInt("GRID_TYPE",1);
        if(typeOfGrid == EnumGridType.GRID){
            columnsOfGrid = getResources().getInteger(R.integer.grid_columns);
        }else{
            columnsOfGrid = getResources().getInteger(R.integer.grid_list_columns);
        }
        lstBankList.setLayoutManager(new GridLayoutManager(G.context,columnsOfGrid));
        lstBankList.setHasFixedSize(true);
        lstBankList.setAdapter(adapterBank);

        /** be dalil inke dar fragment
         *  swipeRefreshLayout.setRefreshing(true);
         *  dar ebteda kar nemikonad ke listener
         *  swipeRefreshLayout.setOnRefreshListener ra seda bezanad be surate dasti aan ra seda mizanim.*/
//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(true);
//            }
//        });
        WebService.sendGetBankList();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("FragmentShowBankList : swipeRefreshLayout onRefresh()");
                WebService.sendGetBankList();
            }
        });
        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }
    public void onEventMainThread(EventOnGetBankListResponse event) {
        Logger.d("FragmentShowBankList : EventOnGetBankListResponse is raised");
        LoadBanksResponse[] banks = event.getBankList();
        if (banks.length == 0) {
            DialogClass dlgShowErrorMessage = new DialogClass();
            dlgShowErrorMessage.showMessageDialog("پیغام", "برای شما بانکی ثبت نشده است، لطفا با پشتبانی تماس بگیرید.");
        } else {
            /** dar surati ke banki baraye moshtarak sabt shode bashad.*/
            adapterBank.updateList(banks);
        }
        layLoading.setVisibility(View.GONE);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void onEventMainThread(EventOnGetErrorGetBankList event){
        Logger.d("FragmentShowBankList : EventOnGetErrorGetBankList is raised");
        layLoading.setVisibility(View.GONE);
        DialogClass dlgShowErrorMessage = new DialogClass();
        dlgShowErrorMessage.showMessageDialog("پیغام","خطا در دریافت لیست بانک ها، لطفا مجددا تلاش کنید.");
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
//    public void onEventMainThread(EventOnBankSelected event) {
//        Logger.d("FragmentShowBankList : EventOnBankSelected is raised");
        //WebService.sendCallBankPageForPayment(factorCode,event.getCode(),"" + money);
//    }
public void onEventMainThread(EventOnBankSelected event) {
    Logger.d("FragmentShowBankList : EventOnBankSelected is raised");


    /**
     *
     *
     .add("rt", "CallBankPageForPayment")
     .add("UserID", "" + G.currentUser.userId)
     .add("ExKey", "" + G.currentUser.exKey)
     .add("FactorCode", "" + factorCode)
     .add("BankCode", "" + bankCode)
     .add("Money", "" + money)

     */

    Logger.d("Payam :" + G.currentUser.ispUrl + G.WS_PAGE
            +"?rt=CallBankPageForPayment&UserID=" + G.currentUser.userId
            +"&ExKey="+ G.currentUser.exKey
            +"&FactorCode="+ factorCode +"&BankCode="+ event.getCode()
            +"&Money=" + money);
    getActivity().getSupportFragmentManager().beginTransaction()
            .add(R.id.layFragment, FragmentBrowser.newInstance(G.currentUser.ispUrl + G.WS_PAGE
                    +"?rt=CallBankPageForPayment&UserID=" + G.currentUser.userId
                    +"&ExKey="+ G.currentUser.exKey
                    +"&FactorCode="+ factorCode+"&BankCode="+ event.getCode()
                    +"&Money=" + money))
            .addToBackStack("FragmentBrowser")
            .commit();
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
