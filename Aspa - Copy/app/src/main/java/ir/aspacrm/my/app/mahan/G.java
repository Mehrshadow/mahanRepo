package ir.aspacrm.my.app.mahan;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import net.time4j.android.ApplicationStarter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

import ir.aspacrm.my.app.mahan.classes.CheckNotification;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.classes.U;
import ir.aspacrm.my.app.mahan.model.Account;
import ir.aspacrm.my.app.mahan.model.Info;
import ir.aspacrm.my.app.mahan.model.License;
import ir.aspacrm.my.app.mahan.model.User;

/**
 * Created by Microsoft on 3/1/2016.
 */
public class G extends Application {
    public static Context context;
    public static final Handler handler = new Handler();
    public static User currentUser;
    public static License currentLicense;
    public static Account currentAccount;
    public static Info currentUserInfo;
    public static AppCompatActivity currentActivity;
    public static SharedPreferences localMemory;
    public static CheckNotification checkNotification;
    public static long customerId;


    /**
     * static field
     */
    public static final String DIR_APP_DOWNLOAD_FOLDER = Environment.getExternalStorageDirectory() + "/ASPA";
    public static final long NOTIFICATION_CHECKER_TIME = 20 * 60 * 1000;
    //    public static final long NOTIFICATION_CHECKER_TIME = 30 * 1000;
    public static final String JMWS = "http://mng.aspacrm.ir/service.aspx"; // WebService Jahanmir
    public static final String WS_PAGE = "/aspamobile.aspx";
    public static final NumberFormat formatterPrice = new DecimalFormat("#,###,###,###");

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        ApplicationStarter.initialize(this, true); // with prefetch on background thread

        /** initialize database*/
        ActiveAndroid.initialize(this);

        /** set SharedPreferences*/
        localMemory = getSharedPreferences("LOCAL", MODE_PRIVATE);

        /** get current User*/
        currentUser = new Select().from(User.class).where("isLogin = ? ", true).executeSingle();
        if (currentUser == null)
            currentUser = new User();

        try {
            currentUserInfo = new Select().from(Info.class).where("userId = ? ", G.currentUser.userId).executeSingle();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /** get customerId */
        customerId = getResources().getInteger(R.integer.customer_id);
//        if(customerId != 0)
//            WebService.sendGetIspUrlRequest(customerId);
//        else {
//           User lastLoginUser = new Select().from(User.class).where("isLastLogin = ? ", true).executeSingle();
//            if(lastLoginUser != null)
//                WebService.sendGetIspUrlRequest(lastLoginUser.ispId);
//        }

        U.overrideFont(getApplicationContext(), "MONOSPACE", "fonts/biran.ttf");
        U.overrideFont(getApplicationContext(), "SERIF", "fonts/iransans_medium.ttf");
        U.overrideFont(getApplicationContext(), "SANS_SERIF", "fonts/sl.ttf");

        Logger.d("G : values folder is " + getResources().getString(R.string.values_folder));

        checkNotification = new CheckNotification();
        checkNotification.SetRepeatAlarm(69, Calendar.getInstance().getTimeInMillis() + NOTIFICATION_CHECKER_TIME, NOTIFICATION_CHECKER_TIME);

    }
}
