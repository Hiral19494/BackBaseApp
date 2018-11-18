package ca.android.backbaseapp.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ca.android.backbaseapp.R;
import ca.android.backbaseapp.model.City;

public class MapFragment extends Fragment {
    private static final String TAG = CityFragment.class.getSimpleName();
    private static final String ARG_CITY = "city";
    private City mCity;
    MapView mapView;
    public GoogleMap mGoogleMap;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(City city) {
        MapFragment mapFragment = new MapFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
        mapFragment.setArguments(args);
        return mapFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (City) getArguments().getSerializable(ARG_CITY);
            Log.i(TAG, "mCity.getCityName() = " + mCity.getCityName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = view.findViewById(R.id.map_view);
        //mCity = (City) getArguments().get(ARG_CITY);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mGoogleMap.setMyLocationEnabled(true);
                MapsInitializer.initialize(getActivity());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(mCity.getLatitude(), mCity.getLongitude()), 20);
                mGoogleMap.animateCamera(cameraUpdate);

                MarkerOptions optionOrigin = new MarkerOptions();
                LatLng latLngOrigin = new LatLng(mCity.getLatitude(), mCity.getLongitude());
                optionOrigin.position(latLngOrigin);
                optionOrigin.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                mGoogleMap.addMarker(optionOrigin);


            }
        });

        return view;
    }
}
