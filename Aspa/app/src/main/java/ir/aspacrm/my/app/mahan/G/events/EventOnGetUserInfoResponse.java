package ir.aspacrm.my.app.mahan.G.events;


import ir.aspacrm.my.app.mahan.G.gson.UserInfoResponse;

/**
 * Created by Microsoft on 3/7/2016.
 */
public class EventOnGetUserInfoResponse {
    UserInfoResponse userInfo;
    public EventOnGetUserInfoResponse(UserInfoResponse userInfo) {
        this.userInfo = userInfo;
    }
    public UserInfoResponse getUserInfo() {
        return userInfo;
    }
}
