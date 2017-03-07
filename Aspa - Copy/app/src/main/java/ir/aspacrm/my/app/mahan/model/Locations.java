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

    @Column(name = "Des")
    String Des;

    @Column(name = "startDate")
    String startDate;

    @Column(name = "endDate")
    String endDate;

    @Column(name = "scoreTypeCode")
    int scoreTypeCode;

    public int getScoreTypeCode() {
        return scoreTypeCode;
    }

    public void setScoreTypeCode(int scoreTypeCode) {
        this.scoreTypeCode = scoreTypeCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }

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