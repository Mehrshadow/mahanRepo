package ir.aspacrm.my.app.mahan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.activeandroid.query.Select;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.classes.WebService;
import ir.aspacrm.my.app.mahan.component.PersianTextViewNormal;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorGetUserInfo;
import ir.aspacrm.my.app.mahan.events.EventOnGetUserInfoResponse;
import ir.aspacrm.my.app.mahan.events.EventOnNoAccessServerResponse;
import ir.aspacrm.my.app.mahan.gson.UserInfoResponse;
import ir.aspacrm.my.app.mahan.model.Account;
import ir.aspacrm.my.app.mahan.model.Info;

public class ActivityShowUserInfo extends AppCompatActivity {
    @Bind(R.id.layBtnClose) LinearLayout layBtnClose;
    @Bind(R.id.txtShowErrorMessage) TextView txtShowErrorMessage;
    @Bind(R.id.txtUserFullName) PersianTextViewNormal txtUserFullName;
    @Bind(R.id.txtUsername) PersianTextViewNormal txtUsername;
    @Bind(R.id.txtMobile) PersianTextViewNormal txtMobile;
    @Bind(R.id.txtTarazMalli) net.kianoni.fontloader.TextView txtTarazMalli;
    @Bind(R.id.txtFirstConnection) net.kianoni.fontloader.TextView txtFirstConnection;
    @Bind(R.id.txtLastConnection) net.kianoni.fontloader.TextView txtLastConnection;
    @Bind(R.id.txtExpireDate) net.kianoni.fontloader.TextView txtExpireDate;
    @Bind(R.id.txtNoeService) net.kianoni.fontloader.TextView txtNoeService;
    @Bind(R.id.txtStatus) net.kianoni.fontloader.TextView txtStatus;
    @Bind(R.id.txtNamayandeForush) net.kianoni.fontloader.TextView txtNamayandeForush;
    @Bind(R.id.layLoading) LinearLayout layLoading;
    @Bind(R.id.card_view) CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_info);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

//
//        if (Build.VERSION.SDK_INT >= 21)
//            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        cardView.setVisibility(View.GONE);
        WebService.sendGetUserInfoRequest();

        layBtnClose
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
    }
    public void onEventMainThread(EventOnGetUserInfoResponse event){
        Logger.d("ActivityShowUserInfo : EventOnGetUserInfoResponse is raised.");
        layLoading.setVisibility(View.GONE);
        UserInfoResponse response = event.getUserInfo();
        if(response.result) {
            txtShowErrorMessage.setVisibility(View.GONE);
            setTextViewValue(response);
        }else{
            /** can show error message */
            cardView.setVisibility(View.GONE);
        }
    }
    public void onEventMainThread(EventOnGetErrorGetUserInfo event){
        Logger.d("ActivityShowUserInfo : EventOnGetErrorGetUserInfo is raised.");
        layLoading.setVisibility(View.GONE);
        getUserInfoFromDB();
    }
    public void onEventMainThread(EventOnNoAccessServerResponse event){
        Logger.d("ActivityShowUserInfo : EventOnNoAccessServerResponse is raised.");
        layLoading.setVisibility(View.GONE);
        getUserInfoFromDB();
    }
    private void getUserInfoFromDB() {
        Info info = new Select().from(Info.class).where("userId = ? " , G.currentUser.userId).executeSingle();
        if(info != null) {
            txtShowErrorMessage.setVisibility(View.VISIBLE);
            UserInfoResponse response = new UserInfoResponse();
            response.fullName = info.fullName;
            response.username = info.username;
            response.firstCon = info.firstCon;
            response.lastCon = info.lastCon;
            response.mobileNo = info.mobileNo;
            response.resellerName = info.resellerName;
            response.serviceType = info.serviceType;
            response.status = "نامشخص";
            /** set TextView Value from Response */
            setTextViewValue(response);
        }else{
            cardView.setVisibility(View.GONE);
        }
    }
    private void setTextViewValue(UserInfoResponse response) {
        txtUserFullName.setText(response.fullName);
        txtUsername.setText(response.username);
        txtMobile.setText(response.mobileNo);
        txtFirstConnection.setText(response.firstCon);
        txtLastConnection.setText(response.lastCon);
        txtNoeService.setText(response.serviceType);
        txtStatus.setText(response.status);
        txtNamayandeForush.setText(response.resellerName);
        txtTarazMalli.setText("نامشخص");
        txtExpireDate.setText("نامشخص");
        Account account = new Select().from(Account.class).where("userId = ? " , G.currentUser.userId).executeSingle();
        if (account != null){
            txtTarazMalli.setText("" + account.balance);
            txtExpireDate.setText(account.farsiExpDate);
        }
        cardView.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
