package ir.aspacrm.my.app.mahan;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.activeandroid.query.Select;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.adapter.AdapterFactor;
import ir.aspacrm.my.app.mahan.classes.DialogClass;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.classes.U;
import ir.aspacrm.my.app.mahan.classes.WebService;
import ir.aspacrm.my.app.mahan.enums.EnumInternetErrorType;
import ir.aspacrm.my.app.mahan.events.*;
import ir.aspacrm.my.app.mahan.gson.FactorDetailResponse;
import ir.aspacrm.my.app.mahan.model.Factor;
import ir.aspacrm.my.app.mahan.model.FactorDetail;

import java.util.ArrayList;
import java.util.List;

public class ActivityShowFactors extends AppCompatActivity{


    @Bind(R.id.lstFactor) RecyclerView lstFactor;
    @Bind(R.id.layBtnClose) LinearLayout layBtnClose;
    @Bind(R.id.layBtnBack) LinearLayout layBtnBack;
    @Bind(R.id.txtShowMessage) TextView txtShowMessage;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;


    AdapterFactor adapterFactor;
    LinearLayoutManager linearLayoutManager;
    List<Factor> factors = new ArrayList<>();
    private String current = "";
    DialogClass dlgWaiting;

    /** baraye inke bedunim range dokme bayad chejuri bashad **/
    boolean isCloseButtonShow = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_factors);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);


        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context,R.color.dark_dark_grey));

        adapterFactor = new AdapterFactor(factors);
        linearLayoutManager = new LinearLayoutManager(this);
        lstFactor.setLayoutManager(linearLayoutManager);
        lstFactor.setAdapter(adapterFactor);

        /** be dalil inke dar fragment
         *  swipeRefreshLayout.setRefreshing(true);
         *  dar ebteda kar nemikonad ke listener
         *  swipeRefreshLayout.setOnRefreshListener ra seda bezanad be surate dasti aan ra seda mizanim.*/
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        WebService.sendGetFactorRequest();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowFactors : swipeRefreshLayout onRefresh()");
                WebService.sendGetFactorRequest();
            }
        });

        layBtnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layBtnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }
    /** darkhaste inke mikhahim yek factor ra start konim az tarafe adapterFactor*/
    public void onEventMainThread(EventOnClickedStartFactor event){
        Logger.d("ActivityShowFactors : EventOnClickedStartFactor is raised");
        WebService.sendStartFactorRequest(event.getFactorId());
        dlgWaiting = new DialogClass();
        dlgWaiting.DialogWaiting();
    }
    /** darkhaste start factor*/
    public void onEventMainThread(EventOnGetStartFactorResponse event){
        Logger.d("ActivityShowFactors : EventOnGetStartFactorResponse is raised");
        if(dlgWaiting != null){
            dlgWaiting.DialogWaitingClose();
        }
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        WebService.sendGetFactorRequest();
    }
    public void onEventMainThread(EventOnGetErrorStartFactor event){
        Logger.d("ActivityShowFactors : EventOnGetErrorStartFactor is raised");
        if(dlgWaiting != null){
            dlgWaiting.DialogWaitingClose();
        }
        if(event.getErrorType() == EnumInternetErrorType.NO_INTERNET_ACCESS){
            U.toast("ارتباط اینترنتی خود را چک کنید.");
        }else{
            U.toast("خطا در دریافت اطلاعات از سرور");
        }
    }
    /** daryaft list factor'ha*/
    public void onEventMainThread(EventOnGetFactorResponse event){
        Logger.d("ActivityShowFactors : EventOnGetPaymentResponse is raised");
        swipeRefreshLayout.setRefreshing(false);
        factors = event.getFactorResponses();
        adapterFactor.updateList(factors);
        if ( factors.size() == 0 ){
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("موردی یافت نشد.");
        }else {
            txtShowMessage.setVisibility(View.GONE);
        }
    }
    public void onEventMainThread(EventOnGetErrorGetFactor event){
        Logger.d("ActivityShowFactors : EventOnGetErrorGetPayment is raised");
        getFactorFromDB();
        swipeRefreshLayout.setRefreshing(false);
        txtShowMessage.setVisibility(View.VISIBLE);
        txtShowMessage.setText("خطا در دریافت اطلاعات از سرور لطفا دوباره تلاش کنید.");
    }
    public void onEventMainThread(EventOnClickedFactorMoreDetail event){
        Logger.d("ActivityShowFactors : EventOnClickedFactorMoreDetail is raised");
        WebService.sendGetFactorDetailRequest(event.getFactorCode());
        dlgWaiting = new DialogClass();
        dlgWaiting.DialogWaiting();
    }
    public void onEventMainThread(EventOnGetFactorDetailsResponse event){
        Logger.d("ActivityShowFactors : EventOnGetFactorDetailsResponse is raised");
        ArrayList<FactorDetailResponse> response = event.getFactorDetailResponse();
        if( response.size() == 0){
            U.toast("موردی ثبت نشده است.");
        }else if( response.size() == 1 ) {
            DialogClass dlgFactorDetail = new DialogClass();
            dlgFactorDetail.showFactorDetail(response.get(0));
        }else {
            Intent intent = new Intent(G.context,ActivityShowFactorDetail.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("FACTOR_DETAIL", response);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        if(dlgWaiting != null){
            dlgWaiting.DialogWaitingClose();
            dlgWaiting = null;
        }
    }
    public void onEventMainThread(EventOnGetErrorGetFactorDetail event){
        Logger.d("ActivityShowFactors : EventOnGetErrorGetFactorDetail is raised");
        if(dlgWaiting != null){
            dlgWaiting.DialogWaitingClose();
            dlgWaiting = null;
        }
        if(event.getErrorType() == EnumInternetErrorType.NO_INTERNET_ACCESS){
            List<FactorDetail> factorDetails = new Select()
                    .from(FactorDetail.class)
                    .where("UserId = ? AND parentId = ?",G.currentUser.userId, event.getFactorCode())
                    .execute();

            ArrayList<FactorDetailResponse> response = new ArrayList<>();
            for (FactorDetail detail : factorDetails){
                FactorDetailResponse detailResponse = new FactorDetailResponse(detail.name,detail.des,detail.price);
                response.add(detailResponse);
            }

            if( response.size() == 1 ) {
                DialogClass dlgFactorDetail = new DialogClass();
                dlgFactorDetail.showFactorDetail(response.get(0));

            }else {
                Intent intent = new Intent(G.context,ActivityShowFactorDetail.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("FACTOR_DETAIL", response);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        }else {
            U.toast("خطا در دریافت اطلاعات از سمت سرور لطفا مجددا تلاش کنید.");
        }
    }
    public void onEventMainThread(EventOnNoAccessServerResponse event){
        Logger.d("ActivityShowFactors : EventOnNoAccessServerResponse is raised");
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getFactorFromDB();
        if(dlgWaiting != null){
            dlgWaiting.DialogWaitingClose();
            dlgWaiting = null;
        }

    }
    private void getFactorFromDB() {
        factors = new Select().from(Factor.class).where("UserId = ?", G.currentUser.userId).execute();
        adapterFactor.updateList(factors);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    /*-------------------------------------------------------------------------------------------------------------*/

}
