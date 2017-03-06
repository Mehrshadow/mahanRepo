package ir.aspacrm.my.app.mahan;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import ir.aspacrm.my.app.mahan.classes.DialogClass;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.events.EventOnAddScoreResponse;
import ir.aspacrm.my.app.mahan.fragment.FragmentShowPaymentList;

public class ActivityPayments extends AppCompatActivity{


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

        supportFragmentManager = super.getSupportFragmentManager();
        supportFragmentManager
                .beginTransaction()
                .add(R.id.layFragment,new FragmentShowPaymentList())
                .addToBackStack("FragmentShowPaymentList")
                .commit();
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
        if(supportFragmentManager.getBackStackEntryCount() == 1)
            finish();
        else
            super.onBackPressed();
    }
    /*-------------------------------------------------------------------------------------------------------------*/

}
