package ca.android.backbaseapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ca.android.backbaseapp.R;
import ca.android.backbaseapp.model.City;

public class MapFragment extends Fragment  {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = view.findViewById(R.id.map_view);
        if (getArguments() != null) {
            mCity = (City) getArguments().getSerializable(ARG_CITY);
            Log.i(TAG, "mCity.getCityName() = " + mCity.getLatitude());
        }

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(mCity.getLatitude(), mCity.getLongitude()))
                                .title("LinkedIn")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));


                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCity.getLatitude(), mCity.getLongitude()), 20));
                    }
                });

        return view;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


}
