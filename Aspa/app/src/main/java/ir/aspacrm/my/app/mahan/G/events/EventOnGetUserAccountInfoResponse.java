package ir.aspacrm.my.app.mahan.G.events;


import ir.aspacrm.my.app.mahan.G.gson.AccountInfoResponse;

public class EventOnGetUserAccountInfoResponse {
    AccountInfoResponse accountInfo;
    public EventOnGetUserAccountInfoResponse(AccountInfoResponse accountInfo) {
        this.accountInfo = accountInfo;
    }
    public AccountInfoResponse getAccountInfo() {
        return accountInfo;
    }
}
