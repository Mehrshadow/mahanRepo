package ir.aspacrm.my.app.mahan.G.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "TicketDetail")
public class TicketDetail extends Model {
    @Column(name = "userId")
    public long userId = 0;
    @Column(name = "ParentCode")
    public long parentCode = 0;
    @Column(name = "Date")
    public String date = "";
    @Column(name = "Unit")
    public String unit = "";
    @Column(name = "User")
    public String user = "";
    @Column(name = "State")
    public String state = "";
    @Column(name = "Text")
    public String text = "";
}
