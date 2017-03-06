package ir.aspacrm.my.app.mahan.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activeandroid.query.Select;

import net.time4j.SystemClock;
import net.time4j.calendar.PersianCalendar;
import net.time4j.format.DisplayMode;
import net.time4j.format.expert.ChronoFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.adapter.AdapterDrawerRecycler;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.classes.WebService;
import ir.aspacrm.my.app.mahan.component.PersianTextViewBold;
import ir.aspacrm.my.app.mahan.component.PersianTextViewNormal;
import ir.aspacrm.my.app.mahan.events.EventOnGetClubScoreResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorGetUserAccountInfo;
import ir.aspacrm.my.app.mahan.events.EventOnGetUserAccountInfoResponse;
import ir.aspacrm.my.app.mahan.model.Account;
import ir.aspacrm.my.app.mahan.model.License;

/**
 * Created by HaMiD on 1/22/2017.
 */

public class FragmentDrawer extends Fragment {

    private View view;
    private RecyclerView RecyDrawer;
    List<String> title;
    PersianTextViewNormal txtStatus, txtScore, txtName, txtRemainDay2;
    PersianTextViewNormal txtRemainDay;
    LinearLayout layTotalClubScore;
    ProgressBar prgLoadingScore;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drawer, container, false);
        initView();
        EventBus.getDefault().register(this);
        WebService.sendGetClubScoreRequest();
        WebService.sendGetUserAccountInfoRequest();
        return view;
    }

    private void initView() {
        txtRemainDay = (PersianTextViewNormal) view.findViewById(R.id.txtRemainDay);
        txtRemainDay2 = (PersianTextViewNormal) view.findViewById(R.id.txtRemainDay2);

        txtScore = (ir.aspacrm.my.app.mahan.component.PersianTextViewNormal) view.findViewById(R.id.txtScore);

        prgLoadingScore = (ProgressBar) view.findViewById(R.id.prgLoadingScore);

        title = new ArrayList<>();
        title.add(getString(R.string.profile));
        title.add(getString(R.string.connectionReport));
        title.add(getString(R.string.onlineSharj));
        title.add(getString(R.string.payReport));
        title.add(getString(R.string.consumechart));
        title.add(getString(R.string.speedTest));
        title.add(getString(R.string.tickets));
        title.add(getString(R.string.inviteFriends));
        title.add(getString(R.string.pointReports));
        title.add(getString(R.string.messages));
        title.add(getString(R.string.games));
        title.add(getString(R.string.special_offer));
        title.add(getString(R.string.news));
        title.add(getString(R.string.exit));

        RecyDrawer = (RecyclerView) view.findViewById(R.id.RecyDrawer);
        RecyDrawer.setLayoutManager(new LinearLayoutManager(G.context, LinearLayoutManager.VERTICAL, false));
        RecyDrawer.setHasFixedSize(true);

        AdapterDrawerRecycler adapterDrawerRecycler = new AdapterDrawerRecycler(title, G.context);
        RecyDrawer.setAdapter(adapterDrawerRecycler);


    }

    private void initializeUserAccountView() {


        // current date
        PersianCalendar jalali = SystemClock.inLocalView().now(PersianCalendar.axis());
        String fDate = jalali.toString();// AP-1394-08-04

//        // localized format of tomorrow (English and Farsi)
//        ChronoFormatter<PersianCalendar> f =
//                ChronoFormatter.ofStyle(DisplayMode.FULL, Locale.ENGLISH, PersianCalendar.axis());
//        Locale farsi = new Locale("fa");
//        String da = f.with(farsi).format(jalali);
//
//        String[] separated0 = da.split(",");
//        String date = separated0[0];
//        String dayOfWeek = separated0[1];
//
//        String[] separated = date.split(" ");
//        String day = separated[3];
//        String month= separated[2];
//        String year= separated[1];
//
//        String farsiDate = dayOfWeek+","+day +" "+ month+" "+year;

        String[] separated = fDate.split("-");
        String day = separated[3];
        String month= separated[2];
        String year= separated[1];
        String farsiDate = year +" /"+ month+" /"+day;





//        txtRemainDay1.setText(dayOfWeek);
        txtRemainDay2.setText(farsiDate);

//        Calendar cc = Calendar.getInstance();
//        int year=cc.get(Calendar.YEAR);
//        int month=cc.get(Calendar.MONTH);
//        int mDay = cc.get(Calendar.DAY_OF_MONTH);
//        System.out.println("Date", year+":"+month+":"+mDay);

        txtStatus = (PersianTextViewNormal) view.findViewById(R.id.txtStatus);
        txtStatus.setText(G.currentUserInfo != null ? G.currentUserInfo.status : "");

        txtName = (PersianTextViewNormal) view.findViewById(R.id.txtName);
        txtName.setText(G.currentUserInfo != null ? G.currentUserInfo.fullName : "");

        if (G.currentAccount.rHour == -11111) {
            if (G.currentAccount.rDay == -11111) {
                txtRemainDay.setText("نامحدود");
//                txtRemainDay.setVisibility(View.GONE);
            } else {
                txtRemainDay.setText("شما تا اتمام سرویس اینترنتی خود " + G.currentAccount.rDay + " " + " روز " + "فرصت دارید ");
            }
        } else {
            if (G.currentAccount.rHour == -11111) {
                txtRemainDay.setText("نامحدود");
//                txtRemainDay.setVisibility(View.GONE);
            } else {
                txtRemainDay.setText("شما تا اتمام سرویس اینترنتی خود " + G.currentAccount.rHour + " " + " ساعت " + " فرصت دارید ");
            }
        }
//        if (G.currentAccount.rTraffic == -11111) {
//            txtRemainDay.setText("نامحدود");
////            lblRemainingDays.setVisibility(View.GONE);
//        } else {
//            txtRemainDay.setText(G.currentAccount.rTraffic + " " + "مگابایت" + " ");
//        }
    }

    public void onEventMainThread(EventOnGetClubScoreResponse event) {
        Logger.d("FragmentDrawer : EventOnGetClubScoreResponse is raised.");
        boolean result = event.isResult();
        if (result) {
            prgLoadingScore.setVisibility(View.GONE);
            txtScore.setVisibility(View.VISIBLE);
//            layTotalClubScore.setVisibility(View.VISIBLE);
            int score = event.getScore();
            if (score == 0)
                txtScore.setText("مجموع امتیازات : " + "0");
            else if (score > 0)
                txtScore.setText("مجموع امتیازات : " + score + "+");
            else
                txtScore.setText("مجموع امتیازات : " + score);
        } else {
//            layTotalClubScore.setVisibility(View.GONE);
        }
    }


    public void onEventMainThread(EventOnGetErrorGetUserAccountInfo event) {
        Logger.d("ActivityMain : EventOnGetErrorGetUserAccountInfo is raised");
        G.currentAccount = new Select().from(Account.class).where("userId = ? ", G.currentUser.userId).executeSingle();
        G.currentLicense = new Select().from(License.class).where("userId = ? ", G.currentUser.userId).executeSingle();
        initializeUserAccountView();
    }

    public void onEventMainThread(EventOnGetUserAccountInfoResponse event) {
        Logger.d("ActivityMain : EventOnGetUserAccountInfoResponse is raised");
        initializeUserAccountView();
        /** send this request to get point for current user if set
         * from managment.*/
    }
}
