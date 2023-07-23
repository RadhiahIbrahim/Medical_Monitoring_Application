package com.example.medicalmonitoringapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.medicalmonitoringapplication.databinding.ActivityMapBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Vector;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap mMap;
    private ActivityMapBinding binding;

    MarkerOptions markerOptions1;
    LatLng centerLocation;

    Vector<MarkerOptions> markerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getCurrentLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

        centerLocation = new LatLng(2.1896, 102.2501);

        markerOptions = new Vector<>();

        markerOptions.add(new MarkerOptions().title("Melaka Hospital")
                .position(new LatLng(2.2172, 102.2613))
                .snippet("Open 24 Hours")
        );

        markerOptions.add(new MarkerOptions().title("Pantai Hospital Ayer Keroh")
                .position(new LatLng(2.2377, 102.2873))
                .snippet("Open 24 Hours")
        );

        markerOptions.add(new MarkerOptions().title("Putra Specialist Hospital")
                .position(new LatLng(2.2022, 102.2525))
                .snippet("Open 24 Hours")
        );
        markerOptions.add(new MarkerOptions().title("Mahkota Medical Centre")
                .position(new LatLng(2.1877, 102.2516))
                .snippet("Open 24 Hours")
        );
        markerOptions.add(new MarkerOptions().title("Oriental Melaka Straits Medical Centre")
                .position(new LatLng(2.2079, 102.2140))
                .snippet("Open 24 Hours")
        );
        markerOptions.add(new MarkerOptions().title("The Southern Hospital Sdn. Bhd.")
                .position(new LatLng(2.2000, 102.2512))
                .snippet("Open 24 Hours")
        );
        markerOptions.add(new MarkerOptions().title("Klinik Pakar Perubatan (MUFUC) Hospital Besar Melaka")
                .position(new LatLng(2.2183, 102.2601))
                .snippet("Open on Weekdays 8am-5pm")
        );
        markerOptions.add(new MarkerOptions().title("Jasin Hospital")
                .position(new LatLng(2.3048, 102.4296))
                .snippet("Open 24 Hours")
        );
        markerOptions.add(new MarkerOptions().title("Alor Gajah Hospital")
                .position(new LatLng(2.3954, 102.2020))
                .snippet("Open 24 Hours")
        );
        markerOptions.add(new MarkerOptions().title("Melaka State Health Department")
                .position(new LatLng(2.2735, 102.2887))
                .snippet("Open 8am-5pm")
        );

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        for (MarkerOptions mark : markerOptions) {
            mMap.addMarker(mark);
        }

        enableMyLocation();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerLocation, 8));
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            String perms[] = {"android.permission.ACCESS_FINE_LOCATION"};
            ActivityCompat.requestPermissions(this, perms, 200);
        }
    }

    public void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            String perms[] = {"android.permission.ACCESS_FINE_LOCATION"};
            ActivityCompat.requestPermissions(this, perms, 200);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Location is available, move the camera and add the marker
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions markerOptions1 = new MarkerOptions().position(latLng).title("Current Location!");
                    mMap.addMarker(markerOptions1);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                } else {
                    // Handle the case when the location is null or unavailable
                    Toast.makeText(Map.this, "Please enable location permissions", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}