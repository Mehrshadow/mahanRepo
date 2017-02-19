package ir.aspacrm.my.app.mahan.G.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Microsoft on 3/27/2016.
 */
@Table(name = "Notify")
public class Notify extends Model {
    @Column(name = "UserId")
    public long userId;
    @Column(name = "NotifyCode")
    public long notifyCode;
    @Column(name = "Message")
    public String message = "";
    @Column(name = "IsSeen")
    public boolean isSeen = false;
}
