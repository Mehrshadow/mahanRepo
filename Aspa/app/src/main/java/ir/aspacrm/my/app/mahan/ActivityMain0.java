package ir.aspacrm.my.app.mahan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.activeandroid.query.Select;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.classes.DialogClass;
import ir.aspacrm.my.app.mahan.classes.Downloader;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.classes.U;
import ir.aspacrm.my.app.mahan.classes.WebService;
import ir.aspacrm.my.app.mahan.component.ColorTool;
import ir.aspacrm.my.app.mahan.enums.EnumDownloadID;
import ir.aspacrm.my.app.mahan.events.EventOnCanceledDialogUpdatingApplication;
import ir.aspacrm.my.app.mahan.events.EventOnChangedDownloadPercent;
import ir.aspacrm.my.app.mahan.events.EventOnCheckGetPollRequest;
import ir.aspacrm.my.app.mahan.events.EventOnClickedLogoutButton;
import ir.aspacrm.my.app.mahan.events.EventOnDownloadedFileCompleted;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorGetIspInfo;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorGetUserAccountInfo;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorSetPoll;
import ir.aspacrm.my.app.mahan.events.EventOnGetIspInfoResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetPollResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetStartFactorResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetUpdateResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetUserAccountInfoResponse;
import ir.aspacrm.my.app.mahan.events.EventOnNoAccessServerResponse;
import ir.aspacrm.my.app.mahan.events.EventOnSendPollRequest;
import ir.aspacrm.my.app.mahan.events.EventOnSetPollResponse;
import ir.aspacrm.my.app.mahan.events.EventOnShowDialogUpdatingApplicationRequest;
import ir.aspacrm.my.app.mahan.gson.GetIspInfoResponse;
import ir.aspacrm.my.app.mahan.model.Account;
import ir.aspacrm.my.app.mahan.model.License;

import static ir.aspacrm.my.app.mahan.G.context;

public class ActivityMain0 extends AppCompatActivity implements View.OnTouchListener {

    @Bind(R.id.mask_mainbg)
    ImageView mask;
    @Bind(R.id.bg_main)
    ImageView bgMain;

    int level = 0;
    DialogClass dlgUpdate;
    DialogClass dlgShowPoll;
    Downloader downloader = null;
    boolean downloadedCompleted;

    DialogClass dlgWaiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main0);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        if (bgMain != null)
            bgMain.setOnTouchListener(this);

        if (getIntent().getExtras() != null) {
            /** yani az safheye login vared safhe asli shodeim. */
            //AccountInfoResponse jsonAccountInfo = new Gson().fromJson(getIntent().getExtras().getString("JSON_ACCOUNT_INFO"),AccountInfoResponse.class);
            initializeUserAccountView();
        } else {
            /** yani mostaghim vared safheye asli shodeim. */
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // bareye check kardane inke bashgah darad ya na.
                    if (G.currentLicense == null)
                        G.currentLicense = new Select().from(License.class).where("UserId = ? ", G.currentUser.userId).executeSingle();

//                    G.handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            dlgWaiting = new DialogClass();
//                            dlgWaiting.DialogWaitingWithBackground(ActivityMain0.this);
//                        }
//                    });
                }
            }).start();
        }
        /** check application update */
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebService.getUpdateRequest();
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("ActivityMain : onResume()");
        G.currentActivity = this;
    }

