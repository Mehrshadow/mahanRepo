package ir.aspacrm.my.app.mahan.classes;

import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.enums.EnumInternetErrorType;
import ir.aspacrm.my.app.mahan.events.*;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WebService {

    //جستجوی شرکت ها  http://mng.aspacrm.ir/service.aspx
    public static void sendGetIspUrlRequest(long customerId) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetISPUrl")
                .add("Id", "" + customerId)
                .build();
        Request request = new Request.Builder().url(G.JMWS).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorIspUrl(EnumInternetErrorType.NO_INTERNET_ACCESS));
                //U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorIspUrl(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getIspUrlResponse(response.body().string());
            }
        });
    }

    //جستجوی شرکت ها  http://mng.aspacrm.ir/service.aspx
    public static void sendGetIspListRequest(String des) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "SearchISP")
                .add("des", des)
                .build();
        Request request = new Request.Builder().url(G.JMWS).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorIspList());
                    return;
                }
                JsonParser.getIspListResponse(response.body().string());
            }
        });
    }

    //گرفتن اطلاعات شرکت برای نمایش دادن در صفحه لاگین
    public static void sendGetIspInfoLoginRequest(String ispURL) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetIspInfoLogin")
                .add("MyLink", ispURL)
                .build();
        Request request = new Request.Builder().url(ispURL + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorIspInfo());
                    return;
                }
                JsonParser.getIspInfoResponse(response.body().string());
            }
        });
    }

    //لاکین کردن
    public static void sendLoginRequest(final String ispURL, final long ispId, String username, String password) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "Authenticate")
                .add("deviceName", "" + U.getDeviceName())
                .add("DeviceModel", "" + U.getDeviceModel())
                .add("OsVer", "" + U.getOSVersion())
                .add("ResW", "" + U.getDeviceWidth())
                .add("ResH", "" + U.getDeviceHeight())
                .add("Density", "" + U.getDeviceDensity())
                .add("username", "" + U.persianToDecimal(username))
                .add("password", "" + U.persianToDecimal(password))
                .add("imei", "" /*+ U.getIMEI()*/)
                .build();

        Logger.d("Webservice : username is " + U.persianToDecimal(username));
        Logger.d("Webservice : password is " + U.persianToDecimal(password));

        Request request = new Request.Builder().url(ispURL + G.WS_PAGE).post(body).build();
