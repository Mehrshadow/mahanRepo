package ir.aspacrm.my.app.mahan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.adapter.AdapterSpinnerVahed;
import ir.aspacrm.my.app.mahan.classes.DialogClass;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.classes.U;
import ir.aspacrm.my.app.mahan.classes.WebService;
import ir.aspacrm.my.app.mahan.events.EventOnGetAddTicketResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorAddTicket;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorGetUnits;
import ir.aspacrm.my.app.mahan.events.EventOnGetUnitResponse;
import ir.aspacrm.my.app.mahan.events.EventOnNoAccessServerResponse;
import ir.aspacrm.my.app.mahan.events.EventOnSendTicketRequest;
import ir.aspacrm.my.app.mahan.model.Unit;

public class ActivitySendTiket extends AppCompatActivity {
    @Bind(R.id.spOlaviat)
    Spinner spOlaviat;
    @Bind(R.id.spVahed) Spinner spVahed;
    @Bind(R.id.layBtnSendTicket)
    CardView layBtnSendTicket;
    @Bind(R.id.edtTicketSubject)
    EditText edtTicketSubject;
    @Bind(R.id.edtTicketDescription) EditText edtTicketDescription;
    @Bind(R.id.txtValue)
    TextView txtBtnSendTicket;
    @Bind(R.id.txtShowErrorMessage) TextView txtShowErrorMessage;

    ArrayAdapter<String> adapterOlaviat;
    AdapterSpinnerVahed adapterSpinnerVahed;
    List<Unit> units = new ArrayList<>();
    boolean loadUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tiket);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        G.context = this;
        G.currentActivity = this;
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

    }

    private void initView() {

        txtBtnSendTicket.setText("ارسال");
        adapterOlaviat = new ArrayAdapter<> (G.context,
                R.layout.s_item_white,
                R.id.txtName,
                getResources().getStringArray(R.array.sp_olaviat_items));
        adapterOlaviat.setDropDownViewResource(R.layout.s_item_black);
        spOlaviat.setAdapter(adapterOlaviat);
        adapterSpinnerVahed = new AdapterSpinnerVahed(units);
        spVahed.setAdapter(adapterSpinnerVahed);
        /** get vahed item from webService*/
        WebService.sendGetUnitsRequest();

        layBtnSendTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String subject = edtTicketSubject.getText().toString().trim();
                if (subject.length() == 0) {
                    txtShowErrorMessage.setText("لطفا عنوان تیکت را وارد کنید.");
                    return;
                }
                String description = edtTicketDescription.getText().toString().trim();
                if (description.length() == 0) {
                    txtShowErrorMessage.setText("لطفا متن تیکت خود را وارد کنید.");
                    return;
                }
                WebService.sendAddTicketRequest(subject, ((adapterSpinnerVahed.getItem(spVahed.getSelectedItemPosition()))).code, spOlaviat.getSelectedItemPosition() + 1, description);
                EventBus.getDefault().post(new EventOnSendTicketRequest());
                DialogClass dialogClass = new DialogClass();



            }

        });
    }

    public void onEventMainThread(EventOnGetUnitResponse event){
        Logger.d("FragmentSendTicket : EventOnGetUnitResponse is raised");
//        units = event.getUnits();
//        adapterSpinnerVahed.updateList(units);
        getUnitsFromDB();
    }
    public void onEventMainThread(EventOnGetErrorGetUnits event){
        Logger.d("FragmentSendTicket : EventOnGetErrorGetUnits is raised.");
        getUnitsFromDB();
    }
    public void onEventMainThread(EventOnNoAccessServerResponse event){
        Logger.d("FragmentSendTicket : EventOnNoAccessServerResponse is raised.");
        getUnitsFromDB();
    }
    public void onEventMainThread(EventOnGetAddTicketResponse event){
        Logger.d("FragmentSendTicket : EventOnGetAddTicketResponse is raised.");
        if(event.getStatus()){
            edtTicketDescription.setText("");
            edtTicketSubject.setText("");
            txtShowErrorMessage.setText("");

//            Animation clickAnimation = AnimationUtils.loadAnimation(G.context, R.anim.anim_click);
//            clickAnimation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//
//                }
//                @Override
//                public void onAnimationEnd(Animation animation) {

                            DialogClass dialogExit = new DialogClass();
                            dialogExit.showSendTicket();

//                    }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//           G.context.startAnimation(clickAnimation);
        }else{
            txtShowErrorMessage.setText("عملیات ارسال تیکت با خطا مواجه شده است دوباره تلاش کنید.");
        }
    }
    public void onEventMainThread(EventOnGetErrorAddTicket event){
        Logger.d("FragmentSendTicket : EventOnGetErrorAddTicket is raised.");
        txtShowErrorMessage.setText("عملیات ارسال تیکت با خطا مواجه شده است دوباره تلاش کنید.");
    }
    private void getUnitsFromDB() {
        units = new Select().from(Unit.class).where("userId = ?", G.currentUser.userId).execute();
        if( units.size() > 0 )
            loadUnit = true;
        adapterSpinnerVahed.updateList(units);
    }
}
