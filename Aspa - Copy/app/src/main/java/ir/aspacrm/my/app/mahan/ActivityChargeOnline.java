package ir.aspacrm.my.app.mahan;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.classes.DialogClass;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.classes.U;
import ir.aspacrm.my.app.mahan.classes.WebService;
import ir.aspacrm.my.app.mahan.enums.EnumChargeOnlineMenuItem;
import ir.aspacrm.my.app.mahan.enums.EnumInternetErrorType;
import ir.aspacrm.my.app.mahan.events.EventOnChargeOnlineMenuItemClicked;
import ir.aspacrm.my.app.mahan.events.EventOnClickedChargeOnlineGroup;
import ir.aspacrm.my.app.mahan.events.EventOnClickedStartFactor;
import ir.aspacrm.my.app.mahan.events.EventOnGetBankPageResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetChargeOnlineForSelectPackage;
import ir.aspacrm.my.app.mahan.events.EventOnGetChargeOnlineForTamdid;
import ir.aspacrm.my.app.mahan.events.EventOnGetCheckChargeOnlineClub;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorCallBankPage;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorCheckChargeOnlineClub;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorStartFactor;
import ir.aspacrm.my.app.mahan.events.EventOnGetStartFactorResponse;
import ir.aspacrm.my.app.mahan.events.EventOnNoAccessServerResponse;
import ir.aspacrm.my.app.mahan.events.EventOnShowFragmentBankListRequest;
import ir.aspacrm.my.app.mahan.events.EventOnShowFragmentChargeOnlineGroupRequest;
import ir.aspacrm.my.app.mahan.events.EventOnShowFragmentChargeOnlinePackageRequest;
import ir.aspacrm.my.app.mahan.fragment.FragmentBrowser;
import ir.aspacrm.my.app.mahan.fragment.FragmentChargeOnlineGroup;
import ir.aspacrm.my.app.mahan.fragment.FragmentChargeOnlineGroupPackage;
import ir.aspacrm.my.app.mahan.fragment.FragmentChargeOnlineMainMenu;
import ir.aspacrm.my.app.mahan.fragment.FragmentSelectAaddiBashgah;
import ir.aspacrm.my.app.mahan.fragment.FragmentShowBankList;
import ir.aspacrm.my.app.mahan.fragment.FragmentShowFactorDetail;

public class ActivityChargeOnline extends AppCompatActivity {
    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;
    @Bind(ir.aspacrm.my.app.mahan.R.id.layFragment)
    FrameLayout layFragment;
    @Bind(ir.aspacrm.my.app.mahan.R.id.layLoading)
    LinearLayout layLoading;

    FragmentManager fragmentManager;
    DialogClass dlgMessage;

