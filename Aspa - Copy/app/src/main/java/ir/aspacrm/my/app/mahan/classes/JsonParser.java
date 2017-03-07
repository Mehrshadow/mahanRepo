package ir.aspacrm.my.app.mahan.classes;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.enums.EnumInternetErrorType;
import ir.aspacrm.my.app.mahan.events.EventOnAddScoreResponse;
import ir.aspacrm.my.app.mahan.events.EventOnChangePasswordResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetAddTicketDetailsResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetAddTicketResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetAdvsResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetAlertResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetBankListResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetBankPageResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetChargeOnlineForLoadGroups;
import ir.aspacrm.my.app.mahan.events.EventOnGetChargeOnlineForLoadPackages;
import ir.aspacrm.my.app.mahan.events.EventOnGetChargeOnlineForPayResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetChargeOnlineForSelectPackage;
import ir.aspacrm.my.app.mahan.events.EventOnGetChargeOnlineForTamdid;
import ir.aspacrm.my.app.mahan.events.EventOnGetChargeOnlineMainItem;
import ir.aspacrm.my.app.mahan.events.EventOnGetChargeOnlineResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetCheckChargeOnlineClub;
import ir.aspacrm.my.app.mahan.events.EventOnGetCheckOrderIdResultResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetCheckTarazResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetCityGroupsResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetCityResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetClubScoreResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetClubScoresResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetConnectionResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetCurrentFeshFesheResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetEndFeshFeshesResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorCallBankPage;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorCheckTaraz;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorGetBankList;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorGetFactorDetail;
import ir.aspacrm.my.app.mahan.events.EventOnGetErrorPayFactorFromCredit;
import ir.aspacrm.my.app.mahan.events.EventOnGetFactorDetailsResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetFactorResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetGraphResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetIspInfoLoginResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetIspInfoResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetIspListResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetIspUrlResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetLoadFeshFeshesResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetLocations;
import ir.aspacrm.my.app.mahan.events.EventOnGetLoginResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetNewsResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetNotifiesResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetPayFactorFromCreditResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetPayOnlineForPayResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetPayOnlineResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetPaymentResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetPollResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetRegConnectAllowResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetRegConnectionResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetRegisterCustomerResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetSelectFactorResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetStartFactorResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetStartFeshFeshesResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetTicketDetailsResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetTicketResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetUnitResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetUpdateResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetUserAccountInfoResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetUserInfoResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetUserLicenseResponse;
import ir.aspacrm.my.app.mahan.events.EventOnGetVisitMobileResponse;
import ir.aspacrm.my.app.mahan.events.EventOnRememberPasswordResponse;
import ir.aspacrm.my.app.mahan.events.EventOnSetAdsRepResponse;
import ir.aspacrm.my.app.mahan.events.EventOnSetPollResponse;
import ir.aspacrm.my.app.mahan.gson.AccountInfoResponse;
import ir.aspacrm.my.app.mahan.gson.AddScoreResponse;
import ir.aspacrm.my.app.mahan.gson.ChargeOnlineGroup;
import ir.aspacrm.my.app.mahan.gson.ChargeOnlineGroupPackage;
import ir.aspacrm.my.app.mahan.gson.ChargeOnlineMainItemResponse;
import ir.aspacrm.my.app.mahan.gson.CityGroupResponse;
import ir.aspacrm.my.app.mahan.gson.CityResponse;
import ir.aspacrm.my.app.mahan.gson.ClubScoresResponse;
import ir.aspacrm.my.app.mahan.gson.CurrentFeshFesheResponse;
import ir.aspacrm.my.app.mahan.gson.FactorDetailResponse;
import ir.aspacrm.my.app.mahan.gson.FactorResponse;
import ir.aspacrm.my.app.mahan.gson.GetAdvsResponse;
import ir.aspacrm.my.app.mahan.gson.GetConnectionsResponse;
import ir.aspacrm.my.app.mahan.gson.GetGraphResponse;
import ir.aspacrm.my.app.mahan.gson.GetIspInfoResponse;
import ir.aspacrm.my.app.mahan.gson.GetPollResponse;
import ir.aspacrm.my.app.mahan.gson.GetUnitsResponse;
import ir.aspacrm.my.app.mahan.gson.GetUpdateResponse;
import ir.aspacrm.my.app.mahan.gson.ISPInfoLoginResponse;
import ir.aspacrm.my.app.mahan.gson.LicenseInfoResponse;
import ir.aspacrm.my.app.mahan.gson.LoadBanksResponse;
import ir.aspacrm.my.app.mahan.gson.LoadFeshFeshesResponse;
import ir.aspacrm.my.app.mahan.gson.LocationsResponse;
import ir.aspacrm.my.app.mahan.gson.LoginResponse;
import ir.aspacrm.my.app.mahan.gson.NewsResponse;
import ir.aspacrm.my.app.mahan.gson.NotifyResponse;
import ir.aspacrm.my.app.mahan.gson.PayFactorFromCreditResponse;
import ir.aspacrm.my.app.mahan.gson.PaymentResponse;
import ir.aspacrm.my.app.mahan.gson.RegConnectResponse;
import ir.aspacrm.my.app.mahan.gson.SearchISPResponse;
import ir.aspacrm.my.app.mahan.gson.SelectFactorResponse;
import ir.aspacrm.my.app.mahan.gson.TicketDetailsResponse;
import ir.aspacrm.my.app.mahan.gson.TicketResponse;
import ir.aspacrm.my.app.mahan.gson.UserInfoResponse;
import ir.aspacrm.my.app.mahan.model.Account;
import ir.aspacrm.my.app.mahan.model.ClubScore;
import ir.aspacrm.my.app.mahan.model.Connection;
import ir.aspacrm.my.app.mahan.model.Factor;
import ir.aspacrm.my.app.mahan.model.FactorDetail;
import ir.aspacrm.my.app.mahan.model.Feshfeshe;
import ir.aspacrm.my.app.mahan.model.Graph;
import ir.aspacrm.my.app.mahan.model.Info;
import ir.aspacrm.my.app.mahan.model.License;
import ir.aspacrm.my.app.mahan.model.Locations;
import ir.aspacrm.my.app.mahan.model.News;
import ir.aspacrm.my.app.mahan.model.Notify;
import ir.aspacrm.my.app.mahan.model.Payment;
import ir.aspacrm.my.app.mahan.model.Ticket;
import ir.aspacrm.my.app.mahan.model.TicketDetail;
import ir.aspacrm.my.app.mahan.model.Unit;
import me.leolin.shortcutbadger.ShortcutBadger;

