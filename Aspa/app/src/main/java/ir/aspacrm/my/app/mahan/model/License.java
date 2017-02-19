package ir.aspacrm.my.app.mahan.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Microsoft on 3/7/2016.
 */
@Table(name = "License")
public class License extends Model {
    //لایسنس برای مشترکی که لاگین کرده است
    @Column(name = "userId")
    public long userId;
    @Column(name = "chargeOnline")
    public boolean chargeOnline;
    @Column(name = "payOnline")
    public boolean payOnline;
    @Column(name = "ticket")
    public boolean ticket;
    @Column(name = "changePass")
    public boolean changePass;
    @Column(name = "adv")
    public boolean adv;
    @Column(name = "club")
    public boolean club;
}
