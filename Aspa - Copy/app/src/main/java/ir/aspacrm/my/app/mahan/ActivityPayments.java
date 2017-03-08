package ir.aspacrm.my.app.mahan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.aspacrm.my.app.mahan.classes.DialogClass;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.events.EventOnAddScoreResponse;
import ir.aspacrm.my.app.mahan.fragment.FragmentShowPaymentList;

public class ActivityPayments extends AppCompatActivity {

    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;

    public static boolean isReturnedFromBrowser = false;
    public static boolean isParent = false;

    private FragmentManager supportFragmentManager;

    @Override
    public FragmentManager getSupportFragmentManager() {
        return supportFragmentManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_payments);
        ButterKnife.bind(this);
//        EventBus.getDefault().register(this);

        isParent = true;

        supportFragmentManager = super.getSupportFragmentManager();
        supportFragmentManager
                .beginTransaction()
                .add(R.id.layFragment, new FragmentShowPaymentList())
                .addToBackStack("FragmentShowPaymentList")
                .commit();

        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReturnedFromBrowser) {
                    isReturnedFromBrowser = false;
                    startActivity(new Intent(G.currentActivity, ActivityMain0.class));
                    finish();
                } else
                    onBackPressed();
            }
        });
    }



//    public void onEventMainThread(EventOnGetBankPageResponse event){
//        Logger.d("ActivityChargeOnline : EventOnGetBankPageResponse is raised");
//        supportFragmentManager.beginTransaction()
//                .add(R.id.layFragment, FragmentBrowser.newInstance(event.getBankUrl()))
//                .addToBackStack("FragmentBrowser")
//                .commit();
//
//    }
//    public void onEventMainThread(EventOnGetErrorCallBankPage event){
//        Logger.d("ActivityChargeOnline : EventOnGetErrorCallBankPage is raised");
//        if(event.getErrorType() == EnumInternetErrorType.NO_INTERNET_ACCESS){
//            DialogClass dlg = new DialogClass();
//            dlg.showMessageDialog("خطا","عدم دسترسی به اینترنت، لطفا ارتباط اینترنتی خود را چک کنید.");
//        }else{
//            DialogClass dlg = new DialogClass();
//            dlg.showMessageDialog("خطا","خطا در دریافت اطلاعات از سرور، لطفا مجددا تلاش کنید.");
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("ActivityShowPayments : onResume()");
        G.currentActivity = this;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        Logger.d("ActivityChargeOnline : onBackPressed()");
        if (supportFragmentManager.getBackStackEntryCount() == 1)
            finish();
        else
            super.onBackPressed();
    }
    /*-------------------------------------------------------------------------------------------------------------*/

    @Override
    protected void onStop() {
        super.onStop();

        isParent = false;
    }
}
