package ir.aspacrm.my.app.mahan;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.adapter.AdapterSpinnerCity;
import ir.aspacrm.my.app.mahan.adapter.AdapterSpinnerCityGroups;
import ir.aspacrm.my.app.mahan.classes.DialogClass;
import ir.aspacrm.my.app.mahan.classes.Logger;
import ir.aspacrm.my.app.mahan.classes.PersianDate;
import ir.aspacrm.my.app.mahan.classes.WebService;
import ir.aspacrm.my.app.mahan.events.EventOnAddScoreResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetCityGroupsResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetCityResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetRegisterCustomerResponse;
import ir.aspacrm.my.app.mahan.gson.CityGroupResponse;
import ir.aspacrm.my.app.mahan.gson.CityResponse;

import java.util.ArrayList;

public class ActivityRegister extends AppCompatActivity {
    @Bind(R.id.edtName) EditText edtName;
    @Bind(R.id.edtFamily) EditText edtFamily;
    @Bind(R.id.edtTarikhTavalod) EditText edtTarikhTavalod;
    @Bind(R.id.edtCodeMelli) EditText edtCodeMelli;
    @Bind(R.id.edtMobile) EditText edtMobile;
    @Bind(R.id.edtPhone) EditText edtPhone;
    @Bind(R.id.spCity) AppCompatSpinner spCity;
    @Bind(R.id.layCitySpinner) LinearLayout layCitySpinner;
    @Bind(R.id.layLoading) LinearLayout layLoading;
    @Bind(R.id.spCityGroup) AppCompatSpinner spCityGroup;
    @Bind(R.id.layCityGroupSpinner) LinearLayout layCityGroupSpinner;
    @Bind(R.id.edtAddress) EditText edtAddress;
    @Bind(R.id.edtUsername) EditText edtUsername;
    @Bind(R.id.edtPassword) EditText edtPassword;
    @Bind(R.id.txtShowErrorMessage) TextView txtShowErrorMessage;
    @Bind(R.id.layBtnLogin) CardView layBtnLogin;
    @Bind(R.id.txtValue) TextView txtBtnRegister;
    @Bind(R.id.txtBack) TextView txtBack;


    AdapterSpinnerCity adapterSpinnerCity;
    AdapterSpinnerCityGroups adapterSpinnerCityGroups;
    ArrayList<CityResponse> cities = new ArrayList<>();
    ArrayList<CityGroupResponse> cityGroups = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        txtBack.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        txtBtnRegister.setText("ثبت اشتراک جدید");


        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        layBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    txtShowErrorMessage.setText("");
                    String name = edtName.getText().toString().trim();
                    if(name.length() == 0){
                        txtShowErrorMessage.setText("نام خود را وارد کنید.");
                        return;
                    }
                    String family = edtFamily.getText().toString().trim();
                    if (family.length() == 0){
                        txtShowErrorMessage.setText("نام خانوادگی خود را وارد کنید.");
                        return;
                    }


                    /** edit text birthday date**/
                    String d = edtTarikhTavalod.getText().toString().trim();
                    d = d.replace("_", "");
                    Logger.d("ActivityRegister : date remove _ from it is " + d);
                    String[] dateSplit = d.split("/");

                    Logger.d("ActivityRegister : dateSplit size is " + dateSplit.length);
                    if (dateSplit.length < 3) {
                        txtShowErrorMessage.setText("تاریخ تولد خود را به صورت کامل وارد کنید.");
                        return;
                    }

                    String year = dateSplit[0].replace("_", "");
                    String month = dateSplit[1].replace("_", "");
                    String day = dateSplit[2].replace("_", "");


                    int valueYear = Integer.valueOf(year);
                    int valueMonth = Integer.valueOf(month);
                    int valueDay = Integer.valueOf(day);

                    if (year.length() < 4 || valueYear < 1300 || valueYear > 1500) {
                        txtShowErrorMessage.setText("سال تولد خود را صحیح وارد کنید.");
                        return;
                    }
                    else if (month.length() < 2 || valueMonth < 1 || valueMonth > 12){
                        txtShowErrorMessage.setText("ماه تولد خود را وارد صحیح وارد کنید.");
                        return;
                    }
                    else if (day.length() < 2 || valueDay < 1 || valueDay > 31){
                        txtShowErrorMessage.setText("روز تولد خود را صحیح وارد کنید.");
                        return;
                    }

                    PersianDate persianDate = new PersianDate();
                    persianDate.PersianToGregorian(valueYear,valueMonth,valueDay);
                    String tarikhTavalod =
                            String.format("%04d", persianDate.getYear())
                                    + "-" + String.format("%02d", persianDate.getMonth())
                                    + "-" + String.format("%02d", persianDate.getDay());
                    Logger.d("ActivityRegister : date in miladi format" + tarikhTavalod);

                    /** get code melli*/
                    String codeMelli = edtCodeMelli.getText().toString().trim();
                    if (codeMelli.length() < 10){
                        txtShowErrorMessage.setText("کد ملی خود را به صورت کامل وارد کنید.");
                        return;
                    }
                    String mobile = edtMobile.getText().toString().trim();
                    if (mobile.length() < 10){
                        txtShowErrorMessage.setText("شماره موبایل خود را کامل وارد کنید.");
                        return;
                    }
                    String phone = edtPhone.getText().toString().trim();
                    if (phone.length() < 10){
                        txtShowErrorMessage.setText("شماره تلفن خود را کامل وارد کنید.");
                        return;
                    }

