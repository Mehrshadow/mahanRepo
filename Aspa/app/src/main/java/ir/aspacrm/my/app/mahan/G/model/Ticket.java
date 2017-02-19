package ir.aspacrm.my.app.mahan.G.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import ir.aspacrm.my.app.mahan.G.G.enums.EnumTicketPriority;

/**
 * Created by Microsoft on 3/26/2016.
 */
@Table(name = "Ticket")
public class Ticket extends Model {
    @Column(name = "userId")
    public long userId = 0;
    @Column(name = "Code")
    public long code;
    @Column(name = "Date")
    public String date = "";
    @Column(name = "Title")
    public String title = "" ;
    @Column(name = "State")
    public String state = "";
    @Column(name = "Priority")
    public String priority = EnumTicketPriority.KAM;
    @Column(name = "Open")
    public int open = 0;
}
