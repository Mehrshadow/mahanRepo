package ir.aspacrm.my.app.mahan.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Microsoft on 3/7/2016.
 */

@Table(name = "Account")
public class Account extends Model{
    //اطلاعات اکانت مشترک
    @Column(name = "userId")
    public long userId;
    @Column(name = "rTraffic")
    public int rTraffic;
    @Column(name = "rDay")
    public int rDay;
    @Column(name = "rHour")
    public double rHour;
    @Column(name = "tPerc")
    public int tPerc;
    @Column(name = "dPerc")
    public int dPerc;
    @Column(name = "hPerc")
    public int hPerc;
    @Column(name = "grpId")
    public long grpId;
    @Column(name = "grpName")
    public String grpName = "";
    @Column(name = "pkgName")
    public String pkgName = "";
    @Column(name = "lastNewsID")
    public int lastNewsID;
    @Column(name = "lastTicketID")
    public int lastTicketID;
    @Column(name = "lastNotifyID")
    public int lastNotifyID;
    @Column(name = "online")
    public boolean online;
    @Column(name = "balance")
    public int balance;
    @Column(name = "expDate")
    public String expDate = "";
    @Column(name = "farsiExpDate")
    public String farsiExpDate = "";
    @Column(name = "regConnect")
    public boolean regConnect;

}
