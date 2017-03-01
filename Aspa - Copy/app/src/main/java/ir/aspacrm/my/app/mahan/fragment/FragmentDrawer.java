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

import java.util.ArrayList;
import java.util.List;

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
    TextView txtStatus, txtScore, txtName;
    TextView txtRemainDay;
    LinearLayout layTotalClubScore;
    ProgressBar prgLoadingScore;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drawer, container, false);
        initView();
        EventBus.getDefault().register(this);
        WebService.sendGetClubScoreRequest();
        return view;
    }

    private void initView() {
        txtRemainDay = (PersianTextViewBold) view.findViewById(R.id.txtRemainDay);

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
        title.add(getString(R.string.spesioansuggestion));
        title.add(getString(R.string.news));
        title.add(getString(R.string.exit));

        RecyDrawer = (RecyclerView) view.findViewById(R.id.RecyDrawer);
        RecyDrawer.setLayoutManager(new LinearLayoutManager(G.context, LinearLayoutManager.VERTICAL, false));
        RecyDrawer.setHasFixedSize(true);

        AdapterDrawerRecycler adapterDrawerRecycler = new AdapterDrawerRecycler(title, G.context);
        RecyDrawer.setAdapter(adapterDrawerRecycler);


    }

    private void initializeUserAccountView() {

        txtStatus = (TextView) view.findViewById(R.id.txtStatus);
        txtStatus.setText(G.currentUserInfo != null ? G.currentUserInfo.status : "");

        txtName = (PersianTextViewNormal) view.findViewById(R.id.txtName);
        txtName.setText(G.currentUserInfo != null ? G.currentUserInfo.fullName : "");

        if (G.currentAccount.rHour == -11111) {
            if (G.currentAccount.rDay == -11111) {
                txtRemainDay.setText("نامحدود");
//                txtRemainDay.setVisibility(View.GONE);
            } else {
                txtRemainDay.setText("" + G.currentAccount.rDay + " " + "روز" + " ");
            }
        } else {
            if (G.currentAccount.rHour == -11111) {
                txtRemainDay.setText("نامحدود");
//                txtRemainDay.setVisibility(View.GONE);
            } else {
                txtRemainDay.setText("" + G.currentAccount.rHour + " " + "ساعت" + " ");
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