//    @Override
//    public void onClick(final View view) {
//        Animation clickAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_click);
//        clickAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                switch (view.getId()) {
//                    case imgLogout:
//                        DialogClass dialogExit = new DialogClass();
//                        dialogExit.showExitDialog();
//                        break;
//                    case imgChangePassword:
//                        if (G.currentLicense != null) {
//                            if (G.currentLicense.changePass) {
//                                DialogClass dialogChangePassword = new DialogClass();
//                                dialogChangePassword.showChangePasswordDialog();
//                            } else {
//                                U.toast("امکان تغییر رمز برای شما فعال نیست.");
//                            }
//                        }
//                        break;
//                    case layPardakhtha:
//                        startActivity(new Intent(G.context, ActivityPayments.class));
//                        break;
//                    case laySuratHesab:
//                        startActivity(new Intent(G.context, ActivityShowFactors.class));
//                        break;
//                    case laySavabeghEtesal:
//                        startActivity(new Intent(G.context, ActivityShowConnections.class));
//                        break;
//                    case layTicket:
//                        if (G.currentLicense != null) {
//                            if (G.currentLicense.ticket) {
//                                startActivity(new Intent(G.context, ActivityShowTickets.class));
//                            } else {
//                                U.toast("امکان ارسال تیکت برای شما فعال نمی باشد.");
//                            }
//                        }
//                        break;
//                    case layNemudarMasraf:
//                        startActivity(new Intent(G.context, ActivityShowGraph.class));
//                        break;
//                    case layChargeOnline:
//                        if (G.currentLicense != null) {
//                            if (G.currentLicense.chargeOnline) {
//                                startActivity(new Intent(G.context, ActivityChargeOnline.class));
//                            } else {
//                                U.toast("امکان شارژ آنلاین برای شما فعال نمیباشد.");
//                            }
//                        }
//                        break;
//                    case imgUserInfo:
//                        startActivity(new Intent(G.context, ActivityShowUserInfo.class));
//                        break;
//                    case imgNews:
//                        startActivity(new Intent(G.context, ActivityShowNews.class));
//                        break;
//                    case imgNotification:
//                        startActivity(new Intent(G.context, ActivityShowNotify.class));
//                        break;
//                    case imgCompanyInfo:
//                        WebService.sendGetIspInfoRequest();
//                        break;
//                    case imgFeshfeshe:
//                        startActivity(new Intent(G.context, ActivityShowFeshfeshe.class));
//                        break;
//                    case imgClub:
//                        startActivity(new Intent(G.context, ActivityShowClubScores.class));
//                        break;
//                }
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        view.startAnimation(clickAnimation);
//    }

    /**
     * gereftane about sherkat bad az darkhaste etelaate sherkat dar ghesmate menu paein
     * bad az gereftane javab az webserice, vared in ghesmat mishavim.
     */
    public void onEventMainThread(EventOnGetIspInfoResponse event) {
        Logger.d("ActivityMain : EventOnGetIspInfoResponse is raised");
        GetIspInfoResponse response = event.getIspInfo();
        if (response.Result) {
            DialogClass dlgShowCompanyInfo = new DialogClass();
            dlgShowCompanyInfo.showCompanyDetailDialog(response);
        } else {
            U.toast("خطا در دریافت اطلاعات از سرور");
        }
    }

    public void onEventMainThread(EventOnGetErrorGetIspInfo event) {
        Logger.d("ActivityMain : EventOnGetErrorGetIspInfo is raised");
        G.currentAccount = new Select().from(Account.class).where("userId = ? ", G.currentUser.userId).executeSingle();
        G.currentLicense = new Select().from(License.class).where("userId = ? ", G.currentUser.userId).executeSingle();
        initializeUserAccountView();
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

    public void onEventMainThread(EventOnNoAccessServerResponse event) {
        if (dlgShowPoll != null)
            dlgShowPoll.showErrorOnPollDialog("لطفا ارتباط اینترنتی خود را چک کرده و سپس مجددا تلاش کنید.");
    }

    public void onEventMainThread(EventOnClickedLogoutButton event) {
        Logger.d("ActivityMain : EventOnClickedLogoutButton is raised");
        G.currentUser.isLogin = false;
        G.currentUser.save();
        startActivity(new Intent(context, ActivityLogin.class));
        finish();
    }

    public void onEventMainThread(EventOnSendPollRequest event) {
        Logger.d("ActivityMain : EventOnSendPollRequest is raised");
        WebService.sendSetPollRequest(event.getPollId(), event.getOptionId(), event.getDes());
    }

    public void onEventMainThread(EventOnSetPollResponse event) {
        Logger.d("ActivityMain : EventOnSetPollResponse is raised");
        if (event.getStatus()) {
            /** dar surati ke pasukhe ma be dorosti sabt shode bashad.*/
            if (dlgShowPoll != null)
                dlgShowPoll.cancelPollDialog();
            U.toast("نظر شما با موفقیت ثبت شد.");
        } else {
            if (dlgShowPoll != null)
                dlgShowPoll.showErrorOnPollDialog("ثبت نظر با مشکل مواجه شد، لطفا دوباره تلاش کنید.");
        }

    }

    public void onEventMainThread(EventOnGetErrorSetPoll event) {
        Logger.d("ActivityMain : EventOnGetErrorSetPoll is raised");
        if (dlgShowPoll != null)
            dlgShowPoll.showErrorOnPollDialog("ثبت نظر با مشکل مواجه شد، لطفا دوباره تلاش کنید.");
    }

    public void onEventMainThread(EventOnGetPollResponse event) {
        Logger.d("ActivityMain : EventOnGetPollResponse is raised");
        if (event.getPollResponse().Result) {
            dlgShowPoll = new DialogClass();
            dlgShowPoll.showPollDialog(event.getPollResponse());
        }
    }

    public void onEventMainThread(EventOnCheckGetPollRequest event) {
        Logger.d("ActivityMain : EventOnCheckGetPollRequest is raised");
        /** darkhate check karane inke nazar sanji vojud darad ya na.*/
        WebService.sendGetPollRequest();
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
            downloadedCompleted = true;
            dlgUpdate.showInstallButton();
        }
    }

    public void onEventMainThread(final EventOnGetStartFactorResponse event) {
        Logger.d("ActivityMain : EventOnGetStartFactorResponse is raised");
        WebService.sendGetUserAccountInfoRequest();
    }

    /*-------------------------------------------------------------------------------------------------------------*/
    public void initializeUserAccountView() {

        if (dlgWaiting != null) {
            dlgWaiting.cancelDialogWaitingWithBackground();
        }

        if (G.currentAccount == null)
            G.currentAccount = new Select().from(Account.class).where("userId = ? ", G.currentUser.userId).executeSingle();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("ActivityMain : onDestroy()");
        EventBus.getDefault().unregister(this);
    }

    public boolean onTouch(View v, MotionEvent ev) {

        final int action = ev.getAction();

        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();

        // If we cannot find the imageView, return.
        ImageView bgMain = (ImageView) v.findViewById(R.id.bg_main);
        if (bgMain == null) return false;

        // When the action is Down, see if we should show the "pressed" image for the default image.
        // We do this when the default image is showing. That condition is detectable by looking at the
        // tag of the view. If it is null or contains the resource number of the default image, display the pressed image.

        // Now that we know the current resource being displayed we can handle the DOWN and UP events.

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Logger.d("action down");
                break;

            case MotionEvent.ACTION_UP:
                Logger.d("action up");
                ColorTool ct = new ColorTool();
                int tolerance = 25;
                int touchColor = getHotspotColor(R.id.mask_mainbg, evX, evY);

                if (ct.closeMatch(Color.BLUE, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityPayments.class));
                else if (ct.closeMatch(Color.YELLOW, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowNotify.class));
                else if (ct.closeMatch(Color.GREEN, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowClubScores.class));
                else if (ct.closeMatch(Color.BLACK, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowUserInfo.class));
                else if (ct.closeMatch(Color.DKGRAY, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowConnections.class));
                else if (ct.closeMatch(Color.CYAN, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowGraph.class));
                else if (ct.closeMatch(Color.GRAY, touchColor, tolerance))
                    Logger.d("Speed test");
                else if (ct.closeMatch(Color.parseColor("#"+Integer.toHexString(context.getResources().getColor(R.color.orange))), touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowTickets.class));
                else if (ct.closeMatch(Color.WHITE, touchColor, tolerance))
                    Logger.d("Free internet");
                else if (ct.closeMatch(Color.MAGENTA, touchColor, tolerance))
                    Logger.d("Game");
                else if (ct.closeMatch(Color.RED, touchColor, tolerance))
                    Logger.d("Special offer");
                else if (ct.closeMatch(Color.LTGRAY, touchColor, tolerance)) {
                    if (G.currentLicense != null) {
                        if (G.currentLicense.chargeOnline) {
                            startActivity(new Intent(context, ActivityChargeOnline.class));
                        } else {
                            U.toast("امکان شارژ آنلاین برای شما فعال نمیباشد.");
                        }
                    }
                }

                break;
        } // end switch
        return true;
    }

    public int getHotspotColor(int hotspotId, int x, int y) {
        ImageView img = (ImageView) findViewById(hotspotId);
        if (img == null) {
            Logger.d("Hot spot image not found");
            return 0;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspots == null) {
                Logger.d("Hot spot bitmap was not created");
                return 0;
            } else {
                img.setDrawingCacheEnabled(false);
                return hotspots.getPixel(x, y);
            }
        }
    }
}
