package com.example.naman.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {


    LocationManager locationManager;
    String provider;
    TextView latTV;
    TextView lngTV;
    TextView accuracyTV;
    TextView speedTV;
    TextView bearingTV;
    TextView altitudeTV;
    TextView addressTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(),"Please wait for few seconds!!",Toast.LENGTH_LONG).show();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
       //  provider = locationManager.getBestProvider(new Criteria(), false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 1, this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
    //   Location location = locationManager.getLastKnownLocation(provider);
      //  onLocationChanged(location);

        latTV=(TextView)findViewById(R.id.lat);
        lngTV=(TextView)findViewById(R.id.lng);
        accuracyTV=(TextView)findViewById(R.id.accuracy);
        speedTV=(TextView)findViewById(R.id.speed);
        bearingTV=(TextView)findViewById(R.id.bearing);
        altitudeTV=(TextView)findViewById(R.id.altitude);
        addressTV=(TextView)findViewById(R.id.address);
    }

    @Override
    public void onLocationChanged(Location location) {

        Double lat = location.getLatitude();
        Double lng = location.getLongitude();
        Double alt=location.getAltitude();
        Float bearing = location.getBearing();
        Float speed=location.getSpeed();
        Float accuracy=location.getAccuracy();
        Geocoder geocoder=new Geocoder(getApplicationContext(),Locale.getDefault());

       try {
            List<Address> listAddresses=geocoder.getFromLocation(lat,lng,1);

            if(listAddresses!=null && listAddresses.size()>0){

                Log.i("PlaceInfo: ",listAddresses.get(0).toString());

                String addressholder="";

                for(int i=0;i<=listAddresses.get(0).getMaxAddressLineIndex();i++){

                    addressholder+=listAddresses.get(0).getAddressLine(i)+"\n";

                }
                addressTV.setText("Address:\n"+addressholder);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        latTV.setText("Latitude: "+lat.toString());
        lngTV.setText("Longitude: "+lng.toString());
        altitudeTV.setText("Altitude: "+alt.toString());
        bearingTV.setText("Bearing: "+bearing.toString());
        speedTV.setText("Speed: "+speed.toString());
        accuracyTV.setText("Accuracy: "+accuracy.toString());


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);
    }
}
