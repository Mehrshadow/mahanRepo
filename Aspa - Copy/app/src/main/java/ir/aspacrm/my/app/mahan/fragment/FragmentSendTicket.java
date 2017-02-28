package ir.aspacrm.my.app.mahan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.activeandroid.query.Select;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.adapter.AdapterSpinnerVahed;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.classes.WebService;
import ir.aspacrm.my.app.mahan.events.*;
import ir.aspacrm.my.app.mahan.model.Unit;

import java.util.ArrayList;
import java.util.List;

public class FragmentSendTicket extends Fragment {


    @Bind(R.id.spOlaviat) Spinner spOlaviat;
    @Bind(R.id.spVahed) Spinner spVahed;
    @Bind(R.id.layBtnSendTicket)  CardView layBtnSendTicket;
    @Bind(R.id.edtTicketSubject) EditText edtTicketSubject;
    @Bind(R.id.edtTicketDescription) EditText edtTicketDescription;
    @Bind(R.id.txtValue) TextView txtBtnSendTicket;
    @Bind(R.id.txtShowErrorMessage) TextView txtShowErrorMessage;

    ArrayAdapter<String> adapterOlaviat;
    AdapterSpinnerVahed adapterSpinnerVahed;
    List<Unit> units = new ArrayList<>();
    boolean loadUnit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_ticket,container,false);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtBtnSendTicket.setText("ارسال");
        adapterOlaviat = new ArrayAdapter<> (getActivity(),
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
            public void onClick(View view) {
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