                    if(spCity.getSelectedItemPosition() == 0){
                        txtShowErrorMessage.setText("لطفا یک شهر را انتخاب کنید.");
                        return;
                    }
                    if(spCityGroup.getSelectedItemPosition() == 0){
                        txtShowErrorMessage.setText("لطفا یک گروه را انتخاب کنید.");
                        return;
                    }
                    int cityId = adapterSpinnerCity.getItem(spCity.getSelectedItemPosition()).Id;
                    int groupId = adapterSpinnerCityGroups.getItem(spCityGroup.getSelectedItemPosition()).Id;
                    String address = edtAddress.getText().toString().trim();
                    if (address.length() < 0){
                        txtShowErrorMessage.setText("آدرس خود را وارد کنید.");
                        return;
                    }
                    String username = edtUsername.getText().toString().trim();
                    if (username.length() < 0){
                        txtShowErrorMessage.setText("نام کاربری پیشنهادی خود را وارد کنید.");
                        return;
                    }
                    String password = edtPassword.getText().toString().trim();
                    if (password.length() < 0){
                        txtShowErrorMessage.setText("رمز عبور را وارد کنید.");
                        return;
                    }
                    WebService.sendRegisterCustomerRequest(cityId,groupId,name,family,tarikhTavalod,codeMelli,mobile,phone,address,username,password);
                    layLoading.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    Logger.e("PAYAM :" + e.getMessage());
                }
            }
        });

        CityResponse unitlCityNotGet = new CityResponse();
        unitlCityNotGet.Name = "لطفا تا دریافت لیست شهرها صبر کنید.";
        cities.add(0,unitlCityNotGet);
        adapterSpinnerCity = new AdapterSpinnerCity(cities);
        spCity.setAdapter(adapterSpinnerCity);
        WebService.sendGetCityRequest();

        CityGroupResponse unitlCityGroupNotGet = new CityGroupResponse();
        unitlCityGroupNotGet.Name = "لطفا تا دریافت لیست گروه ها صبر کنید.";
        cityGroups.add(0,unitlCityGroupNotGet);
        adapterSpinnerCityGroups = new AdapterSpinnerCityGroups(cityGroups);
        spCityGroup.setAdapter(adapterSpinnerCityGroups);

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                cityGroups.clear();
                CityGroupResponse unitlCityGroupNotGet = new CityGroupResponse();
                unitlCityGroupNotGet.Name = "-";
                cityGroups.add(0,unitlCityGroupNotGet);
                adapterSpinnerCityGroups.updateList(cityGroups);
                if( position != 0 ){
                    WebService.sendGetCityGroupsRequest(adapterSpinnerCity.getItem(spCity.getSelectedItemPosition()).Id);
                    layLoading.setVisibility(View.VISIBLE);
                    Logger.d("ActivityRegister : city id is " + (adapterSpinnerCity.getItem(spCity.getSelectedItemPosition())).Id);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void onEventMainThread(EventOnGetCityResponse event) {
        Logger.d("ActivityRegister : EventOnGetCityResponse is raised.");
        CityResponse[] responses = event.getCityResponses();
        cities.clear();
        for (int i = 0; i < responses.length + 1; i++) {
            if (i == 0) {
                CityResponse firstSpinnerItem = new CityResponse();
                firstSpinnerItem.Name = "شهر مورد نظر خود را انتخاب کنید.";
                cities.add(firstSpinnerItem);
            }else {
                cities.add(responses[i-1]);
            }
        }
        Logger.d("ActivityRegister : cities size is " + cities.size());
        adapterSpinnerCity.updateList(cities);
        layLoading.setVisibility(View.GONE);
    }
    public void onEventMainThread(EventOnGetCityGroupsResponse event){
        Logger.d("ActivityRegister : EventOnGetCityGroupsResponse is raised.");
        CityGroupResponse[] responses = event.getCityGroupResponses();
        cityGroups.clear();
        for (int i = 0; i < responses.length + 1; i++) {
            if (i == 0) {
                CityGroupResponse firstSpinnerItem = new CityGroupResponse();
                firstSpinnerItem.Name = "گروه مورد نظر خود را انتخاب کنید.";
                cityGroups.add(firstSpinnerItem);
            }else {
                cityGroups.add(responses[i-1]);
            }
        }
        Logger.d("ActivityRegister : cityGroup size is " + cityGroups.size());
        adapterSpinnerCityGroups.updateList(cityGroups);
        layLoading.setVisibility(View.GONE);
    }
    public void onEventMainThread(EventOnGetRegisterCustomerResponse event){
        Logger.d("ActivityRegister : EventOnGetRegisterCustomerResponse is raised.");
        boolean result = event.isResult();
        String message = event.getMessage();
        String title = "";
        if(!result){
            title = "خطا";
        }else{
            edtPhone.setText("");
            edtMobile.setText("");
            edtTarikhTavalod.setText("");
            edtAddress.setText("");
            edtCodeMelli.setText("");
            edtFamily.setText("");
            edtName.setText("");
            edtPassword.setText("");
            edtUsername.setText("");
            spCity.setSelection(0);
            spCityGroup.setSelection(0);
        }
        DialogClass dlgMessage = new DialogClass();
        dlgMessage.showMessageDialog(title,message);
        layLoading.setVisibility(View.GONE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
