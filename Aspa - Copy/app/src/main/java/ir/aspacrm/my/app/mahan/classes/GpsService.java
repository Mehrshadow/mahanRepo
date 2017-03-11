package ir.aspacrm.my.app.mahan.classes;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.activeandroid.query.Update;

import java.util.List;

import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.model.Locations;


/**
 * Created by HaMiD on 6/16/2016.
 */
public class GpsService extends Service implements LocationListener {
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    public boolean isRunning = false;

    GpsParams[] gpsParams;
    boolean gpsProvider = false;
    Location location;

    double latitude = 0;               // latitude
    double longitude = 0;               // longitude

    double lastLatitude = 0;               // last-latitude
    double lastLongitude = 0;               // last-longitude

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;              // 30 meters
    private static final long MIN_TIME_BW_UPDATES = 10 * 1000;   // 1 minute


    private LocationManager locationManager;
    String URL = "";
    static final double _eQuatorialEarthRadius = 6378.1370D;
    static final double _d2r = (Math.PI / 180D);
    int distance;
    DialogClass dialogClass;

    @Override
    public void onCreate() {
        super.onCreate();
        dialogClass = new DialogClass();
        G.updateLocations();
        try {
            gpsParams = new GpsParams[G.locations.size()];
            for (int i = 0; i < G.locations.size(); i++) {
                GpsParams gpsParam = new GpsParams();
                gpsParam.longitude = G.locations.get(i).getLongitude();
                gpsParam.latitude = G.locations.get(i).getLatitude();
                gpsParams[i] = gpsParam;
            }
        } catch (Exception e) {
            e.printStackTrace();
            getLocation();
        }
        getLocation();


    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) GpsService.this.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            if (!isGPSEnabled && !isNetworkEnabled) {
                if (!G.isFirstCheckGps) {
                    dialogClass.enableGps();
                    G.isFirstCheckGps = true;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            } else {
                this.canGetLocation = true;
                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Logger.d("GPSService : GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        Logger.d("GPSService :  Location in GPS " + location);
                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
//                                G.toast("Location in GPS " + location);
                                gpsProvider = true;
                            }
                        });
                    }
                }
                if (isNetworkEnabled) {
                    Logger.d("GPSService :  IF is Network Enabled");
                    if (location == null) {
                        Logger.d("GPSService :  IF Network location is null ");
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Logger.d("GPSService : Network");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            Handler handler = new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
//                                    G.toast("Location in Network " + location);
                                    gpsProvider = true;
                                }
                            });
                        }
                    } else
                        gpsProvider = true;
                }
                updateGPSCoordinates(location);
            }
        } catch (Exception e) {
            Logger.d("GPSService : Impossible to connect to LocationManager : " + e);
        }
        return location;
    }

    public void updateGPSCoordinates(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            if (lastLatitude == 0 && lastLongitude == 0) {
                lastLatitude = latitude;
                lastLongitude = longitude;
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void checkLocation(double sLat, double sLon) {
        DialogClass showMessage = new DialogClass();
        try {
            G.updateLocations();
            for (int i = 0; i < G.locations.size(); i++) {
                distance = getDistanceInM(sLat, sLon, G.locations.get(i).getLatitude(), G.locations.get(i).getLongitude());
                if (distance < 100) {


                    if (!G.locations.get(i).isHasConditions()) {
                        showMessage.showMessageDialog("امتیاز جدید", "امتیاز مربوط به رویداد " + G.locations.get(i).getDes() + " به شما تعلق گرفت");
                    }

                    new Update(Locations.class)
                            .set("hasConditions = ?", 1)
                            .where("id = ?", G.locations.get(i).getId())
                            .execute();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendLocations() {
        if (G.locations != null && G.locations.size() != 0) {
            for (int i = 0; i < G.locations.size(); i++) {
                if (G.locations.get(i).isHasConditions()) {
                    WebService.addLocationScore(G.locations.get(i));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

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


    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getApplicationContext(), "Latitude>>" + location.getLatitude() + "\nLongitude>>" + location.getLongitude() + "\n<<Sent To>>\n" + URL, Toast.LENGTH_LONG).show();
        checkLocation(location.getLatitude(), location.getLongitude());
        sendLocations();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

        Logger.d("GPSService : onProviderEnabled");
        Logger.d("onProviderEnabled");
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled)
            gpsProvider = true;

    }

    @Override
    public void onProviderDisabled(String provider) {

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isNetworkEnabled) {
            locationManager.removeUpdates(GpsService.this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            try {
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        gpsProvider = false;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (isGPSEnabled) {
            locationManager.removeUpdates(GpsService.this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            Logger.d("GPSService : Change Provider To GPS...");

            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    gpsProvider = true;
                }
            });
        } else if (!isGPSEnabled && !isNetworkEnabled) {
            locationManager.removeUpdates(GpsService.this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//            Logger.d("GPSService : two provider disable and force provider to Mobile Network");

            gpsProvider = true;
        }

    }

    private class GpsParams {
        public double latitude;
        public double longitude;
        public double distance;
    }
}