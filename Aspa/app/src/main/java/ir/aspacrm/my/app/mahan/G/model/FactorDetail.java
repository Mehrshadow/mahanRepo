package ir.aspacrm.my.app.mahan.G.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name = "FactorDetail")
public class FactorDetail extends Model {
    @Column(name = "UserId")
    public long userId;
    @Column(name = "ParentId")
    public long parentId;
    @Column(name = "Name")
    public String name = "";
    @Column(name = "Des")
    public String des = "";
    @Column(name = "Price")
    public String price = "";
}