import static ir.aspacrm.my.app.mahan.G.gson;

public class JsonParser {

    //    private static String adsRepResponse;
    public static void getIspListResponse(String json) {
        Logger.d("JsonParser : getIspListResponse json is  " + json);
        try {
            SearchISPResponse[] isps = gson.fromJson(json, SearchISPResponse[].class);
            EventBus.getDefault().post(new EventOnGetIspListResponse(Arrays.asList(isps)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getIspListResponse() " + e.getMessage());
        }
    }

    public static void getIspInfoResponse(String json) {
        Logger.d("JsonParser : getIspInfoResponse json is  " + json);
        try {
            ISPInfoLoginResponse[] ispInfo = gson.fromJson(json, ISPInfoLoginResponse[].class);
            EventBus.getDefault().post(new EventOnGetIspInfoLoginResponse(ispInfo[0]));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getIspInfoResponse() " + e.getMessage());
        }
    }

    public static void getLoginResponse(String json, long ispId, String ispUrl) {
        Logger.d("JsonParser : getLoginResponse json is  " + json);
        try {
            LoginResponse[] response = gson.fromJson(json, LoginResponse[].class);
            /** be dalil inke ma meghdar ispUrl ra nadarim mabaghi amaliat ra dar safheye login anjam midahim.**/
            EventBus.getDefault().post(new EventOnGetLoginResponse(response[0], ispId, ispUrl));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getLoginResponse() " + e.getMessage());
        }
    }

    public static void getUserLicenseResponse(String json) {
        Logger.d("JsonParser : getUserLicenseResponse json is  " + json);
        try {
            LicenseInfoResponse[] response = gson.fromJson(json, LicenseInfoResponse[].class);
            if (response[0].Result) {
                License existLicenseCurrentUser = new Select().from(License.class).where("userId = ? ", G.currentUser.userId).executeSingle();
                if (existLicenseCurrentUser == null) {
                    /** dar surati ke license baraye in user sabt nashode ast.*/
                    License newLicenseCurrentUser = new License();
                    newLicenseCurrentUser.userId = G.currentUser.userId;
                    newLicenseCurrentUser.ticket = response[0].Ticket;
                    newLicenseCurrentUser.adv = response[0].Adv;
                    newLicenseCurrentUser.changePass = response[0].ChangePass;
//                    newLicenseCurrentUser.regConnect = response[0].RegConnect;
                    newLicenseCurrentUser.chargeOnline = response[0].Chargeonline;
                    newLicenseCurrentUser.club = response[0].Club;
                    newLicenseCurrentUser.payOnline = response[0].PayOnline;
                    newLicenseCurrentUser.save();
                    G.currentLicense = newLicenseCurrentUser;
                } else {
                    /** dar surati ke az ghabl baraye user license sabt shode bashad.*/
                    existLicenseCurrentUser.ticket = response[0].Ticket;
                    existLicenseCurrentUser.adv = response[0].Adv;
                    existLicenseCurrentUser.changePass = response[0].ChangePass;
//                    existLicenseCurrentUser.regConnect = response[0].RegConnect;
                    existLicenseCurrentUser.chargeOnline = response[0].Chargeonline;
                    existLicenseCurrentUser.club = response[0].Club;
                    existLicenseCurrentUser.payOnline = response[0].PayOnline;
                    existLicenseCurrentUser.save();
                    G.currentLicense = existLicenseCurrentUser;
                }


            }
            EventBus.getDefault().post(new EventOnGetUserLicenseResponse());
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getUserLicenseResponse() " + e.getMessage());
        }
    }

    public static void getUserAccountInfoResponse(String json) {
        Logger.d("JsonParser : getUserAccountInfoResponse json is  " + json);
        try {
            AccountInfoResponse[] response = gson.fromJson(json, AccountInfoResponse[].class);
            if (response[0].Result) {
                Account existAccountCurrentUser = new Select().from(Account.class).where("userId = ?", G.currentUser.userId).executeSingle();
                if (existAccountCurrentUser == null) {
                    existAccountCurrentUser = new Account();
                    existAccountCurrentUser.userId = G.currentUser.userId;
                    existAccountCurrentUser.balance = response[0].Balance;
                    existAccountCurrentUser.rHour = response[0].RHour;
                    existAccountCurrentUser.rDay = response[0].Rday;
                    existAccountCurrentUser.rTraffic = response[0].Rtraffic;
                    existAccountCurrentUser.grpId = response[0].GrpId;
                    existAccountCurrentUser.grpName = response[0].GrpName;
                    existAccountCurrentUser.hPerc = response[0].Hperc;
                    existAccountCurrentUser.tPerc = response[0].Tperc;
                    existAccountCurrentUser.dPerc = response[0].Dperc;
                    existAccountCurrentUser.grpName = response[0].GrpName;
                    existAccountCurrentUser.pkgName = response[0].PkgName;
                    existAccountCurrentUser.online = response[0].Online;
                    existAccountCurrentUser.lastNewsID = response[0].LastNewsID;
                    existAccountCurrentUser.lastNotifyID = response[0].LastNotifyID;
                    existAccountCurrentUser.lastTicketID = response[0].LastTicketID;
                    existAccountCurrentUser.expDate = response[0].ExpDate;
                    existAccountCurrentUser.farsiExpDate = response[0].FarsiExpDate;
                    existAccountCurrentUser.regConnect = response[0].RegConnect;
                    existAccountCurrentUser.save();
                } else {
                    existAccountCurrentUser.balance = response[0].Balance;
                    existAccountCurrentUser.rHour = response[0].RHour;
                    existAccountCurrentUser.rDay = response[0].Rday;
                    existAccountCurrentUser.rTraffic = response[0].Rtraffic;
                    existAccountCurrentUser.grpId = response[0].GrpId;
                    existAccountCurrentUser.grpName = response[0].GrpName;
                    existAccountCurrentUser.hPerc = response[0].Hperc;
                    existAccountCurrentUser.tPerc = response[0].Tperc;
                    existAccountCurrentUser.dPerc = response[0].Dperc;
                    existAccountCurrentUser.grpName = response[0].GrpName;
                    existAccountCurrentUser.pkgName = response[0].PkgName;
                    existAccountCurrentUser.online = response[0].Online;
                    existAccountCurrentUser.lastNewsID = response[0].LastNewsID;
                    existAccountCurrentUser.lastNotifyID = response[0].LastNotifyID;
                    existAccountCurrentUser.lastTicketID = response[0].LastTicketID;
                    existAccountCurrentUser.expDate = response[0].ExpDate;
                    existAccountCurrentUser.farsiExpDate = response[0].FarsiExpDate;
                    existAccountCurrentUser.regConnect = response[0].RegConnect;
                    existAccountCurrentUser.save();
                }
                G.currentAccount = existAccountCurrentUser;
            }
            EventBus.getDefault().post(new EventOnGetUserAccountInfoResponse(response[0]));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getUserAccountInfoResponse() " + e.getMessage());
        }
    }

    public static void getGetUserInfoResponse(String json) {
        Logger.d("JsonParser : getGetUserInfoResponse json is  " + json);
        try {
            UserInfoResponse[] response = gson.fromJson(json, UserInfoResponse[].class);
            if (response[0].result) {
                Info info = new Select().from(Info.class).where("userId = ? ", G.currentUser.userId).executeSingle();
                if (info == null) {
                    Info newInfo = new Info();
                    newInfo.userId = G.currentUser.userId;
                    newInfo.fullName = response[0].fullName;
                    newInfo.username = response[0].username;
                    newInfo.mobileNo = response[0].mobileNo;
                    newInfo.resellerName = response[0].resellerName;
                    newInfo.firstCon = response[0].firstCon;
                    newInfo.lastCon = response[0].lastCon;
                    newInfo.serviceType = response[0].serviceType;
                    newInfo.status = response[0].status;
                    newInfo.save();
                    G.currentUserInfo = newInfo;
                } else {
                    info.fullName = response[0].fullName;
                    info.username = response[0].username;
                    info.mobileNo = response[0].mobileNo;
                    info.resellerName = response[0].resellerName;
                    info.firstCon = response[0].firstCon;
                    info.lastCon = response[0].lastCon;
                    info.serviceType = response[0].serviceType;
                    info.status = response[0].status;
                    info.save();

                    G.currentUserInfo = info;
                }
            }

            EventBus.getDefault().post(new EventOnGetUserInfoResponse(response[0]));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getGetUserInfoResponse() " + e.getMessage());
        }
    }

    public static void sendRememberPasswordResponse(String json) {
        Logger.d("JsonParser : sendRememberPasswordResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean status = jsonObject.getBoolean("Result");
            String message = jsonObject.getString("Msg");
            if (status)
                message = "درخواست شما برای بازیابی رمز عبور ارسال شد.";
            else
                message = message + ".";
            EventBus.getDefault().post(new EventOnRememberPasswordResponse(status, message));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on sendRememberPasswordResponse() " + e.getMessage());
        }
    }

    public static void getChangePasswordResponse(String json) {
        Logger.d("JsonParser : getChangePasswordResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean status = jsonObject.getBoolean("Result");
            String message = jsonObject.getString("Message");
            if (status)
                message = "درخواست شما برای تغییر رمز عبور با موفقیت انجام گرفت.";
            else
                message = "عملیات تغییر رمز عبور ناموفق بود، " + message + ".";
            EventBus.getDefault().post(new EventOnChangePasswordResponse(status, message));
            final String finalMessage = message;
            G.handler.post(new Runnable() {
                @Override
                public void run() {
                    DialogClass dlg = new DialogClass();
                    dlg.showMessageDialog("پیغام", finalMessage);
                }
            });
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChangePasswordResponse() " + e.getMessage());
        }
    }

    public static void getPaymentResponse(String json) {
        Logger.d("JsonParser : getPaymentResponse json is  " + json);
        try {
            PaymentResponse[] payments = gson.fromJson(json, PaymentResponse[].class);
            List<Payment> paymentList = new ArrayList<>();
            U.deletePaymentTableItem();
            for (PaymentResponse payment : payments) {
                Payment newPayment = new Payment();
                newPayment.userId = G.currentUser.userId;
                newPayment.date = payment.Date;
                newPayment.des = payment.Des;
                newPayment.price = payment.Price;
                newPayment.type = payment.Type;
                newPayment.save();
                paymentList.add(newPayment);
            }
            EventBus.getDefault().post(new EventOnGetPaymentResponse(paymentList));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getPaymentResponse() " + e.getMessage());
        }
    }

    public static void getFactorResponse(String json) {
        Logger.d("JsonParser : getFactorResponse json is  " + json);
        try {
            FactorResponse[] response = gson.fromJson(json, FactorResponse[].class);
            /** delete all item in Factor table and save new factor on it.*/
            U.deleteTicketTableItem();
            List<Factor> factors = new ArrayList<>();
            for (FactorResponse factor : response) {
                Factor newFactor = new Factor();
                newFactor.userId = G.currentUser.userId;
                newFactor.factorId = factor.ID;
                newFactor.date = factor.Date;
                newFactor.startDate = factor.StartDate;
                newFactor.expireDate = factor.ExpireDate;
                newFactor.price = factor.Price;
                newFactor.des = factor.Des;
                newFactor.status = factor.Status;
                newFactor.discount = factor.Discount;
                newFactor.tax = factor.Tax;
                newFactor.amount = factor.Amount;
                newFactor.save();
                factors.add(newFactor);
            }
            EventBus.getDefault().post(new EventOnGetFactorResponse(factors));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getFactorResponse() " + e.getMessage());
        }
    }

    public static void getFactorDetailResponse(String json, long factorCode) {
        Logger.d("JsonParser : getFactorDetailResponse json is  " + json);
        try {
            FactorDetailResponse[] response = gson.fromJson(json, FactorDetailResponse[].class);
            /** delete all item in FactorDetail table and save new FactorDetail on it.*/
            U.deleteFactorDetailTableItem(factorCode);
            for (FactorDetailResponse factorDetail : response) {
                FactorDetail newFactorDetail = new FactorDetail();
                newFactorDetail.userId = G.currentUser.userId;
                newFactorDetail.parentId = factorCode;
                newFactorDetail.name = factorDetail.Name;
                newFactorDetail.des = factorDetail.Des;
                newFactorDetail.price = factorDetail.Price;
                newFactorDetail.save();
            }
            EventBus.getDefault().post(new EventOnGetFactorDetailsResponse(new ArrayList<FactorDetailResponse>(Arrays.asList(response))));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getFactorDetailResponse() " + e.getMessage());
            EventBus.getDefault().post(new EventOnGetErrorGetFactorDetail(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED, factorCode));
        }
    }

    public static void getStartFactorResponse(String json) {
        Logger.d("JsonParser : getStartFactorResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean status = jsonObject.getBoolean("Result");
            String message = jsonObject.getString("Msg");
            EventBus.getDefault().post(new EventOnGetStartFactorResponse(status, message));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getStartFactorResponse() " + e.getMessage());
        }
    }

    public static void getUnitsResponse(String json) {
        Logger.d("JsonParser : getUnitsResponse json is  " + json);
        try {
            GetUnitsResponse[] response = gson.fromJson(json, GetUnitsResponse[].class);
            /** delete all item in unit table and save new unit on it.*/
            U.deleteUnitTableItem();
            for (GetUnitsResponse unit : response) {
                Unit newUnit = new Unit();
                newUnit.userId = G.currentUser.userId;
                newUnit.code = unit.Code;
                newUnit.name = unit.Name;
                newUnit.save();
            }
            EventBus.getDefault().post(new EventOnGetUnitResponse(Arrays.asList(response)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getUnitsResponse() " + e.getMessage());
        }
    }

    public static void getAddTicketResponse(String json) {
        Logger.d("JsonParser : getAddTicketResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean status = jsonObject.getBoolean("Result");
            long code = jsonObject.getLong("Code");
            EventBus.getDefault().post(new EventOnGetAddTicketResponse(status, code));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getAddTicketResponse() " + e.getMessage());
        }
    }

    public static void getAddTicketDetailResponse(String json) {
        Logger.d("JsonParser : getAddTicketDetailResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean status = jsonObject.getBoolean("Result");
            String message = jsonObject.getString("Msg");
            EventBus.getDefault().post(new EventOnGetAddTicketDetailsResponse(status, message));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getAddTicketDetailResponse() " + e.getMessage());
        }
    }

    public static void getTicketsResponse(String json) {
        Logger.d("JsonParser : getTicketsResponse json is  " + json);
        try {
            TicketResponse[] response = gson.fromJson(json, TicketResponse[].class);
            /** delete all item in ticket table and save new tickets on it.*/
            U.deleteTicketTableItem();
            for (TicketResponse ticket : response) {
                Ticket newTicket = new Ticket();
                newTicket.userId = G.currentUser.userId;
                newTicket.code = ticket.Code;
                newTicket.date = ticket.Date;
                newTicket.open = ticket.Open;
                newTicket.priority = ticket.Priority;
                newTicket.state = ticket.State;
                newTicket.title = ticket.Title;
                newTicket.save();
            }
            EventBus.getDefault().post(new EventOnGetTicketResponse(Arrays.asList(response)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getTicketsResponse() " + e.getMessage());
        }
    }

    public static void getTicketDetailsResponse(String json, long ticketCode) {
        Logger.d("JsonParser : getTicketDetailsResponse json is  " + json);
        try {
            TicketDetailsResponse[] response = gson.fromJson(json, TicketDetailsResponse[].class);
            /** delete all item in ticketDetail table with special parentCode and save new ticketDetails on it.*/
            U.deleteTicketDetailTableItem(ticketCode);
            for (TicketDetailsResponse ticketDetail : response) {
                TicketDetail newTicketDetail = new TicketDetail();
                newTicketDetail.userId = G.currentUser.userId;
                newTicketDetail.parentCode = ticketCode;
                newTicketDetail.user = ticketDetail.User;
                newTicketDetail.date = ticketDetail.Date;
                newTicketDetail.state = ticketDetail.State;
                newTicketDetail.text = ticketDetail.Text;
                newTicketDetail.unit = ticketDetail.Unit;
                newTicketDetail.save();
            }
            EventBus.getDefault().post(new EventOnGetTicketDetailsResponse(Arrays.asList(response)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getTicketDetailsResponse() " + e.getMessage());
        }
    }

    public static void getRegConnectAllowResponse(String json) {
        Logger.d("JsonParser : getRegConnectAllowResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean status = jsonObject.getBoolean("Result");
            EventBus.getDefault().post(new EventOnGetRegConnectAllowResponse(status));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getRegConnectAllowResponse() " + e.getMessage());
        }
    }

    public static void getRegConnectResponse(String json) {
        Logger.d("JsonParser : getRegConnectResponse json is  " + json);
        try {
            RegConnectResponse[] response = gson.fromJson(json, RegConnectResponse[].class);
            EventBus.getDefault().post(new EventOnGetRegConnectionResponse(Arrays.asList(response)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getRegConnectResponse() " + e.getMessage());
        }
    }

    public static void getConnectionsResponse(String json) {
        Logger.d("JsonParser : getConnectionsResponse json is  " + json);
        try {
            GetConnectionsResponse[] response = gson.fromJson(json, GetConnectionsResponse[].class);
            List<Connection> connections = new ArrayList<>();
            U.deleteConnectionTableItem();
            for (GetConnectionsResponse connection : response) {
                Connection newConnection = new Connection();
                newConnection.userId = G.currentUser.userId;
                newConnection.startTime = connection.StartTime;
                newConnection.endTime = connection.EndTime;
                newConnection.duration = connection.Duration;
                newConnection.send = connection.Send;
                newConnection.recieve = connection.Recieve;
                newConnection.trafficUsed = connection.TrafficUsed;
                newConnection.save();
                connections.add(newConnection);
            }
            EventBus.getDefault().post(new EventOnGetConnectionResponse(connections));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getConnectionsResponse() " + e.getMessage());
        }
    }

    public static void getGraphResponse(String json) {
        Logger.d("JsonParser : getGraphResponse json is  " + json);
        try {
            GetGraphResponse[] response = gson.fromJson(json, GetGraphResponse[].class);
            Graph newGraph = new Graph();
            if (response.length > 0) {
                new Delete().from(Graph.class).where("UserId = ? ").execute();
                newGraph.userId = G.currentUser.userId;
                newGraph.xData = response[0].XData;
                newGraph.yData = response[0].YData;
                newGraph.save();
            }
            EventBus.getDefault().post(new EventOnGetGraphResponse(newGraph));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getGraphResponse() " + e.getMessage());
        }
    }

    public static void getChargeOnlineResponse(String html) {
        Logger.d("JsonParser : getChargeOnlineResponse html is  " + html);
        try {
            EventBus.getDefault().post(new EventOnGetChargeOnlineResponse(html));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChargeOnlineResponse() " + e.getMessage());
        }
    }

    public static void getPayOnlineResponse(String html) {
        Logger.d("JsonParser : getPayOnlineResponse html is  " + html);
        try {
            EventBus.getDefault().post(new EventOnGetPayOnlineResponse(html));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getPayOnlineResponse() " + e.getMessage());
        }
    }

    public static void getClubScoreResponse(String json) {
        Logger.d("JsonParser : getClubScoreResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean result = jsonObject.getBoolean("Result");
            int score = jsonObject.getInt("Score");
            EventBus.getDefault().post(new EventOnGetClubScoreResponse(result, score));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getClubScoreResponse() " + e.getMessage());
        }
    }

    public static void getClubScoresResponse(String json) {
        Logger.d("JsonParser : getClubScoresResponse json is  " + json);
        try {
            ClubScoresResponse[] response = gson.fromJson(json, ClubScoresResponse[].class);
            List<ClubScore> clubScores = new ArrayList<>();
            /** delete all item in ticketDetail table with special parentCode and save new ticketDetails on it.*/
            U.deleteClubScoreTableItem();
            for (ClubScoresResponse score : response) {
                ClubScore newClubScore = new ClubScore();
                newClubScore.userId = G.currentUser.userId;
                newClubScore.title = score.Title;
                newClubScore.des = score.Des;
                newClubScore.score = score.Score;
                newClubScore.save();
                clubScores.add(newClubScore);
            }
            EventBus.getDefault().post(new EventOnGetClubScoresResponse(clubScores));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getClubScoresResponse() " + e.getMessage());
        }
    }

    public static void getLoadFeshFeshesResponse(String json) {
        Logger.d("JsonParser : getLoadFeshFeshesResponse json is  " + json);
        try {
            LoadFeshFeshesResponse[] response = gson.fromJson(json, LoadFeshFeshesResponse[].class);
            List<Feshfeshe> feshfesheList = new ArrayList<>();
            /** delete all item in ticketDetail table with special parentCode and save new ticketDetails on it.*/
            U.deleteFeshfesheTableItem();
            for (LoadFeshFeshesResponse feshfeshe : response) {
                Feshfeshe newFeshfeshe = new Feshfeshe();
                newFeshfeshe.userId = G.currentUser.userId;
                newFeshfeshe.packageName = feshfeshe.Package;
                newFeshfeshe.code = feshfeshe.Code;
                newFeshfeshe.started = feshfeshe.Started;
                newFeshfeshe.save();
                feshfesheList.add(newFeshfeshe);
            }
            EventBus.getDefault().post(new EventOnGetLoadFeshFeshesResponse(feshfesheList));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getLoadFeshFeshesResponse() " + e.getMessage());
        }
    }

    public static void getStartFeshFeshesResponse(String json) {
        Logger.d("JsonParser : getStartFeshFeshesResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean status = jsonObject.getBoolean("Result");
            String message = jsonObject.getString("Msg");
            EventBus.getDefault().post(new EventOnGetStartFeshFeshesResponse(status, message));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getStartFeshFeshesResponse() " + e.getMessage());
        }
    }

    public static void getCurrentFeshFeshesResponse(String json) {
        Logger.d("JsonParser : getCurrentFeshFeshesResponse json is  " + json);
        try {
            CurrentFeshFesheResponse[] response = gson.fromJson(json, CurrentFeshFesheResponse[].class);
            EventBus.getDefault().post(new EventOnGetCurrentFeshFesheResponse(Arrays.asList(response)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getCurrentFeshFeshesResponse() " + e.getMessage());
        }
    }

    public static void getEndFeshFeshesResponse(String json) {
        Logger.d("JsonParser : getEndFeshFeshesResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean status = jsonObject.getBoolean("Result");
            String message = jsonObject.getString("Msg");
            EventBus.getDefault().post(new EventOnGetEndFeshFeshesResponse(status, message));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getEndFeshFeshesResponse() " + e.getMessage());
        }
    }

    public static void getNewsResponse(String json) {
        Logger.d("JsonParser : getNewsResponse json is  " + json);
        try {
            NewsResponse[] response = gson.fromJson(json, NewsResponse[].class);
            for (NewsResponse news : response) {
                News existNews = new Select().from(News.class).where("UserId = ? AND NewsID = ? ", G.currentUser.userId, news.NewsID).executeSingle();
                if (existNews == null) {
                    News newNews = new News();
                    newNews.userId = G.currentUser.userId;
                    newNews.bodyText = news.BodyText;
                    newNews.title = news.Title;
                    newNews.important = news.Important;
                    newNews.newsDate = news.NewsDate;
                    newNews.newsID = news.NewsID;
                    newNews.save();
                } else {
                    existNews.bodyText = news.BodyText;
                    existNews.title = news.Title;
                    existNews.important = news.Important;
                    existNews.newsDate = news.NewsDate;
                    existNews.save();
                }
            }
            EventBus.getDefault().post(new EventOnGetNewsResponse(Arrays.asList(response)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getNewsResponse() " + e.getMessage());
        }
    }

    public static void getAlertResponse(String json) {
        Logger.d("JsonParser : getAlertResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean status = jsonObject.getBoolean("Result");
            String message = jsonObject.getString("Message");
            EventBus.getDefault().post(new EventOnGetAlertResponse(status, message));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getAlertResponse() " + e.getMessage());
        }
    }

    public static void getNotifiesResponse(String json, boolean showNotification) {
        Logger.d("JsonParser : getNotifiesResponse json is  " + json);
        try {
            NotifyResponse[] response = gson.fromJson(json, NotifyResponse[].class);
            int countNewNotify = 0;
            for (NotifyResponse notify : response) {
                Notify existNotify = new Select().from(Notify.class).where("UserId = ? AND NotifyCode = ? ", G.currentUser.userId, notify.Code).executeSingle();
                if (existNotify == null) {
                    Notify newNotify = new Notify();
                    newNotify.userId = G.currentUser.userId;
                    newNotify.notifyCode = notify.Code;
                    newNotify.message = notify.Msg;
                    newNotify.isSeen = false;
                    newNotify.save();
                    countNewNotify++;
                } else {
                    existNotify.message = notify.Msg;
                    existNotify.save();
                }
            }
            EventBus.getDefault().post(new EventOnGetNotifiesResponse(Arrays.asList(response)));
            if (countNewNotify > 0 && showNotification) {
                if (countNewNotify == 1) {
                    ShortcutBadger.applyCount(G.context, 1); //for 1.1.4+
                    List<Notify> unSeenNotify = new Select()
                            .from(Notify.class)
                            .where("UserId = ? AND IsSeen = ?", G.currentUser.userId, false)
                            .execute();
                    int countUnSeenNotify = unSeenNotify.size();
                    if (countUnSeenNotify != 0) {
                        ShortcutBadger.applyCountOrThrow(G.context, countUnSeenNotify); //for 1.1.4+
                    }
                    U.sendNotification(response[response.length - 1].Msg);
                } else {
                    List<Notify> unSeenNotify = new Select()
                            .from(Notify.class)
                            .where("UserId = ? AND IsSeen = ?", G.currentUser.userId, false)
                            .execute();
                    int countUnSeenNotify = unSeenNotify.size();
                    if (countUnSeenNotify != 0) {
                        ShortcutBadger.applyCountOrThrow(G.context, countUnSeenNotify); //for 1.1.4+
                    }
                    U.sendNotification("" + countNewNotify + " پیغام جدید");
                }
            }
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getNotifiesResponse() " + e.getMessage());
        }
    }

    public static void getPollResponse(String json) {
        Logger.d("JsonParser : getPollResponse json is  " + json);
        try {
            GetPollResponse[] response = gson.fromJson(json, GetPollResponse[].class);
            EventBus.getDefault().post(new EventOnGetPollResponse(response[0]));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getPollResponse() " + e.getMessage());
        }
    }

    public static void setPollResponse(String json) {
        Logger.d("JsonParser : setPollResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean status = jsonObject.getBoolean("Result");
            EventBus.getDefault().post(new EventOnSetPollResponse(status));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on setPollResponse() " + e.getMessage());
        }
    }

    public static void GetAdvsResponse(String json) {
        Logger.d("JsonParser : GetAdvsResponse json is  " + json);
        try {
            GetAdvsResponse[] response = gson.fromJson(json, GetAdvsResponse[].class);
            EventBus.getDefault().post(new EventOnGetAdvsResponse(Arrays.asList(response)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on GetAdvsResponse() " + e.getMessage());
        }
    }

    public static void setAdsRepResponse(String json) {
        Logger.d("JsonParser : setAdsRepResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean status = jsonObject.getBoolean("Result");
            EventBus.getDefault().post(new EventOnSetAdsRepResponse(status));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on setAdsRepResponse() " + e.getMessage());
        }
    }

    public static void GetIspInfoResponse(String json) {
        Logger.d("JsonParser : GetIspInfoResponse json is  " + json);
        try {
            GetIspInfoResponse[] response = gson.fromJson(json, GetIspInfoResponse[].class);
            EventBus.getDefault().post(new EventOnGetIspInfoResponse(response[0]));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on GetIspInfoResponse() " + e.getMessage());
        }
    }

    public static void visitMobileResponse(String json) {
        Logger.d("JsonParser : VisitMobileResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean status = jsonObject.getBoolean("Result");
            EventBus.getDefault().post(new EventOnGetVisitMobileResponse(status));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on VisitMobileResponse() " + e.getMessage());
        }
    }

    public static void getUpdateResponse(String json) {
        Logger.d("JsonParser : getUpdateResponse json is  " + json);
        try {
            GetUpdateResponse[] response = gson.fromJson(json, GetUpdateResponse[].class);
            EventBus.getDefault().post(new EventOnGetUpdateResponse(response[0]));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getUpdateResponse() " + e.getMessage());
        }
    }

    public static void getChargeOnlineMainItemResponse(String json) {
        Logger.d("JsonParser : getChargeOnlineMainItemResponse json is  " + json);
        try {
            ChargeOnlineMainItemResponse[] response = gson.fromJson(json, ChargeOnlineMainItemResponse[].class);
            EventBus.getDefault().post(new EventOnGetChargeOnlineMainItem(response[0]));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChargeOnlineMainItemResponse() " + e.getMessage());
        }
    }

    public static void getCheckChargeOnlineClub(String json, int whichMenuItem) {
        Logger.d("JsonParser : getCheckChargeOnlineClub json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean status = jsonObject.getBoolean("Result");
            boolean isClub = jsonObject.getBoolean("Club");
            if (!status) {
                G.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        DialogClass dlg = new DialogClass();
                        dlg.showMessageDialog("خطا", "" + "خطا در دریافت اطلاعات اکانتینگ شما رخ داده است لطفا مجددا چک کنید");
                    }
                });
            }
            EventBus.getDefault().post(new EventOnGetCheckChargeOnlineClub(status, isClub, whichMenuItem));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChangePasswordResponse() " + e.getMessage());
        }
    }

    public static void getChargeOnlineForTamdid(String json) {
        Logger.d("JsonParser : getChargeOnlineForTamdid json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean result = jsonObject.getBoolean("Result");
            String message = jsonObject.getString("Msg");
            long factorCode = jsonObject.getLong("FactorCode");
//            if(!status)  {
//                G.handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        G.dialog.showMessageDialog("" +  "خطا در دریافت اطلاعات اکانتینگ شما رخ داده است لطفا مجددا چک کنید");
//                    }
//                });
//            }
            EventBus.getDefault().post(new EventOnGetChargeOnlineForTamdid(result, message, factorCode));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChangePasswordResponse() " + e.getMessage());
        }

    }

    public static void getSelectFactorResponse(String json) {
        Logger.d("JsonParser : getSelectFactorResponse json is  " + json);
        try {
            SelectFactorResponse[] response = gson.fromJson(json, SelectFactorResponse[].class);
            EventBus.getDefault().post(new EventOnGetSelectFactorResponse(response[0]));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getSelectFactorResponse() " + e.getMessage());
        }
    }

    public static void getChargeOnlineForLoadGroups(String json) {
        Logger.d("JsonParser : getChargeOnlineForLoadGroups json is  " + json);
        try {
            ChargeOnlineGroup[] response = gson.fromJson(json, ChargeOnlineGroup[].class);
            EventBus.getDefault().post(new EventOnGetChargeOnlineForLoadGroups(Arrays.asList(response)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChargeOnlineForLoadGroups() " + e.getMessage());
        }
    }

    public static void getChargeOnlineForLoadPackages(String json) {
        Logger.d("JsonParser : getChargeOnlineForLoadPackages json is  " + json);
        try {
            ChargeOnlineGroupPackage[] response = gson.fromJson(json, ChargeOnlineGroupPackage[].class);
            EventBus.getDefault().post(new EventOnGetChargeOnlineForLoadPackages(Arrays.asList(response)));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChargeOnlineForLoadPackages() " + e.getMessage());
        }
    }

    public static void getChargeOnlineForSelectPackage(String json) {
        Logger.d("JsonParser : getChargeOnlineForSelectPackage json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean result = jsonObject.getBoolean("Result");
            String message = jsonObject.getString("Msg");
            long factorCode = jsonObject.getLong("FactorCode");
//            if(!status)  {
//                G.handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        G.dialog.showMessageDialog("" +  "خطا در دریافت اطلاعات اکانتینگ شما رخ داده است لطفا مجددا چک کنید");
//                    }
//                });
//            }
            EventBus.getDefault().post(new EventOnGetChargeOnlineForSelectPackage(result, message, factorCode));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChargeOnlineForSelectPackage() " + e.getMessage());
        }
    }

    public static void getIspUrlResponse(String json) {
        Logger.d("JsonParser : getIspUrlResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String name = jsonObject.getString("Name");
            String url = jsonObject.getString("MyLink");
            if (url.length() != 0) {
                G.currentUser.ispUrl = url;
                if (G.currentUser.userId != 0) {
                    /** dar surati ke shakhs login karde bashad etelaate aan ra beruz mikonim.*/
                    G.currentUser.save();
                }
            }
            EventBus.getDefault().post(new EventOnGetIspUrlResponse(G.currentUser.ispUrl));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getIspUrlResponse() " + e.getMessage());
        }
    }

    public static void getPayOnlineForPayRequest(String json) {
        Logger.d("JsonParser : getPayOnlineForPayRequest json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            long orderId = jsonObject.getLong("OrderId");
            String orderUssd = jsonObject.getString("OrderId2");
            EventBus.getDefault().post(new EventOnGetPayOnlineForPayResponse(orderId, orderUssd));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getPayOnlineForPayRequest() " + e.getMessage());
        }
    }

    public static void getCheckOrderIdResultRequest(String json) {
        Logger.d("JsonParser : getCheckOrderIdResultRequest json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean result = jsonObject.getBoolean("Result");
            String message = jsonObject.getString("Msg");
            long factorCode = jsonObject.getLong("FactorCode");
            EventBus.getDefault().post(new EventOnGetCheckOrderIdResultResponse(result, message, factorCode));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getCheckOrderIdResultRequest() " + e.getMessage());
        }
    }

    public static void getChargeOnlineForPayRequest(String json) {
        Logger.d("JsonParser : getChargeOnlineForPayRequest json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            long orderId = jsonObject.getLong("OrderId");
            String orderUssd = jsonObject.getString("OrderId2");
            EventBus.getDefault().post(new EventOnGetChargeOnlineForPayResponse(orderId, orderUssd));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getChargeOnlineForPayRequest() " + e.getMessage());
        }
    }

    public static void getCityResponse(String json) {
        Logger.d("JsonParser : getCityResponse json is  " + json);
        try {
            CityResponse[] response = gson.fromJson(json, CityResponse[].class);
            EventBus.getDefault().post(new EventOnGetCityResponse(response));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getCityResponse() " + e.getMessage());
        }
    }

    public static void getCityGroupsResponse(String json) {
        Logger.d("JsonParser : getCityGroupsResponse json is  " + json);
        try {
            CityGroupResponse[] response = gson.fromJson(json, CityGroupResponse[].class);
            EventBus.getDefault().post(new EventOnGetCityGroupsResponse(response));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getCityGroupsResponse() " + e.getMessage());
        }
    }

    public static void getRegisterCustomerResponse(String json) {
        Logger.d("JsonParser : getRegisterCustomerResponse json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            boolean result = jsonObject.getBoolean("Result");
            String message = jsonObject.getString("Msg");
            EventBus.getDefault().post(new EventOnGetRegisterCustomerResponse(result, message));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getRegisterCustomerResponse() " + e.getMessage());
        }
    }

    public static void getBankListRequest(String json) {
        Logger.d("JsonParser : getBankListRequest json is  " + json);
        try {
            LoadBanksResponse[] response = gson.fromJson(json, LoadBanksResponse[].class);
//            /** delete all item in FactorDetail table and save new FactorDetail on it.*/
            //U.deleteFactorDetailTableItem(factorCode);
            EventBus.getDefault().post(new EventOnGetBankListResponse(response));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getBankListRequest() " + e.getMessage());
            EventBus.getDefault().post(new EventOnGetErrorGetBankList(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
        }
    }

    public static void getCheckTarazRequest(String json) {
        Logger.d("JsonParser : getCheckTarazRequest json is  " + json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            int taraz = jsonObject.getInt("taraz");
            EventBus.getDefault().post(new EventOnGetCheckTarazResponse(taraz));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getCheckTarazRequest() " + e.getMessage());
            EventBus.getDefault().post(new EventOnGetErrorCheckTaraz(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
        }
    }

    public static void getCallBankPageRequest(String json) {
        Logger.d("JsonParser : getCallBankPageRequest json is  " + json);
        try {
            EventBus.getDefault().post(new EventOnGetBankPageResponse(json));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getCallBankPageRequest() " + e.getMessage());
            EventBus.getDefault().post(new EventOnGetErrorCallBankPage(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
        }
    }

    public static void getPayFactorFromCreditRequest(String json) {
        Logger.d("JsonParser : getPayFactorFromCreditRequest json is  " + json);
        try {
            PayFactorFromCreditResponse[] response = gson.fromJson(json, PayFactorFromCreditResponse[].class);

            EventBus.getDefault().post(new EventOnGetPayFactorFromCreditResponse(response[0]));
        } catch (Exception e) {
            Logger.e("JsonParser : Error on getPayFactorFromCreditRequest() " + e.getMessage());
            EventBus.getDefault().post(new EventOnGetErrorPayFactorFromCredit(EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
        }
    }

    public static void getLocations(String json) {
        Logger.d("JsonParser : getLocations json is  " + json);
        try {
            LocationsResponse[] response = gson.fromJson(json, LocationsResponse[].class);
            U.deleteLocationsItem();
            for (int i = 0; i < response.length; i++) {
                ActiveAndroid.beginTransaction();
                Locations locations = new Locations();
                locations.setLatitude(response[i].positionX);
                locations.setDes(response[i].name);
                locations.setLongitude(response[i].positionY);
                locations.setStartDate(response[i].startDate);
                locations.setEndDate(response[i].endDate);
                locations.setScoreTypeCode(response[i].scoreTypeCode);
                locations.setHasConditions(false);
                locations.save();
                ActiveAndroid.setTransactionSuccessful();
                ActiveAndroid.endTransaction();

            }
            EventBus.getDefault().post(new EventOnGetLocations());
        } catch (Exception e) {
            Logger.e("JsonParser : Error on EventOnGetLocations() " + e.getMessage());
            EventBus.getDefault().post(new EventOnGetLocations());
        }

    }

    public static void addScoreResponse(String json) {
        Logger.d("JsonParser : addScoreResponse json is  " + json);
        if (!json.equals("") && json != null) {
            AddScoreResponse[] addScoreResponses = gson.fromJson(json, AddScoreResponse[].class);
            EventBus.getDefault().post(new EventOnAddScoreResponse(addScoreResponses[0]));
        }
//        1 sabt
//        0 ghbl
//       -1 tarikh
    }
}
