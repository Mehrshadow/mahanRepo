package ir.aspacrm.my.app.mahan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.classes.WebService;
import ir.aspacrm.my.app.mahan.enums.EnumChargeOnlineMenuItem;
import ir.aspacrm.my.app.mahan.events.EventOnChargeOnlineMenuItemClicked;
import ir.aspacrm.my.app.mahan.events.EventOnGetChargeOnlineMainItem;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorChargeOnline;
import ir.aspacrm.my.app.mahan.events.EventOnNoAccessServerResponse;
import ir.aspacrm.my.app.mahan.gson.ChargeOnlineMainItemResponse;

public class FragmentChargeOnlineMainMenu extends Fragment implements View.OnClickListener{

    @Bind(R.id.layBtnTamdidService) CardView layBtnTamdidService;
    @Bind(R.id.layTamdidService) LinearLayout layTamdidService;
    @Bind(R.id.layBtnTaghirService) CardView layBtnTaghirService;
    @Bind(R.id.layTaghirService) LinearLayout layTaghirService;
    @Bind(R.id.layBtnTraffic) CardView layBtnTraffic;
    @Bind(R.id.layTraffic) LinearLayout layTraffic;
    @Bind(R.id.layBtnIP) CardView layBtnIP;
    @Bind(R.id.layIP) LinearLayout layIP;
    @Bind(R.id.layBtnFeshfeshe) CardView layBtnFeshfeshe;
    @Bind(R.id.layFeshfeshe) LinearLayout layFeshfeshe;
    @Bind(R.id.layShowMenuItem) CardView layShowMenuItem;
    @Bind(R.id.layLoading) LinearLayout layLoading;
    @Bind(R.id.layBtnBack) LinearLayout layBtnBack;
    @Bind(R.id.txtShowMessage) TextView txtShowMessage;


    @Bind(R.id.layBtnClose) LinearLayout layBtnClose;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("FragmentChargeOnlineMainMenu : onCreate()");
        EventBus.getDefault().register(this);
    }
    @Override
    public void onStart() {
        super.onStart();
        Logger.d("FragmentChargeOnlineMainMenu : onStart()");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.d("FragmentChargeOnlineMainMenu : onCreateView()");
        View view = inflater.inflate(R.layout.fragment_charge_online_main_menu, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        Logger.d("FragmentChargeOnlineMainMenu : onResume()");
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d("FragmentChargeOnlineMainMenu : onActivityCreated()");
        layShowMenuItem.setVisibility(View.GONE);
        layBtnTamdidService.setOnClickListener(this);
        layBtnTaghirService.setOnClickListener(this);
        layBtnTraffic.setOnClickListener(this);
        layBtnIP.setOnClickListener(this);
        layBtnFeshfeshe.setOnClickListener(this);
        WebService.sendGetChargeOnlineMainItemsRequest();
        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });
    }
    public void onEventMainThread(EventOnGetChargeOnlineMainItem event) {
        Logger.d("FragmentChargeOnlineMainMenu : EventOnGetChargeOnlineMainItem is raised.");
        layLoading.setVisibility(View.GONE);
        ChargeOnlineMainItemResponse response = event.getChargeOnlineMainItemResponse();
        if (response.Result) {
            if (!response.Feshfeshe)
                layFeshfeshe.setVisibility(View.GONE);
            if (!response.Taghir)
                layTaghirService.setVisibility(View.GONE);
            if (!response.Tamdid)
                layTamdidService.setVisibility(View.GONE);
            if (!response.Traffic)
                layTraffic.setVisibility(View.GONE);
            if (!response.Ip)
                layIP.setVisibility(View.GONE);
            layShowMenuItem.setVisibility(View.VISIBLE);
        } else {
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText(response.Msg);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layBtnTamdidService:
                EventBus.getDefault().post(new EventOnChargeOnlineMenuItemClicked(EnumChargeOnlineMenuItem.TAMDID_SERVICE));
                break;
            case R.id.layBtnTaghirService:
                EventBus.getDefault().post(new EventOnChargeOnlineMenuItemClicked(EnumChargeOnlineMenuItem.TAGHIR_SERVICE));
                break;
            case R.id.layBtnTraffic :
                EventBus.getDefault().post(new EventOnChargeOnlineMenuItemClicked(EnumChargeOnlineMenuItem.TRAFFIC));
                break;
            case R.id.layBtnIP:
                EventBus.getDefault().post(new EventOnChargeOnlineMenuItemClicked(EnumChargeOnlineMenuItem.IP));
                break;
            case R.id.layBtnFeshfeshe:
                EventBus.getDefault().post(new EventOnChargeOnlineMenuItemClicked(EnumChargeOnlineMenuItem.FESHFESHE));
                break;
        }
    }
    public void onEventMainThread(EventOnGetErrorChargeOnline event){
        Logger.d("FragmentChargeOnlineMainMenu : EventOnGetErrorChargeOnline is raised");
        layLoading.setVisibility(View.GONE);
        txtShowMessage.setVisibility(View.VISIBLE);
        txtShowMessage.setText("خطا در دریافت اطلاعات از سرور لطفا دوباره تلاش کنید.");
    }
    public void onEventMainThread(EventOnNoAccessServerResponse event) {
        Logger.d("FragmentChargeOnlineMainMenu : EventOnNoAccessServerResponse is raised");
        /** use this event to hide loading */
        layLoading.setVisibility(View.GONE);
    }
    @Override
    public void onPause() {
        super.onPause();
        Logger.d("FragmentChargeOnlineMainMenu : onPause()");
    }
    @Override
    public void onStop() {
        super.onStop();
        Logger.d("FragmentChargeOnlineMainMenu : onStop()");
    }
    @Override
    public void onDestroyView() {
//        if (view.getParent() != null) {
//            ((ViewGroup)view.getParent()).removeView(view);
//        }
        super.onDestroyView();
        ButterKnife.unbind(this);
        Logger.d("FragmentChargeOnlineMainMenu : onDestroyView()");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("FragmentChargeOnlineMainMenu : onDestroy()");
        EventBus.getDefault().unregister(this);
    }
}
