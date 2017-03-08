package ir.aspacrm.my.app.mahan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.classes.DialogClass;
import ir.aspacrm.my.app.mahan.classes.Downloader;
import ir.aspacrm.my.app.mahan.classes.GpsService;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.classes.U;
import ir.aspacrm.my.app.mahan.classes.WebService;
import ir.aspacrm.my.app.mahan.enums.EnumDownloadID;
import ir.aspacrm.my.app.mahan.enums.EnumInternetErrorType;
import ir.aspacrm.my.app.mahan.events.EventOnAddScoreResponse;
import ir.aspacrm.my.app.mahan.events.EventOnCanceledDialogUpdatingApplication;
import ir.aspacrm.my.app.mahan.events.EventOnChangedDownloadPercent;
import ir.aspacrm.my.app.mahan.events.EventOnClickedLogoutButton;
import ir.aspacrm.my.app.mahan.events.EventOnDownloadedFileCompleted;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorGetUserAccountInfo;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorRegConnect;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorUserLicense;
import ir.aspacrm.my.app.mahan.events.EventOnGetRegConnectionResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetUpdateResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetUserAccountInfoResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetUserLicenseResponse;
import ir.aspacrm.my.app.mahan.events.EventOnShowDialogUpdatingApplicationRequest;
import ir.aspacrm.my.app.mahan.model.Account;
import ir.aspacrm.my.app.mahan.model.License;
import ir.aspacrm.my.app.mahan.model.Locations;
import ir.aspacrm.my.app.mahan.model.News;

import static ir.aspacrm.my.app.mahan.G.context;