//        Logger.d("WebService : rt is " + "Authenticate");
//        Logger.d("WebService : deviceName is " + U.getDeviceName());
//        Logger.d("WebService : DeviceModel is " +  U.getDeviceModel());
//        Logger.d("WebService : OsVer is " + U.getOSVersion());
//        Logger.d("WebService : ResW is " + U.getDeviceWidth());
//        Logger.d("WebService : ResH is " + U.getDeviceHeight());
//        Logger.d("WebService : Density is " + U.getDeviceDensity());
//        Logger.d("WebService : username is " + username);
//        Logger.d("WebService : password is " + password);
//        Logger.d("WebService : imei is " + U.getIMEI());
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorLogin());
                    return;
                }
                JsonParser.getLoginResponse(response.body().string(), ispId, ispURL);
            }
        });
    }

    //گرفتن لایسنس برای مشترکی که لاگین کرده است
    public static void sendGetUserLicenseRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetLicence")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();
        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        Logger.d("WebService : sendGetUserLicenseRequest rt is " + "GetLicence");
        Logger.d("WebService : sendGetUserLicenseRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetUserLicenseRequest ExKey is " + G.currentUser.exKey);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorUserLicense());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorUserLicense());
                    return;
                }
                JsonParser.getUserLicenseResponse(response.body().string());
            }
        });
    }

    //گرفتن اطلاعات اکانت مشترک
    public static void sendGetUserAccountInfoRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetRemain")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();
        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        Logger.d("WebService : sendGetUserAccountInfoRequest url is " + G.currentUser.ispUrl + G.WS_PAGE);
        Logger.d("WebService : sendGetUserAccountInfoRequest rt is " + "GetRemain");
        Logger.d("WebService : sendGetUserAccountInfoRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetUserAccountInfoRequest ExKey is " + G.currentUser.exKey);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetUserAccountInfo(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetUserAccountInfo(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getUserAccountInfoResponse(response.body().string());
            }
        });
    }

    //گرفتن اطلاعات مشترکی که لاگین کرده است
    public static void sendGetUserInfoRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetUserInfo")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();
        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        Logger.d("WebService : sendGetUserInfoRequest rt is " + "GetUserInfo");
        Logger.d("WebService : sendGetUserInfoRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetUserInfoRequest ExKey is " + G.currentUser.exKey);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetUserInfo());
                    return;
                }
                JsonParser.getGetUserInfoResponse(response.body().string());
            }
        });
    }

    //بازیابی رمز عبور توسط نام کاربری و شماره موبایل ذخیره در سیستم
    public static void sendRememberPassRequest(String username, String mobile, String ispUrl) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "RememberPass")
                .add("Username", "" + username)
                .add("Mobile", "" + mobile)
                .build();
        Logger.d("WebService : sendRememberPassRequest rt is " + "RememberPass");
        Logger.d("WebService : sendRememberPassRequest Username is " + username);
        Logger.d("WebService : sendRememberPassRequest Mobile is " + mobile);

        Request request = new Request.Builder().url(ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorSendRememberPassword());
                    return;
                }
                JsonParser.sendRememberPasswordResponse(response.body().string());
            }
        });
    }

    //تغییر رمز مشترک
    public static void sendChangePasswordRequest(String currentPassword, String newPassword) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "ChangePassword")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("LastPass", currentPassword)
                .add("NewPass", newPassword)
                .build();
        Logger.d("WebService : sendChangePasswordRequest rt is " + "ChangePassword");
        Logger.d("WebService : sendChangePasswordRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendChangePasswordRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendChangePasswordRequest LastPass is " + currentPassword);
        Logger.d("WebService : sendChangePasswordRequest NewPass is " + newPassword);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorChangePassword());
                    return;
                }
                JsonParser.getChangePasswordResponse(response.body().string());
            }
        });
    }

    /* WebService for Payment*/
    //گرفتن همه پرداخت های مشترک بر اساس تعدادی که در مدیریت اجازه داده شده است
    public static void sendGetPaymentRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetPayments")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();
        Logger.d("WebService : sendGetPaymentRequest rt is " + "GetPayments");
        Logger.d("WebService : sendGetPaymentRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetPaymentRequest ExKey is " + G.currentUser.exKey);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorChangePassword());
                    return;
                }
                JsonParser.getPaymentResponse(response.body().string());
            }
        });
    }

    /* WebService for Factor*/
    //گرفتن کلیه فاکتورهای مشترک بر اساس تعدادی که در قسمت مدیریت اجازه داده شده است`
    public static void sendGetFactorRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetFactors")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();
        Logger.d("WebService : sendGetFactorRequest rt is " + "GetFactors");
        Logger.d("WebService : sendGetFactorRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetFactorRequest ExKey is " + G.currentUser.exKey);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetFactor());
                    return;
                }
                JsonParser.getFactorResponse(response.body().string());
            }
        });
    }

    //گرفتن جزییات فاکتور
    public static void sendGetFactorDetailRequest(final long factorCode) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetFactorDetails")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("FactorCode", "" + factorCode)
                .build();
        Logger.d("WebService : sendGetFactorDetailRequest rt is " + "GetFactorDetails");
        Logger.d("WebService : sendGetFactorDetailRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetFactorDetailRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendGetFactorDetailRequest FactorCode is " + factorCode);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetFactorDetail(EnumInternetErrorType.NO_INTERNET_ACCESS, factorCode));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetFactorDetail(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED, factorCode));
                    return;
                }
                JsonParser.getFactorDetailResponse(response.body().string(), factorCode);
            }
        });
    }

    //شروع کردن فاکتور
    public static void sendSelectFactorRequest(long factorCode) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "SelectFactor")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("FactorCode", "" + factorCode)
                .build();
        Logger.d("WebService : sendStartFactorRequest rt is " + "SelectFactor");
        Logger.d("WebService : sendStartFactorRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendStartFactorRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendStartFactorRequest FactorCode is " + factorCode);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorSelectFactor());
                    return;
                }
                JsonParser.getSelectFactorResponse(response.body().string());
            }
        });
    }

    //انتخاب کردن فاکتور
    public static void sendStartFactorRequest(long factorCode) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "StartFactor")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("FactorCode", "" + factorCode)
                .build();
        Logger.d("WebService : sendStartFactorRequest rt is " + "GetFactors");
        Logger.d("WebService : sendStartFactorRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendStartFactorRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendStartFactorRequest FactorCode is " + factorCode);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorStartFactor(EnumInternetErrorType.NO_INTERNET_ACCESS));
                //U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorStartFactor(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getStartFactorResponse(response.body().string());
            }
        });
    }

    /* WebService for Ticket*/
    //گرفتن واحد ها
    public static void sendGetUnitsRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetUnits")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();
        Logger.d("WebService : sendGetUnitsRequest rt is " + "GetUnits");
        Logger.d("WebService : sendGetUnitsRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetUnitsRequest ExKey is " + G.currentUser.exKey);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetUnits());
                    return;
                }
                JsonParser.getUnitsResponse(response.body().string());
            }
        });
    }

    //اضافه کردن تیکت
    public static void sendAddTicketRequest(String title, long unitCode, int priority, String des) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "AddTicket")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("Title", "" + title)
                .add("UnitCode", "" + unitCode)
                .add("Priority", "" + priority)
                .add("Des", "" + des)
                .build();
        Logger.d("WebService : sendAddTicketRequest rt is " + "AddTicket");
        Logger.d("WebService : sendAddTicketRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendAddTicketRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendAddTicketRequest Title is " + title);
        Logger.d("WebService : sendAddTicketRequest UnitCode is " + unitCode);
        Logger.d("WebService : sendAddTicketRequest Priority is " + priority);
        Logger.d("WebService : sendAddTicketRequest Des is " + des);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorAddTicket());
                    return;
                }
                JsonParser.getAddTicketResponse(response.body().string());
            }
        });
    }

    //ثبت جزییات تیکت
    public static void sendAddTicketDetailRequest(long ticketCode, long unitCode, String des) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "AddTicketDetail")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("TicketCode", "" + ticketCode)
                .add("UnitCode", "" + unitCode)
                .add("Des", "" + des)
                .build();

        Logger.d("WebService : sendAddTicketDetailRequest rt is " + "AddTicketDetail");
        Logger.d("WebService : sendAddTicketDetailRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendAddTicketDetailRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendAddTicketDetailRequest UnitCode is " + unitCode);
        Logger.d("WebService : sendAddTicketDetailRequest TicketCode is " + ticketCode);
        Logger.d("WebService : sendAddTicketDetailRequest Des is " + des);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorAddTicket());
                    return;
                }
                JsonParser.getAddTicketDetailResponse(response.body().string());
            }
        });
    }

    //گرفتن همه تیکت های مشترک
    public static void sendGetTicketsRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetTickets")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();

        Logger.d("WebService : sendGetTicketsRequest rt is " + "GetTickets");
        Logger.d("WebService : sendGetTicketsRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetTicketsRequest ExKey is " + G.currentUser.exKey);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetTickets(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetTickets(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getTicketsResponse(response.body().string());
            }
        });
    }

    //گرفتن جزییات تیکت
    public static void sendGetTicketDetailsRequest(final long ticketCode) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetTicketDetails")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("TicketCode", "" + ticketCode)
                .build();

        Logger.d("WebService : sendGetTicketDetailsRequest rt is " + "GetTicketDetails");
        Logger.d("WebService : sendGetTicketDetailsRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetTicketDetailsRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendGetTicketDetailsRequest TicketCode is " + ticketCode);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetTicketDetails());
                    return;
                }
                JsonParser.getTicketDetailsResponse(response.body().string(), ticketCode);
            }
        });
    }

    /* WebService for Connect */
    //اجازه وصل موقت دارد یا نه
    public static void sendRegConnectAllowRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "RegConnectAllow")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();

        Logger.d("WebService : sendRegConnectAllowRequest rt is " + "RegConnectAllow");
        Logger.d("WebService : sendRegConnectAllowRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendRegConnectAllowRequest ExKey is " + G.currentUser.exKey);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorRegConnectAllow());
                    return;
                }
                JsonParser.getRegConnectAllowResponse(response.body().string());
            }
        });
    }

    //وصل موقت
    public static void sendRegConnectRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "RegConnect")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();

        Logger.d("WebService : sendRegConnectRequest rt is " + "RegConnect");
        Logger.d("WebService : sendRegConnectRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendRegConnectRequest ExKey is " + G.currentUser.exKey);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorRegConnect(EnumInternetErrorType.NO_INTERNET_ACCESS));
                //U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorRegConnect(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getRegConnectResponse(response.body().string());
            }
        });
    }

    /* WebService for Connections*/
    //گرفتن سوابق اتصال مشترک بر اساس تاریخ
    public static void sendGetConnectionsRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetConnections")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("StartDate", "" + U.getDate(-10))
                .add("EndDate", "" + U.getCurrentDate())
                .build();

        Logger.d("WebService : sendGetConnectionsRequest rt is " + "RegConnect");
        Logger.d("WebService : sendGetConnectionsRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetConnectionsRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendGetConnectionsRequest StartDate is " + U.getDate(-10));
        Logger.d("WebService : sendGetConnectionsRequest EndDate is " + U.getCurrentDate());

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetConnections());
                    return;
                }
                JsonParser.getConnectionsResponse(response.body().string());
            }
        });
    }

    //گرفتن گراف مصرف مشترک بر اساس تاریخ ، بیش از یک ماه اجازه داده نمی شود
    public static void sendGetGraphRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetGraph")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("StartDate", "" + U.getDate(-10))
                .add("EndDate", "" + U.getCurrentDate())
                .build();

        Logger.d("WebService : sendGetGraphRequest rt is " + "GetGraph");
        Logger.d("WebService : sendGetGraphRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetGraphRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendGetGraphRequest StartDate is " + U.getDate(-10));
        Logger.d("WebService : sendGetGraphRequest EndDate is " + U.getCurrentDate());

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetGraph(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetGraph(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getGraphResponse(response.body().string());
            }
        });
    }

    /* WebService for Online*/
    //صدا زدن آدرس برای انتقال به صفحه شارژ آنلاین
    public static void sendChargeOnlineRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "ChargeOnline")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();

        Logger.d("WebService : sendChargeOnlineRequest rt is " + "ChargeOnline");
        Logger.d("WebService : sendChargeOnlineRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendChargeOnlineRequest ExKey is " + G.currentUser.exKey);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorChargeOnline());
                    return;
                }
                JsonParser.getChargeOnlineResponse(response.body().string());
            }
        });
    }

    //صدا زدن آدرس برای انتقال به صفحه پرداخت آنلاین
    public static void sendPayOnlineRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "PayOnline")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();

        Logger.d("WebService : sendPayOnlineRequest rt is " + "PayOnline");
        Logger.d("WebService : sendPayOnlineRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendPayOnlineRequest ExKey is " + G.currentUser.exKey);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorPayOnline());
                    return;
                }
                JsonParser.getPayOnlineResponse(response.body().string());
            }
        });
    }

    //ارسال درخواست گرفتن کد ussd برای شارژ آنلاین
    public static void sendChargeOnlineForPayRequest(long factorCode) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "ChargeOnlineForPay")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("FactorCode", "" + factorCode)
                .build();
        Logger.d("WebService : sendChargeOnlineForPayRequest rt is " + "ChargeOnlineForPay");
        Logger.d("WebService : sendChargeOnlineForPayRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendChargeOnlineForPayRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendChargeOnlineForPayRequest FactorCode is " + factorCode);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorChargeOnlineForPayRequest(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorChargeOnlineForPayRequest(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getChargeOnlineForPayRequest(response.body().string());
            }
        });
    }

    //ارسال درخواست گرفتن کد ussd
    public static void sendPayOnlineForPayRequest(int money) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "PayOnlineForPay")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("Money", "" + money)
                .build();
        Logger.d("WebService : sendPayOnlineForPayRequest rt is " + "PayOnlineForPay");
        Logger.d("WebService : sendPayOnlineForPayRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendPayOnlineForPayRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendPayOnlineForPayRequest Money is " + money);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorPayOnlineForPay(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorPayOnlineForPay(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getPayOnlineForPayRequest(response.body().string());
            }
        });
    }

    public static void sendCheckOrderIdResultRequest(long orderId) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "CheckOrderIdResult")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("OrderId", "" + orderId)
                .build();
        Logger.d("WebService : sendCheckOrderIdResultRequest rt is " + "CheckOrderIdResult");
        Logger.d("WebService : sendCheckOrderIdResultRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendCheckOrderIdResultRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendCheckOrderIdResultRequest OrderId is " + orderId);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorCheckOrderIdResult(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorCheckOrderIdResult(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getCheckOrderIdResultRequest(response.body().string());
            }
        });
    }

    // لود کردن لیست بانک ها
    public static void sendGetBankList() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "LoadBanks")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();
        Logger.d("WebService : sendGetBankList rt is " + "LoadBanks");
        Logger.d("WebService : sendGetBankList ExKey is " + G.currentUser.exKey);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetBankList(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetBankList(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getBankListRequest(response.body().string());
            }
        });
    }

    // چک کردن تراز مالی
    public static void sendCheckTaraz(long factorCode) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "CheckTarazForPaymentThisFactor")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("FactorCode", "" + factorCode)
                .build();
        Logger.d("WebService : sendCheckTaraz url is " + G.currentUser.ispUrl + G.WS_PAGE);
        Logger.d("WebService : sendCheckTaraz rt is " + "CheckTarazForPaymentThisFactor");
        Logger.d("WebService : sendCheckTaraz UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendCheckTaraz ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendCheckTaraz FactorCode is " + factorCode);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorCheckTaraz(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorCheckTaraz(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getCheckTarazRequest(response.body().string());
            }
        });
    }

    // صدا زدن صفحه بانک برای پرداخت
    public static void sendCallBankPageForPayment(long factorCode, int bankCode, String money) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "CallBankPageForPayment")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("FactorCode", "" + factorCode)
                .add("BankCode", "" + bankCode)
                .add("Money", "" + money)
                .build();

        Logger.d("WebService : sendCallBankPageForPayment rt is " + "CallBankPageForPayment");
        Logger.d("WebService : sendCallBankPageForPayment UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendCallBankPageForPayment ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendCallBankPageForPayment FactorCode is " + factorCode);
        Logger.d("WebService : sendCallBankPageForPayment BankCode is " + bankCode);
        Logger.d("WebService : sendCallBankPageForPayment Money is " + money);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorCallBankPage(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorCallBankPage(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getCallBankPageRequest(response.body().string());
            }
        });
    }

    // پرداخت توسط اعتبار
    public static void sendPayFactorFromCredit(long factorCode) {
        OkHttpClient client = new OkHttpClient();
        new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "PayFactorFromCredit")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("FactorCode", "" + factorCode)
                .build();

        Logger.d("WebService : sendPayFactorFromCredit rt is " + "PayFactorFromCredit");
        Logger.d("WebService : sendPayFactorFromCredit UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendPayFactorFromCredit ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendPayFactorFromCredit FactorCode is " + factorCode);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorPayFactorFromCredit(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorPayFactorFromCredit(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getPayFactorFromCreditRequest(response.body().string());
            }
        });
    }

    /* WebService for Club*/
    //گرفتن کلیه امتیازهایی که مشترک گرفته است
    public static void sendClubScoresRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "ClubScores")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();

        Logger.d("WebService : sendClubScoresRequest rt is " + "ClubScores");
        Logger.d("WebService : sendClubScoresRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendClubScoresRequest ExKey is " + G.currentUser.exKey);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorClubScores());
                    return;
                }
                JsonParser.getClubScoresResponse(response.body().string());
            }
        });
    }

    //گرفتن کل امتیاز مشترک
    public static void sendGetClubScoreRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetClubScore")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();

        Logger.d("WebService : sendClubScoresRequest rt is " + "GetClubScore");
        Logger.d("WebService : sendClubScoresRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendClubScoresRequest ExKey is " + G.currentUser.exKey);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetClubScore());
                    return;
                }
                JsonParser.getClubScoreResponse(response.body().string());
            }
        });
    }

    //گرفتن کلیه فشفشه های مشترک
    public static void sendLoadFeshFeshesRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "LoadFeshFeshes")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();

        Logger.d("WebService : sendLoadFeshFeshesRequest rt is " + "LoadFeshFeshes");
        Logger.d("WebService : sendLoadFeshFeshesRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendLoadFeshFeshesRequest ExKey is " + G.currentUser.exKey);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorLoadFeshFeshes());
                    return;
                }
                JsonParser.getLoadFeshFeshesResponse(response.body().string());
            }
        });
    }

    //شروع کردن فشفشه
    public static void sendStartFeshFeshesRequest(long code) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "StartFeshFeshe")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("Code", "" + code)
                .build();
        Logger.d("WebService : sendStartFeshFeshesRequest rt is " + "StartFeshFeshe");
        Logger.d("WebService : sendStartFeshFeshesRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendStartFeshFeshesRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendStartFeshFeshesRequest Code is " + code);
        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorStartFeshFeshes());
                    return;
                }
                JsonParser.getStartFeshFeshesResponse(response.body().string());
            }
        });
    }

    //گرفتن اطلاعات فشفشه جاری
    public static void sendGetCurrentFeshFeshesRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "CurrentFeshFeshe")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();

        Logger.d("WebService : sendStartFeshFeshesRequest rt is " + "CurrentFeshFeshe");
        Logger.d("WebService : sendStartFeshFeshesRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendStartFeshFeshesRequest ExKey is " + G.currentUser.exKey);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetCurrentFeshFeshes());
                    return;
                }
                JsonParser.getCurrentFeshFeshesResponse(response.body().string());
            }
        });
    }

    //پایان دادن به فشفشه
    public static void sendGetEndFeshFeshesRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "EndFeshFeshe")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();

        Logger.d("WebService : sendGetEndFeshFeshesRequest rt is " + "EndFeshFeshe");
        Logger.d("WebService : sendGetEndFeshFeshesRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetEndFeshFeshesRequest ExKey is " + G.currentUser.exKey);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorEndFeshFeshes());
                    return;
                }
                JsonParser.getEndFeshFeshesResponse(response.body().string());
            }
        });
    }

    /* WebService for News*/
    //گرفتن همه اخبارها
    public static void sendGetNewsRequest(long code) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetNews")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("Code", "" + code)
                .build();
        Logger.d("WebService : sendGetNewsRequest rt is " + "GetNews");
        Logger.d("WebService : sendGetNewsRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetNewsRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendGetNewsRequest Code is " + code);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetNews(EnumInternetErrorType.NO_INTERNET_ACCESS));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetNews(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getNewsResponse(response.body().string());
            }
        });
    }

    //گرفتن پیام هشدار
    public static void sendGetAlertRequest(long code) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetAlert")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("Code", "" + code)
                .build();
        Logger.d("WebService : sendGetAlertRequest rt is " + "GetNews");
        Logger.d("WebService : sendGetAlertRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetAlertRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendGetAlertRequest Code is " + code);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetAlert());
                    return;
                }
                JsonParser.getAlertResponse(response.body().string());
            }
        });
    }

    //گرفتن همه Notification ها
    public static void sendGetNotifiesRequest(long code, final boolean showNotification) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetNotifies")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("Code", "" + code)
                .build();
        Logger.d("WebService : sendGetNotifiesRequest rt is " + "GetNotifies");
        Logger.d("WebService : sendGetNotifiesRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetNotifiesRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendGetNotifiesRequest Code is " + code);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetNotifies(EnumInternetErrorType.NO_INTERNET_ACCESS));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetNotifies(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getNotifiesResponse(response.body().string(), showNotification);
            }
        });
    }

    //گرفتن نظرسنجی
    public static void sendGetPollRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetPoll")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();
        Logger.d("WebService : sendGetNotifiesRequest rt is " + "GetPoll");
        Logger.d("WebService : sendGetNotifiesRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetNotifiesRequest ExKey is " + G.currentUser.exKey);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetPoll());
                    return;
                }
                JsonParser.getPollResponse(response.body().string());
            }
        });
    }

    //ثبت نظرسنجی
    public static void sendSetPollRequest(long pollId, String optionId, String des) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "SetPoll")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("PollID", "" + pollId)
                .add("OptionID", "" + optionId)
                .add("Des", "" + des)
                .build();
        Logger.d("WebService : sendSetPollRequest rt is " + "SetPoll");
        Logger.d("WebService : sendSetPollRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendSetPollRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendSetPollRequest PollID is " + pollId);
        Logger.d("WebService : sendSetPollRequest OptionID is " + optionId);
        Logger.d("WebService : sendSetPollRequest Des is " + des);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorSetPoll());
                    return;
                }
                JsonParser.setPollResponse(response.body().string());
            }
        });
    }

    //گرفتن همه تبلیغات
    public static void sendGetAdvsRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetAdvs")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();
        Logger.d("WebService : sendGetAdvsRequest rt is " + "GetAdvs");
        Logger.d("WebService : sendGetAdvsRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetAdvsRequest ExKey is " + G.currentUser.exKey);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetAdvs());
                    return;
                }
                JsonParser.GetAdvsResponse(response.body().string());
            }
        });
    }

    //ثبت ابنکه مشترک تبلیغ خاصی را مشاهده کرده است
    public static void sendSetAdsRepRequest(long adsCode) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "SetAdsRep")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("AdsCode", "" + adsCode)
                .build();
        Logger.d("WebService : sendGetAdvsRequest rt is " + "SetAdsRep");
        Logger.d("WebService : sendGetAdvsRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetAdvsRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendGetAdvsRequest AdsCode is " + adsCode);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorSendSetAdsRep());
                    return;
                }
                JsonParser.setAdsRepResponse(response.body().string());
            }
        });
    }

    /* WebService for Other*/
    //گرفتن اطلاعات شرکت برای نمایش در صفحه دریاره ما
    public static void sendGetIspInfoRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetIspInfo")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();
        Logger.d("WebService : sendGetIspInfoRequest rt is " + "GetIspInfo");
        Logger.d("WebService : sendGetIspInfoRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetIspInfoRequest ExKey is " + G.currentUser.exKey);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetIspInfo());
                    return;
                }
                JsonParser.GetIspInfoResponse(response.body().string());
            }
        });
    }

    //ثبت اینکه مشترک به صفحه موبایل وارد شده است جهت آمار بازدید بعدی
    public static void sendVisitMobileRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "VisitMobile")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();
        Logger.d("WebService : sendVisitMobileRequest rt is " + "VisitMobile");
        Logger.d("WebService : sendVisitMobileRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendVisitMobileRequest ExKey is " + G.currentUser.exKey);

        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorVisitMobile());
                    return;
                }
                JsonParser.visitMobileResponse(response.body().string());
            }
        });
    }

    //گرفتن آپدیت های احتمالی و انجام آنها ، این فقط مخصوص اندروید است
    public static void getUpdateRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetUpdate")
                .build();
        Logger.d("WebService : sendVisitMobileRequest rt is " + "GetUpdate");
        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetUpdate(EnumInternetErrorType.NO_INTERNET_ACCESS));
                //U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetUpdate(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getUpdateResponse(response.body().string());
            }
        });
    }

    /* WebService for ChargeOnline*/
    //صدا زدن آیتم های صفحه اصلی شارژ انلاینست
    public static void sendGetChargeOnlineMainItemsRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetChargeOnlineMainItems")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();
        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        Logger.d("WebService : sendGetChargeOnlineMainItemsRequest rt is " + "GetChargeOnlineMainItems");
        Logger.d("WebService : sendGetChargeOnlineMainItemsRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendGetChargeOnlineMainItemsRequest ExKey is " + G.currentUser.exKey);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetChargeOnlineMainItems());
                    return;
                }
                JsonParser.getChargeOnlineMainItemResponse(response.body().string());
            }
        });
    }

    //چک کردن اینکه باشگاه نمایش داده شود یا نه
    public static void sendCheckChargeOnlineClubRequest(final int whichMenuItem) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "CheckChargeOnlineClub")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("Select", "" + (whichMenuItem - 1))
                .build();
        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        Logger.d("WebService : sendCheckChargeOnlineClubRequest rt is " + "CheckChargeOnlineClub");
        Logger.d("WebService : sendCheckChargeOnlineClubRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendCheckChargeOnlineClubRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendCheckChargeOnlineClubRequest Select is " + (whichMenuItem - 1));
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorCheckChargeOnlineClub());
                    return;
                }
                JsonParser.getCheckChargeOnlineClub(response.body().string(), whichMenuItem);
            }
        });
    }

    //درخواست تمدید سرویس
    public static void sendChargeOnlineForTamdidRequest(int isClub) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "ChargeOnlineForTamdid")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("Club", "" + isClub)
                .build();
        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        Logger.d("WebService : sendChargeOnlineForTamdidRequest rt is " + "ChargeOnlineForTamdid");
        Logger.d("WebService : sendChargeOnlineForTamdidRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendChargeOnlineForTamdidRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendChargeOnlineForTamdidRequest Club is " + isClub);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorChargeOnlineForTamdid());
                    return;
                }
                JsonParser.getChargeOnlineForTamdid(response.body().string());
            }
        });
    }

    //بارگذاری گروه ها
    public static void sendChargeOnlineForLoadGroupsRequest(int isClub) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "ChargeOnlineForLoadGroups")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("Club", "" + isClub)
                .build();
        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        Logger.d("WebService : sendChargeOnlineForLoadGroupsRequest rt is " + "ChargeOnlineForLoadGroups");
        Logger.d("WebService : sendChargeOnlineForLoadGroupsRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendChargeOnlineForLoadGroupsRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendChargeOnlineForLoadGroupsRequest Club is " + isClub);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorChargeOnlineForLoadGroups());
                    return;
                }
                JsonParser.getChargeOnlineForLoadGroups(response.body().string());
            }
        });
    }

    //بارگذاری پکیج ها
    public static void sendChargeOnlineForLoadPackagesRequest(int isClub, long groupCode, final int whichMenuItem) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "ChargeOnlineForLoadPackages")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("Club", "" + isClub)
                .add("GroupCode", "" + groupCode)
                .add("Select", "" + (whichMenuItem - 1))
                .build();
        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        Logger.d("WebService : sendChargeOnlineForLoadPackagesRequest rt is " + "ChargeOnlineForLoadPackages");
        Logger.d("WebService : sendChargeOnlineForLoadPackagesRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendChargeOnlineForLoadPackagesRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendChargeOnlineForLoadPackagesRequest Club is " + isClub);
        Logger.d("WebService : sendChargeOnlineForLoadPackagesRequest GroupCode is " + groupCode);
        Logger.d("WebService : sendChargeOnlineForLoadPackagesRequest Select is " + (whichMenuItem - 1));
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorChargeOnlineForLoadPackages());
                    return;
                }
                JsonParser.getChargeOnlineForLoadPackages(response.body().string());
            }
        });
    }

    //بارگذاری پکیج ها
    public static void sendChargeOnlineForSelectPackageRequest(int isClub, long packageCode) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "ChargeOnlineForSelectPackage")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("Club", "" + isClub)
                .add("PackageCode", "" + packageCode)
                .build();
        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        Logger.d("WebService : sendChargeOnlineForSelectPackageRequest rt is " + "ChargeOnlineForSelectPackage");
        Logger.d("WebService : sendChargeOnlineForSelectPackageRequest UserID is " + G.currentUser.userId);
        Logger.d("WebService : sendChargeOnlineForSelectPackageRequest ExKey is " + G.currentUser.exKey);
        Logger.d("WebService : sendChargeOnlineForSelectPackageRequest Club is " + isClub);
        Logger.d("WebService : sendChargeOnlineForSelectPackageRequest PackageCode is " + packageCode);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnNoAccessServerResponse());
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorChargeOnlineForSelectPackage());
                    return;
                }
                JsonParser.getChargeOnlineForSelectPackage(response.body().string());
            }
        });
    }

    /* WebService for Register*/
    public static void sendGetCityRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetCities")
                .build();
        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        Logger.d("WebService : sendGetCityRequest rt is " + "GetCities");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getCityResponse(response.body().string());
            }
        });
    }

    public static void sendGetCityGroupsRequest(int cityId) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "GetCityGroups")
                .add("CityId", "" + cityId)
                .build();
        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        Logger.d("WebService : sendGetCityGroupsRequest rt is " + "GetCityGroups");
        Logger.d("WebService : sendGetCityGroupsRequest CityId is " + cityId);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCityGroups(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetCityGroups(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getCityGroupsResponse(response.body().string());
            }
        });
    }

    public static void sendRegisterCustomerRequest(int cityId, int groupId, String name, String family, String birthday, String melli, String mobile, String phone, String address, String username, String password) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "RegisterCustomer")
                .add("CityId", "" + cityId)
                .add("GroupId", "" + groupId)
                .add("Name", "" + name)
                .add("Family", "" + family)
                .add("BirthDay", "" + birthday)
                .add("Melli", "" + melli)
                .add("Mobile", "" + mobile)
                .add("Phone", "" + phone)
                .add("Address", "" + address)
                .add("UserName", "" + username)
                .add("Pass", "" + password)
                .build();
        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        Logger.d("WebService : sendRegisterCustomerRequest rt is " + "GetCityGroups");
        Logger.d("WebService : sendRegisterCustomerRequest CityId is " + cityId);
        Logger.d("WebService : sendRegisterCustomerRequest GroupId is " + groupId);
        Logger.d("WebService : sendRegisterCustomerRequest Name is " + name);
        Logger.d("WebService : sendRegisterCustomerRequest Family is " + family);
        Logger.d("WebService : sendRegisterCustomerRequest BirthDay is " + birthday);
        Logger.d("WebService : sendRegisterCustomerRequest Melli is " + melli);
        Logger.d("WebService : sendRegisterCustomerRequest Mobile is " + mobile);
        Logger.d("WebService : sendRegisterCustomerRequest Phone is " + phone);
        Logger.d("WebService : sendRegisterCustomerRequest Address is " + address);
        Logger.d("WebService : sendRegisterCustomerRequest UserName is " + username);
        Logger.d("WebService : sendRegisterCustomerRequest Pass is " + password);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorRegisterCustomer(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorRegisterCustomer(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getRegisterCustomerResponse(response.body().string());
            }
        });
    }

    public static void sendGetLocationsRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "getgpspoints")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .build();
        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        Logger.d("WebService : sendGetLocationsRequest rt is " + "getgpspoints");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorLocations(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorLocations(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.getLocations(response.body().string());
            }
        });
    }

    public static void sendAddScoreRequest(int type){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("rt", "addscore")
                .add("UserID", "" + G.currentUser.userId)
                .add("ExKey", "" + G.currentUser.exKey)
                .add("type", "" + type)
                .build();
        Request request = new Request.Builder().url(G.currentUser.ispUrl + G.WS_PAGE).post(body).build();
        Logger.d("WebService : sendGetLocationsRequest rt is " + "addscore");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.NO_INTERNET_ACCESS));
                U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new EventOnGetErrorGetCities(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                JsonParser.addScoreResponse(response.body().string());
            }
        });
    }
}
