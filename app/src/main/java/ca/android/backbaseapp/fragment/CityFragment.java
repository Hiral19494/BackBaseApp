package ca.android.backbaseapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ca.android.backbaseapp.R;
import ca.android.backbaseapp.adapter.CityRecyclerViewAdapter;
import ca.android.backbaseapp.model.City;

public class CityFragment extends Fragment {

    private static final String TAG = CityFragment.class.getSimpleName();

    RecyclerView rvCity;
    EditText edtSearch;
    private ArrayList<City> cityArrayList;
    private CityRecyclerViewAdapter cityAdapter;
    private OnListFragmentInteractionListener mListener;

    public CityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_list, container, false);

        rvCity = view.findViewById(R.id.rv_city);
        edtSearch = view.findViewById(R.id.edt_search);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable query) {
          //      Log.i(TAG, "query = " + query);
                if (cityAdapter != null) {
                    cityAdapter.getFilter().filter(query);
                }
            }
        });

        cityArrayList = new ArrayList<>();
        pareCityJson(loadJSONFromAsset());
        sortCity();
        rvCity.setLayoutManager(new LinearLayoutManager(getActivity()));
        cityAdapter = new CityRecyclerViewAdapter(cityArrayList, mListener);
        rvCity.setAdapter(cityAdapter);
        return view;
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void pareCityJson(String cityJson) {
        try {
            JSONArray cityJArray = new JSONArray(cityJson);
            for (int i = 0; i < cityJArray.length(); i++) {
                cityArrayList.add(new City(cityJArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sortCity() {
        Collections.sort(cityArrayList, new Comparator<City>() {
            @Override
            public int compare(City c1, City c2) {
                String cityName1 = c1.getCityName();
                String cityName2 = c2.getCityName();
                int sComp = cityName1.compareTo(cityName2);

                if (sComp != 0) {
                    return sComp;
                }

                String countryName1 = c1.getCountryName();
                String countryName2 = c2.getCountryName();
                return countryName1.compareTo(countryName2);
            }
        });

        for (City city : cityArrayList) {
           // Log.i(TAG, city.getCityName() + ", " + city.getCountryName());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(City city);
    }
}
