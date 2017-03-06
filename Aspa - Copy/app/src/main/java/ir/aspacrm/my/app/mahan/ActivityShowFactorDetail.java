package ir.aspacrm.my.app.mahan;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.aspacrm.my.app.mahan.adapter.AdapterFactorDetail;
import ir.aspacrm.my.app.mahan.classes.DialogClass;
import ir.aspacrm.my.app.mahan.events.EventOnAddScoreResponse;
import ir.aspacrm.my.app.mahan.gson.FactorDetailResponse;

import java.util.ArrayList;

public class ActivityShowFactorDetail extends AppCompatActivity {


    @Bind(R.id.layBtnClose) LinearLayout layBtnClose;
    @Bind(R.id.lstFactorDetail) RecyclerView lstFactorDetail;
    @Bind(R.id.layBtnBack) LinearLayout layBtnBack;



    LinearLayoutManager linearLayoutManager;
    AdapterFactorDetail adapterFactorDetail;
    ArrayList<FactorDetailResponse> factorDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_factor_detail);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        factorDetails = getIntent().getParcelableArrayListExtra("FACTOR_DETAIL");

        linearLayoutManager = new LinearLayoutManager(this);
        adapterFactorDetail = new AdapterFactorDetail(factorDetails);
        lstFactorDetail.setLayoutManager(linearLayoutManager);
        lstFactorDetail.setAdapter(adapterFactorDetail);
        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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


}
