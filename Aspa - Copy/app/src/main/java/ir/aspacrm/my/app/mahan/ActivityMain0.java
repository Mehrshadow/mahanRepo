package ir.aspacrm.my.app.mahan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.activeandroid.query.Select;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.classes.DialogClass;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.classes.U;
import ir.aspacrm.my.app.mahan.classes.WebService;
import ir.aspacrm.my.app.mahan.component.ColorTool;
import ir.aspacrm.my.app.mahan.events.EventOnAddScoreResponse;
import ir.aspacrm.my.app.mahan.events.EventOnCheckGetPollRequest;
import ir.aspacrm.my.app.mahan.events.EventOnClickedLogoutButton;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorGetNews;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorSetPoll;
import ir.aspacrm.my.app.mahan.events.EventOnGetNewsResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetPollResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetStartFactorResponse;
import ir.aspacrm.my.app.mahan.events.EventOnNoAccessServerResponse;
import ir.aspacrm.my.app.mahan.events.EventOnSendPollRequest;
import ir.aspacrm.my.app.mahan.events.EventOnSetPollResponse;
import ir.aspacrm.my.app.mahan.model.License;

import static ir.aspacrm.my.app.mahan.G.context;

public class ActivityMain0 extends AppCompatActivity implements View.OnTouchListener {

    @Bind(R.id.mask_mainbg)
    ImageView mask;
    @Bind(R.id.bg_main)
    ImageView bgMain;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.imgDrawerToggle)
    ImageView imgDrawerToggle;
    DialogClass dlgShowPoll;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main0);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        initToolbar();

        if (bgMain != null)
            bgMain.setOnTouchListener(this);

        /** yani mostaghim vared safheye asli shodeim. */
        // bareye check kardane inke bashgah darad ya na.
        if (G.currentLicense == null)
            G.currentLicense = new Select().from(License.class).where("UserId = ? ", G.currentUser.userId).executeSingle();

        if (G.currentLicense != null && !G.currentLicense.club) {
            bgMain.setImageResource(R.drawable.bg_main_no_feshfeshe_club);
            mask.setImageResource(R.drawable.mask_mainbg_no_feshfeshe_club);
        } else if (G.currentLicense != null && G.currentLicense.club) {
            bgMain.setImageResource(R.drawable.bg_main);
            mask.setImageResource(R.drawable.mask_mainbg);
        }

        WebService.sendGetUserAccountInfoRequest();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("ActivityMain : onResume()");
        G.currentActivity = this;
        G.context = this;
        G.startGpsService();
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

    public void onEventMainThread(final EventOnGetStartFactorResponse event) {
        Logger.d("ActivityMain : EventOnGetStartFactorResponse is raised");
        WebService.sendGetUserAccountInfoRequest();
    }

    public void onEventMainThread(EventOnGetErrorGetNews event) {
        Logger.d("ActivityMain : EventOnGetErrorGetNews is raised");
    }

    public void onEventMainThread(EventOnGetNewsResponse event) {
        Logger.d("ActivityMain : EventOnGetNewsResponse is raised");
    }

    public void onEventMainThread(EventOnAddScoreResponse event) {
        DialogClass showMessage = new DialogClass();

        if (event.getResponse().isResult()) {
            switch (event.getResponse().getErr()) {
                case 0:
                    showMessage.showMessageDialog("امتیاز جدید", "امتیاز مربوط به رخداد" + event.getResponse().getName() + " قبلا ثبت شده است ");
                    break;
                case 1:
                    showMessage.showMessageDialog("امتیاز جدید", "امتیاز مربوط به رخداد" + event.getResponse().getName() + " با موفقیت ثبت شد");
                    break;

                case -1:
                    showMessage.showMessageDialog("امتیاز جدید", "فرصت امتیاز گیری برای رخداد" + event.getResponse().getName() + "به چایان رسیده است ");
                    break;
            }

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("ActivityMain : onDestroy()");
        EventBus.getDefault().unregister(this);
        G.stopGpsService();
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
                else if (ct.closeMatch(Color.parseColor("#" + Integer.toHexString(context.getResources().getColor(R.color.purple))), touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowUserInfo.class));
                else if (ct.closeMatch(Color.DKGRAY, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowConnections.class));
                else if (ct.closeMatch(Color.CYAN, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowGraph.class));
                else if (ct.closeMatch(Color.GRAY, touchColor, tolerance))
                    new DialogClass().showMessageDialog(getString(R.string.future), getString(R.string.item_available_in_future));
                else if (ct.closeMatch(Color.parseColor("#" + Integer.toHexString(context.getResources().getColor(R.color.orange))), touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowTickets.class));
                else if (ct.closeMatch(Color.WHITE, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowFactors.class));
                else if (ct.closeMatch(Color.MAGENTA, touchColor, tolerance))
                    new DialogClass().showMessageDialog(getString(R.string.future), getString(R.string.item_available_in_future));
//                    startActivity(new Intent(context, ActivityShowNews.class));
                else if (ct.closeMatch(Color.RED, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowFeshfeshe.class));
                else if (ct.closeMatch(Color.parseColor("#" + Integer.toHexString(context.getResources().getColor(R.color.brown))), touchColor, tolerance)) {
                    startActivity(new Intent(context, ActivityShowCurrentService.class));
//                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    finish();
                } else if (ct.closeMatch(Color.LTGRAY, touchColor, tolerance)) {
                    if (G.currentLicense != null) {
                        if (G.currentLicense.chargeOnline) {
                            startActivity(new Intent(context, ActivityChargeOnline.class));
                        } else {
                            U.toast("امکان شارژ آنلاین برای شما فعال     نمیباشد.");
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
