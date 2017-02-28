package ir.aspacrm.my.app.mahan.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Microsoft on 4/3/2016.
 */
@Table(name = "Connection")
public class Connection extends Model {
    @Column(name = "UserId")
    public long userId;
    @Column(name = "StartTime")
    public String startTime = "";
    @Column(name = "EndTime")
    public String endTime = "";
    @Column(name = "Duration")
    public String duration = "";
    @Column(name = "Send")
    public int send;
    @Column(name = "Recieve")
    public int recieve;
    @Column(name = "TrafficUsed")
    public String trafficUsed = "";
}