    public static boolean isReturnedFromBrowser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ir.aspacrm.my.app.mahan.R.layout.activity_charge_online);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, ir.aspacrm.my.app.mahan.R.color.dark_dark_grey));


        layLoading.setVisibility(View.GONE);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(ir.aspacrm.my.app.mahan.R.id.layFragment, new FragmentChargeOnlineMainMenu())
                .addToBackStack("FragmentChargeOnlineMainMenu")
                .commit();
        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * darkhaste inke mikhahim yek factor ra start konim az tarafe adapterFactor
     */
    public void onEventMainThread(EventOnClickedStartFactor event) {
        Logger.d("ActivityChargeOnline : EventOnClickedStartFactor is raised");
        WebService.sendStartFactorRequest(event.getFactorId());
        layLoading.setVisibility(View.VISIBLE);
    }

    /**
     * daryaft pasokhe darkhaste start factor
     */
    public void onEventMainThread(final EventOnGetStartFactorResponse event) {
        Logger.d("ActivityChargeOnline : EventOnGetStartFactorResponse is raised");
        layLoading.setVisibility(View.GONE);

        final Dialog dialog = new Dialog(G.currentActivity, ir.aspacrm.my.app.mahan.R.style.DialogSlideAnim);
        dialog.setContentView(ir.aspacrm.my.app.mahan.R.layout.dialog_show_message);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView imgCloseDialog = (ImageView) dialog.findViewById(ir.aspacrm.my.app.mahan.R.id.imgCloseDialog);
        TextView txtMessageTitle = (TextView) dialog.findViewById(ir.aspacrm.my.app.mahan.R.id.txtMessageTitle);
        TextView txtMessageDescription = (TextView) dialog.findViewById(ir.aspacrm.my.app.mahan.R.id.txtMessageDescription);

        txtMessageTitle.setText("" + "وضعیت شروع فاکتور");
        txtMessageDescription.setText("" + event.getMessage());
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (event.getStatus()) {
                    //finish();
                    for (int i = 0; i < fragmentManager.getBackStackEntryCount() - 1; ++i) {
                        fragmentManager.popBackStack();
                    }
                }
            }
        });
        dialog.show();
    }

    public void onEventMainThread(EventOnGetErrorStartFactor event) {
        Logger.d("ActivityShowPayments : EventOnGetErrorStartFactor is raised");
        layLoading.setVisibility(View.GONE);
        if (event.getErrorType() == EnumInternetErrorType.NO_INTERNET_ACCESS) {
            U.toast("ارتباط اینترنتی خود را چک کنید.");
        } else {
            U.toast("خطا در دریافت اطلاعات از سرور");
        }
    }

    public void onEventMainThread(EventOnChargeOnlineMenuItemClicked event) {
        Logger.d("ActivityChargeOnline : EventOnChargeOnlineMenuItemClicked is raised.");
        int whichMenuItem = event.getWhichMenuItem();
        layLoading.setVisibility(View.VISIBLE);
        WebService.sendCheckChargeOnlineClubRequest(whichMenuItem);
    }

    public void onEventMainThread(EventOnGetCheckChargeOnlineClub event) {
        Logger.d("ActivityChargeOnline : EventOnGetCheckChargeOnlineClub is raised.");
        layLoading.setVisibility(View.GONE);
        boolean status = event.getStatus();
        if (status) {
            int whichMenuItem = event.getWhichMenuItem();
            boolean isClub = event.getIsClub();
            if (isClub) {
                fragmentManager
                        .beginTransaction()
                        .add(ir.aspacrm.my.app.mahan.R.id.layFragment, FragmentSelectAaddiBashgah.newInstance(whichMenuItem))
                        .addToBackStack("FragmentSelectAaddiBashgah")
                        .commit();
            } else {
                switch (whichMenuItem) {
                    case EnumChargeOnlineMenuItem.TAMDID_SERVICE:
                        WebService.sendChargeOnlineForTamdidRequest(0);
                        break;
                    case EnumChargeOnlineMenuItem.TAGHIR_SERVICE:
                        fragmentManager
                                .beginTransaction()
                                .add(ir.aspacrm.my.app.mahan.R.id.layFragment, FragmentChargeOnlineGroup.newInstance(0, EnumChargeOnlineMenuItem.TAGHIR_SERVICE))
                                .addToBackStack("FragmentChargeOnlineGroup")
                                .commit();
                        break;
                    case EnumChargeOnlineMenuItem.TRAFFIC:
                        fragmentManager.beginTransaction()
                                .add(ir.aspacrm.my.app.mahan.R.id.layFragment, FragmentChargeOnlineGroupPackage.newInstance(0, event.getWhichMenuItem(), G.currentAccount.grpId))
                                .addToBackStack("FragmentChargeOnlineGroup")
                                .commit();
                        break;
                    case EnumChargeOnlineMenuItem.IP:
                        fragmentManager.beginTransaction()
                                .add(ir.aspacrm.my.app.mahan.R.id.layFragment, FragmentChargeOnlineGroupPackage.newInstance(0, event.getWhichMenuItem(), G.currentAccount.grpId))
                                .addToBackStack("FragmentChargeOnlineGroup")
                                .commit();
                        break;
                    case EnumChargeOnlineMenuItem.FESHFESHE:
                        fragmentManager.beginTransaction()
                                .add(ir.aspacrm.my.app.mahan.R.id.layFragment, FragmentChargeOnlineGroupPackage.newInstance(0, event.getWhichMenuItem(), G.currentAccount.grpId))
                                .addToBackStack("FragmentChargeOnlineGroup")
                                .commit();
                        break;
                }
            }
        } else {
            dlgMessage = new DialogClass();
            dlgMessage.showMessageDialog("خطا", "خطا در دریافت اطلاعاتی دریافتی از سمت سرور، لطفا دوباره تلاش کنید.");
        }
    }

    public void onEventMainThread(EventOnGetErrorCheckChargeOnlineClub event) {
        Logger.d("ActivityChargeOnline : EventOnGetErrorCheckChargeOnlineClub is raised.");
        layLoading.setVisibility(View.GONE);
    }

    public void onEventMainThread(EventOnGetChargeOnlineForTamdid event) {
        Logger.d("ActivityShowGraph : EventOnGetChargeOnlineForTamdid is raised");
        boolean result = event.isResult();
        String message = event.getMessage();
        long factorCode = event.getFactorCode();
        if (message.length() == 0) {
            fragmentManager.beginTransaction()
                    .add(ir.aspacrm.my.app.mahan.R.id.layFragment, FragmentShowFactorDetail.newInstance(factorCode))
                    .addToBackStack("FragmentShowFactorDetail")
                    .commit();
        } else {
            DialogClass dlgShow = new DialogClass();
            dlgShow.showMessageDialog("پیغام", "" + message);
        }
    }

    public void onEventMainThread(EventOnShowFragmentChargeOnlineGroupRequest event) {
        Logger.d("ActivityChargeOnline : EventOnShowFragmentChargeOnlineGroupRequest is raised");
        fragmentManager.beginTransaction()
                .add(ir.aspacrm.my.app.mahan.R.id.layFragment, FragmentChargeOnlineGroup.newInstance(event.getIsClub(), event.getWhichMenuItem()))
                .addToBackStack("FragmentChargeOnlineGroup")
                .commit();
    }

    public void onEventMainThread(EventOnShowFragmentChargeOnlinePackageRequest event) {
        Logger.d("ActivityChargeOnline : EventOnShowFragmentChargeOnlinePackegeRequest is raised");
        fragmentManager.beginTransaction()
                .add(ir.aspacrm.my.app.mahan.R.id.layFragment, FragmentChargeOnlineGroupPackage.newInstance(event.getIsClub(), event.getWhichMenuItem(), G.currentAccount.grpId))
                .addToBackStack("FragmentChargeOnlineGroup")
                .commit();
    }

    public void onEventMainThread(EventOnClickedChargeOnlineGroup event) {
        Logger.d("ActivityChargeOnline : EventOnClickedChargeOnlineGroup is raised");
        fragmentManager.beginTransaction()
                .add(ir.aspacrm.my.app.mahan.R.id.layFragment, FragmentChargeOnlineGroupPackage.newInstance(event.getIsClub(), event.getWhichMenuItem(), event.getGroupCode()))
                .addToBackStack("FragmentChargeOnlineGroupPackage")
                .commit();
    }

    public void onEventMainThread(EventOnGetChargeOnlineForSelectPackage event) {
        Logger.d("ActivityChargeOnline : EventOnGetChargeOnlineForSelectPackage is raised");
        boolean result = event.isResult();
        String message = event.getMessage();
        long factorCode = event.getFactorCode();
        if (result) {
            if (message.length() == 0) {
                fragmentManager.beginTransaction()
                        .add(ir.aspacrm.my.app.mahan.R.id.layFragment, FragmentShowFactorDetail.newInstance(factorCode))
                        .addToBackStack("FragmentShowFactorDetail")
                        .commit();
            } else {
                DialogClass dlgShow = new DialogClass();
                dlgShow.showMessageDialog("پیغام", "" + message);
            }
        } else {
            DialogClass dlgShow = new DialogClass();
            dlgShow.showMessageDialog("پیغام", "" + message);
        }
    }

    public void onEventMainThread(EventOnShowFragmentBankListRequest event) {
        Logger.d("ActivityChargeOnline : EventOnShowFragmentBankListRequest is raised");
        fragmentManager.beginTransaction()
                .add(ir.aspacrm.my.app.mahan.R.id.layFragment, FragmentShowBankList.newInstance(event.getFactorCode(), event.getMoney()))
                .addToBackStack("FragmentShowBankList")
                .commit();

    }

    public void onEventMainThread(EventOnGetBankPageResponse event) {
        Logger.d("ActivityChargeOnline : EventOnGetBankPageResponse is raised");
        fragmentManager.beginTransaction()
                .add(ir.aspacrm.my.app.mahan.R.id.layFragment, FragmentBrowser.newInstance(event.getBankUrl()))
                .addToBackStack("FragmentBrowser")
                .commit();

    }

    public void onEventMainThread(EventOnGetErrorCallBankPage event) {
        Logger.d("ActivityChargeOnline : EventOnGetErrorCallBankPage is raised");

        layLoading.setVisibility(View.GONE);
        if (event.getErrorType() == EnumInternetErrorType.NO_INTERNET_ACCESS) {
            DialogClass dlg = new DialogClass();
            dlg.showMessageDialog("خطا", "عدم دسترسی به اینترنت، لطفا ارتباط اینترنتی خود را چک کنید.");
        } else {
            DialogClass dlg = new DialogClass();
            dlg.showMessageDialog("خطا", "خطا در دریافت اطلاعات از سرور، لطفا مجددا تلاش کنید.");
        }

    }

    public void onEventMainThread(EventOnNoAccessServerResponse event) {
        Logger.d("ActivityChargeOnline : EventOnNoAccessServerResponse is raised.");
        layLoading.setVisibility(View.GONE);
        dlgMessage = new DialogClass();
        dlgMessage.showMessageDialog("خطا", "عدم دسترسی به اینترنت، لطفا ارتباط اینترنتی خود را چک کرده و دوباره تلاش کنید.");
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

    @Override
    public void onBackPressed() {
        Logger.d("ActivityChargeOnline : onBackPressed()");
        if (fragmentManager.getBackStackEntryCount() == 1)
            finish();
        else if (isReturnedFromBrowser) {
            isReturnedFromBrowser = false;
            startActivity(new Intent(G.currentActivity, ActivityShowCurrentService.class));
            finish();
        } else
            super.onBackPressed();
    }
}
