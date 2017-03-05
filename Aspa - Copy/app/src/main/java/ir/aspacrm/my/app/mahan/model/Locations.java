package ir.aspacrm.my.app.mahan.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by HaMiD on 3/4/2017.
 */
@Table(name = "Locations ")
public class Locations extends Model {

    @Column(name = "Latitude")
    double Latitude;


    @Column(name = "Longitude")
    double Longitude;

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
