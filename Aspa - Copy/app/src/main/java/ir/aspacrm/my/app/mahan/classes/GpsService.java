package ir.aspacrm.my.app.mahan.classes;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.*;

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
    List<Locations> locationses;


    @Override
    public void onCreate() {
        super.onCreate();

        try {
            locationses = readLocationsFromDb();
            gpsParams = new GpsParams[locationses.size()];

            for (int i = 0; i < locationses.size(); i++) {
                GpsParams gpsParam = new GpsParams();
                gpsParam.longitude = locationses.get(i).getLongitude();
                gpsParam.latitude = locationses.get(i).getLatitude();
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
        Locations locations7 = new Locations();
        locations7.setEndDate("2017-02-02T12:23:10");
        locations7.setStartDate("2017-02-01T13:23:10");
        locations7.setScoreTypeCode(-2);
        addScore(locations7);
        try {
            locationses = readLocationsFromDb();
            for (int i = 0; i < locationses.size(); i++) {
                distance = getDistanceInM(sLat, sLon, locationses.get(i).getLatitude(), locationses.get(i).getLongitude());
                if (distance < 500) {

                    // do something
                    addScore(locationses.get(i));

                    new Delete()
                            .from(Locations.class)
                            .where("Latitude = ?", locationses.get(i).getLatitude())
                            .where("Longitude = ?", locationses.get(i).getLongitude())
                            .execute();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addScore(Locations locations) {
        String startDate = locations.getStartDate();
        String endDate = locations.getEndDate();
        int type = locations.getScoreTypeCode();


        try {
            DateFormat currentDay = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            DateFormat currentHour = new SimpleDateFormat("hh:mm:ss ", Locale.ENGLISH);
            String nDay = currentDay.format(Calendar.getInstance().getTime());
            String nHour = currentHour.format(Calendar.getInstance().getTime());


            SimpleDateFormat Datee = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat houre = new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH);
            String[] startDay = startDate.split("T");
            String[] endDay = endDate.split("T");


            Date sDay = Datee.parse(startDay[0]);
            Date sHour = houre.parse(startDay[1]);

            Date eDay = Datee.parse(endDay[0]);
            Date eHour = houre.parse(endDay[1]);
            int campareDay = eDay.compareTo(sDay);
            int campareHour = eHour.compareTo(sHour);

            switch (campareDay) {
                case 0:

                    if (campareHour > 0)
                        WebService.sendAddScoreRequest(type);

                    break;
                case 1:
                    WebService.sendAddScoreRequest(type);
                    break;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

//
//        Date strDate = sdf.parse(valid_until);
//        if (new Date().after(strDate)) {
//            catalog_outdated = 1;
//        }

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

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getApplicationContext(), "Latitude>>" + location.getLatitude() + "\nLongitude>>" + location.getLongitude() + "\n<<Sent To>>\n" + URL, Toast.LENGTH_LONG).show();
        checkLocation(location.getLatitude(), location.getLongitude());
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