public class ActivityShowCurrentService extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.tv_package_name)
    TextView tvPackageName;
    @Bind(R.id.lay_temp_connection)
    LinearLayout layTempConnection;
    @Bind(R.id.layBtnVaslMovaghat)
    CardView btnTempConnection;
    @Bind(R.id.lay_expired)
    LinearLayout layExpired;
    @Bind(R.id.lay_remaining_days)
    LinearLayout lay_remaining_days;
    @Bind(R.id.tv_remaining_days)
    TextView tvRemainingDays;
    @Bind(R.id.lbl_remaining_days)
    TextView lblRemainingDays;
    @Bind(R.id.tv_taraze_mali)
    TextView tvTarazeMali;
    @Bind(R.id.tv_connection_status)
    TextView tvConnectionStatus;
    @Bind(R.id.btn_enter)
    CardView btnEnter;
    @Bind(R.id.layLoading)
    LinearLayout layLoading;


    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.imgDrawerToggle)
    ImageView imgDrawerToggle;

    DialogClass dlgWaiting;
    DialogClass dlgUpdate;
    Downloader downloader = null;
    DialogClass gpsDialog;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mActionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_current_service);
        ButterKnife.bind(this);
        G.currentActivity = this;
        G.context = this;
        EventBus.getDefault().register(this);

        WebService.sendGetUserAccountInfoRequest();
        WebService.sendGetLocationsRequest();

        G.startGpsService();


        initToolbar();

        setOnClickListeners();

        layLoading.setVisibility(View.INVISIBLE);
        layTempConnection.setVisibility(View.INVISIBLE);

        if (getIntent().getExtras() != null) {
            /** yani az safheye login vared safhe asli shodeim. */
//            AccountInfoResponse jsonAccountInfo = new Gson().fromJson(getIntent().getExtras().getString("JSON_ACCOUNT_INFO"),AccountInfoResponse.class);
            initializeUserAccountView();
        } else {
            /** yani mostaghim vared safheye asli shodeim. */
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // bareye check kardane inke bashgah darad ya na.
                    if (G.currentLicense == null)
                        G.currentLicense = new Select().from(License.class).where("UserId = ? ", G.currentUser.userId).executeSingle();

                    G.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            dlgWaiting = new DialogClass();
                            dlgWaiting.DialogWaitingWithBackground(ActivityShowCurrentService.this);
                        }
                    });
                    WebService.sendGetUserLicenseRequest();
                }
            }).start();

            /** check application update */
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendRequestGetNews();
                    WebService.getUpdateRequest();
                }
            }).start();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
        G.context = this;
    }


    @Override
    protected void onStop() {
        super.onStop();

    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//         toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_down));
        drawerLayout = (DrawerLayout) findViewById(R.id.main_navgdrawer);
        setupDrawerToggleInActionBar();
    }

    private void setupDrawerToggleInActionBar() {
        imgDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

        // setup the action bar properties that give us a hamburger menu
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        //  the toggle allows for the simplest of open/close handling
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.string.open,
                R.string.close);
        // drawerListener must be set before syncState is called
        drawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mActionBarDrawerToggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

    }

    private void sendRequestGetNews() {
        News lastNews = new Select()
                .from(News.class)
                .where("UserId = ? ", G.currentUser.userId)
                .orderBy("NewsID desc")
                .limit(1)
                .executeSingle();
        if (lastNews == null) {
            WebService.sendGetNewsRequest(0);
        } else {
            WebService.sendGetNewsRequest(lastNews.newsID);
        }
    }

    public void initializeUserAccountView() {

        if (dlgWaiting != null) {
            dlgWaiting.cancelDialogWaitingWithBackground();
        }
        if (G.currentLicense == null)
            G.currentLicense = new Select().from(License.class).where("UserId = ? ", G.currentUser.userId).executeSingle();

        if (G.currentAccount == null)
            G.currentAccount = new Select().from(Account.class).where("userId = ? ", G.currentUser.userId).executeSingle();

        if (G.currentAccount.regConnect) {
            /** namayesh halate vasl movaghat.*/
            lay_remaining_days.setVisibility(View.GONE);
            layExpired.setVisibility(View.VISIBLE);
            layLoading.setVisibility(View.INVISIBLE);
            layTempConnection.setVisibility(View.VISIBLE);
            tvConnectionStatus.setText(R.string.offline);
            tvTarazeMali.setText(" " + G.currentAccount.balance + "");
        } else {
            lay_remaining_days.setVisibility(View.VISIBLE);
            layLoading.setVisibility(View.INVISIBLE);
            layTempConnection.setVisibility(View.GONE);
            layExpired.setVisibility(View.GONE);
            tvConnectionStatus.setText(" " + getString(R.string.online));
            tvTarazeMali.setText(" " + G.currentAccount.balance + "");
        }

        tvPackageName.setText(G.currentAccount.pkgName.trim().length() < 10 ?
                String.format(getString(R.string.current_package_name), G.currentAccount.pkgName)
                : String.format(getString(R.string.current_package_name), "\n" + G.currentAccount.pkgName));

        if (G.currentAccount.rHour == -11111) {
            if (G.currentAccount.rDay == -11111) {
                tvRemainingDays.setText("نامحدود");
                lblRemainingDays.setVisibility(View.GONE);
            } else {
                tvRemainingDays.setText("" + G.currentAccount.rDay + " " + "روز" + " ");
            }
        } else {
            if (G.currentAccount.rHour == -11111) {
                tvRemainingDays.setText("نامحدود");
                lblRemainingDays.setVisibility(View.GONE);
            } else {
                tvRemainingDays.setText("" + G.currentAccount.rHour + " " + "ساعت" + " ");
            }
        }
//        if (G.currentAccount.rTraffic == -11111) {
//            tvRemainingDays.setText("نامحدود");
//            lblRemainingDays.setVisibility(View.GONE);
//        } else {
//            tvRemainingDays.setText(G.currentAccount.rTraffic + " " + "مگابایت" + " ");
//        }
    }

    public void onEventMainThread(EventOnGetErrorRegConnect event) {
        Logger.d("ActivityMain : EventOnGetErrorRegConnect is raised");
        layLoading.setVisibility(View.INVISIBLE);
        btnTempConnection.setClickable(true);

        if (event.getErrorType() == EnumInternetErrorType.NO_INTERNET_ACCESS) {
            U.toast("ارتباط اینترنتی خود را چک کنید.");
        } else {
            U.toast("خطا در دریافت اطلاعات از سمت سرور، لطفا مجددا تلاش کنید.");
        }
    }

    /**
     * gereftane natije darkhaste etesal movaghat.
     */
    public void onEventMainThread(EventOnGetRegConnectionResponse event) {
        Logger.d("ActivityMain : EventOnGetRegConnectionResponse is raised");
        layLoading.setVisibility(View.INVISIBLE);
        btnTempConnection.setClickable(true);

        if ((event.getRegConnectResponses().get(0)).Result) {
            WebService.sendGetUserAccountInfoRequest();
            if ((event.getRegConnectResponses().get(0)).Msg.length() != 0) {
                DialogClass dlgErrorMessage = new DialogClass();
                dlgErrorMessage.showMessageDialog("خطا در اتصال موقت", (event.getRegConnectResponses().get(0)).Msg);
            }
        } else {
            DialogClass dlgErrorMessage = new DialogClass();
            dlgErrorMessage.showMessageDialog("خطا در اتصال موقت", (event.getRegConnectResponses().get(0)).Msg);
        }
    }

    public void onEventMainThread(EventOnGetErrorUserLicense event) {
        Logger.d("ActivityMain : EventOnGetErrorUserLicense is raised.");
        initializeUserAccountView();
    }

    public void onEventMainThread(EventOnGetUserLicenseResponse event) {
        Logger.d("ActivityMain : EventOnGetUserLicenseResponse is raised.");

        if (G.currentLicense == null)
            G.currentLicense = new Select().from(License.class).where("UserId = ? ", G.currentUser.userId).executeSingle();

        WebService.sendGetUserAccountInfoRequest();
    }

    /**
     * geteftane etelaate moshtarak baraye namayesh dar safhe aval
     * bad az gereftane etelate moshtarak shamel hajme baghi mande va ruzaye baghi mande
     * darkhate ersal inke moshtarak tavasote mobile vared hesabe karbari khod shode ast ersal mishavad.
     * sepass darkhaste gereftane khabar'haye jadid ra midahim.
     */
    public void onEventMainThread(EventOnGetUserAccountInfoResponse event) {
        Logger.d("ActivityMain : EventOnGetUserAccountInfoResponse is raised");
        initializeUserAccountView();
        /** send this request to get point for current user if set
         * from managment.*/
        WebService.sendVisitMobileRequest();
    }

    public void onEventMainThread(EventOnGetErrorGetUserAccountInfo event) {
        Logger.d("ActivityMain : EventOnGetErrorGetUserAccountInfo is raised");
        G.currentAccount = new Select().from(Account.class).where("userId = ? ", G.currentUser.userId).executeSingle();
        G.currentLicense = new Select().from(License.class).where("userId = ? ", G.currentUser.userId).executeSingle();
        initializeUserAccountView();
    }

    /* Update EventBus Method */
    public void onEventMainThread(EventOnGetUpdateResponse event) {
        Logger.d("ActivityMain : EventOnGetUpdateResponse is raised");
        try {
            String version = event.getUpdateResponse().Ver;
            if (version.length() == 0)
                version = "0.0";
            if (Float.parseFloat(version) > Float.parseFloat(U.getAppVersionName())) {
                dlgUpdate = new DialogClass();
                dlgUpdate.showUpdateApplicationDialog(event.getUpdateResponse().Ver, event.getUpdateResponse().Force, event.getUpdateResponse().Url);
            } else {
                /** darkhate check karane inke nazar sanji vojud darad ya na.*/
                WebService.sendGetPollRequest();
            }
        } catch (Exception e) {
        }
    }

    public void onEventMainThread(EventOnShowDialogUpdatingApplicationRequest event) {
        Logger.d("ActivityMain : EventOnShowDialogUpdatingApplicationRequest is raised");
        if (dlgUpdate != null) {
            dlgUpdate.showUpdatingApplicationDialog(event.getNewVersion(), event.isForce(), event.getUrl());
            downloader = new Downloader();
            downloader.requestDownload(event.getUrl(), EnumDownloadID.ACTIVITY_MAIN);
        }
    }

    public void onEventMainThread(EventOnCanceledDialogUpdatingApplication event) {
        Logger.d("ActivityMain : EventOnCanceledDialogUpdatingApplication is raised");
        if (dlgUpdate != null && event.isForce()) {
            /** dar surati ke download update ejbari bashad ba cancel kardane dialog updating baz bayad
             * dialog update application namayesh dade shavad.*/
            dlgUpdate.showUpdateApplicationDialog(event.getNewVersion(), event.isForce(), event.getUrl());
            /** cancel current doanload and delete raw downloaded file*/
            downloader.cancelDownload();
        }
    }

    public void onEventMainThread(EventOnChangedDownloadPercent event) {
        Logger.d("ActivityMain : EventOnChangedDownloadPercent is raised");
        if (dlgUpdate != null) {
            dlgUpdate.changeProgressPercent(event.getPercent());
        }
    }

    public void onEventMainThread(EventOnDownloadedFileCompleted event) {
        Logger.d("ActivityMain : EventOnDownloadedFileCompleted is raised");
        if (dlgUpdate != null) {
            dlgUpdate.showInstallButton();
        }
    }

    public void onEventMainThread(EventOnClickedLogoutButton event) {
        Logger.d("ActivityMain : EventOnClickedLogoutButton is raised");
        G.currentUser.isLogin = false;
        G.currentUser.save();
        startActivity(new Intent(context, ActivityLogin.class));
        finish();
    }


    private void setOnClickListeners() {
        btnTempConnection.setOnClickListener(this);
        btnEnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layBtnVaslMovaghat:
                WebService.sendRegConnectRequest();
                btnTempConnection.setClickable(false);
                layLoading.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_enter:
                startActivity(new Intent(G.currentActivity, ActivityMain0.class));
//                overridePendingTransition(0, R.anim.right_to_left);
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("ActivityMain : onDestroy()");
        EventBus.getDefault().unregister(this);
        G.stopGpsService();
    }


    @Override
    public void onStart() {
        super.onStart();

    }
}
