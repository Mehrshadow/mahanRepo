package ir.aspacrm.my.app.mahan.classes;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import ir.aspacrm.my.app.mahan.model.Locations;


/**
 * Created by HaMiD on 6/16/2016.
 */
public class GpsService extends Service {

    private LocationListener listener;
    private LocationManager locationManager;
    String URL = "";
    static final double _eQuatorialEarthRadius = 6378.1370D;
    static final double _d2r = (Math.PI / 180D);
    int distance;
    List<Locations> locationses;


    @Override
    public void onCreate() {

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                Intent i = new Intent("location_update");
//                i.putExtra("coordinates", "Latitude>>" + location.getLatitude() + "\nLongitude>>" + location.getLongitude() + "\n<<Sent To>>\n" + URL);
//                sendBroadcast(i);

                Toast.makeText(getApplicationContext(), "Latitude>>" + location.getLatitude() + "\nLongitude>>" + location.getLongitude() + "\n<<Sent To>>\n" + URL, Toast.LENGTH_LONG).show();
                checkLocation(location.getLatitude(), location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //noinspection MissingPermission
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0,listener);


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void checkLocation(double sLat, double sLon) {
        locationses = readLocationsFromDb();
        for (int i = 0; i < locationses.size(); i++) {
            distance = getDistanceInM(sLat, sLon, locationses.get(i).getLatitude(), locationses.get(i).getLongitude());
            if (distance < 500) {


                // do something


                new Delete()
                        .from(Locations.class)
                        .where("Latitude = ?", locationses.get(i).getLatitude())
                        .where("Longitude = ?", locationses.get(i).getLongitude())
                        .execute();
            }
        }
//         distance = getDistanceInM(sLat, sLon, latitude, longitude);
    }

    public static int getDistanceInM(double lat1, double long1, double lat2, double long2) {
        return (int) (1000D * getDistanceInKM(lat1, long1, lat2, long2));
    }

    public static double getDistanceInKM(double lat1, double long1, double lat2, double long2) {

        double dlong = (long2 - long1) * _d2r;
        double dlat = (lat2 - lat1) * _d2r;
        double a = Math.pow(Math.sin(dlat / 2D), 2D) + Math.cos(lat1 * _d2r) * Math.cos(lat2 * _d2r)
                * Math.pow(Math.sin(dlong / 2D), 2D);
        double c = 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
        double d = _eQuatorialEarthRadius * c;
        return d;
    }

    private List<Locations> readLocationsFromDb() {
        try {
            return new Select(new String[]{"Id,Latitude,Longitude"}).from(Locations.class).execute();
        } catch (Exception e) {
            e.printStackTrace();
            List<Locations> list = new ArrayList<>();
            return list;
        }

    }

}