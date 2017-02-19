package ir.aspacrm.my.app.mahan.G.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "User")
public class User extends Model {
    @Column(name = "userId")
    public long userId;
    @Column(name = "exKey")
    public String exKey = "";
    @Column(name = "isLogin")
    public boolean isLogin = false;
    @Column(name = "ispUrl")
    public String ispUrl = "";
    @Column(name = "ispId")
    public long ispId;
    @Column(name = "isLastLogin")
    public boolean isLastLogin = false;
}
