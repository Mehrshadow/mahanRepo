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
import ir.aspacrm.my.app.mahan.classes.U;
import ir.aspacrm.my.app.mahan.classes.WebService;
import ir.aspacrm.my.app.mahan.enums.EnumChargeOnlineMenuItem;
import ir.aspacrm.my.app.mahan.events.EventOnGetChargeOnlineForTamdid;
import ir.aspacrm.my.app.mahan.events.EventOnShowFragmentChargeOnlineGroupRequest;
import ir.aspacrm.my.app.mahan.events.EventOnShowFragmentChargeOnlinePackageRequest;

public class FragmentSelectAaddiBashgah extends Fragment {

    @Bind(R.id.layBtnAaddi) CardView layBtnAaddi;
    @Bind(R.id.layAaddi) LinearLayout layAaddi;
    @Bind(R.id.layBtnBashgah) CardView layBtnBashgah;
    @Bind(R.id.layBashgah) LinearLayout layBashgah;
    @Bind(R.id.layLoading) LinearLayout layLoading;
    @Bind(R.id.txtShowMessage) TextView txtShowMessage;

    @Bind(R.id.layBtnClose) LinearLayout layBtnClose;
    @Bind(R.id.txtPageTitle) TextView txtPageTitle;

    int whichMenuItem;
    public static FragmentSelectAaddiBashgah newInstance(int whichMenuItem) {
        Bundle args = new Bundle();
        args.putInt("WHICH_MENU_ITEM", whichMenuItem);
        FragmentSelectAaddiBashgah fragment = new FragmentSelectAaddiBashgah();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_aaddi_bashgah, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layLoading.setVisibility(View.GONE);
        whichMenuItem = getArguments().getInt("WHICH_MENU_ITEM");

        txtPageTitle.setText(U.getMenuItemName(whichMenuItem));
        layBtnAaddi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layLoading.setVisibility(View.VISIBLE);
                switch (whichMenuItem) {
                    case EnumChargeOnlineMenuItem.TAMDID_SERVICE:
                        WebService.sendChargeOnlineForTamdidRequest(0);
                        break;
                    case EnumChargeOnlineMenuItem.TAGHIR_SERVICE:
                        EventBus.getDefault().post(new EventOnShowFragmentChargeOnlineGroupRequest(0, EnumChargeOnlineMenuItem.TAGHIR_SERVICE));
                        break;
                    case EnumChargeOnlineMenuItem.TRAFFIC:
                        EventBus.getDefault().post(new EventOnShowFragmentChargeOnlinePackageRequest(0, EnumChargeOnlineMenuItem.TRAFFIC));
                        break;
                    case EnumChargeOnlineMenuItem.IP:
                        EventBus.getDefault().post(new EventOnShowFragmentChargeOnlinePackageRequest(0, EnumChargeOnlineMenuItem.IP));
                        break;
                    case EnumChargeOnlineMenuItem.FESHFESHE:
                        EventBus.getDefault().post(new EventOnShowFragmentChargeOnlinePackageRequest(0, EnumChargeOnlineMenuItem.FESHFESHE));
                        break;
                }
            }
        });
        layBtnBashgah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layLoading.setVisibility(View.VISIBLE);
                switch (whichMenuItem) {
                    case EnumChargeOnlineMenuItem.TAMDID_SERVICE:
                        WebService.sendChargeOnlineForTamdidRequest(1);
                        break;
                    case EnumChargeOnlineMenuItem.TAGHIR_SERVICE:
                        EventBus.getDefault().post(new EventOnShowFragmentChargeOnlineGroupRequest(1, EnumChargeOnlineMenuItem.TAGHIR_SERVICE));
                        break;
                    case EnumChargeOnlineMenuItem.TRAFFIC:
                        EventBus.getDefault().post(new EventOnShowFragmentChargeOnlinePackageRequest(1, EnumChargeOnlineMenuItem.TRAFFIC));
                        break;
                    case EnumChargeOnlineMenuItem.IP:
                        EventBus.getDefault().post(new EventOnShowFragmentChargeOnlinePackageRequest(1, EnumChargeOnlineMenuItem.IP));
                        break;
                    case EnumChargeOnlineMenuItem.FESHFESHE:
                        EventBus.getDefault().post(new EventOnShowFragmentChargeOnlinePackageRequest(1, EnumChargeOnlineMenuItem.FESHFESHE));
                        break;
                }
            }
        });

        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }
    public void onEventMainThread(EventOnGetChargeOnlineForTamdid event){
        Logger.d("FragmentSelectAaddiBashgah : EventOnGetChargeOnlineForTamdid is raised");
        /** use this event to hide loading */
        layLoading.setVisibility(View.GONE);
    }
    public void onEventMainThread(EventOnShowFragmentChargeOnlineGroupRequest event) {
        Logger.d("FragmentSelectAaddiBashgah : EventOnShowFragmentChargeOnlineGroup is raised");
        /** use this event to hide loading */
        layLoading.setVisibility(View.GONE);
    }
    public void onEventMainThread(EventOnShowFragmentChargeOnlinePackageRequest event) {
        Logger.d("FragmentSelectAaddiBashgah : EventOnShowFragmentChargeOnlinePackageRequest is raised");
        /** use this event to hide loading */
        layLoading.setVisibility(View.GONE);
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
