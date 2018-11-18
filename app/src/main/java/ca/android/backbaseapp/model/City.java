package ca.android.backbaseapp.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class City implements Serializable {
    private String id;
    private String cityName;
    private String countryName;
    private double latitude;
    private double longitude;

    public City(JSONObject cityJObject) {
        try {
            id = cityJObject.optString("_id");
            cityName = cityJObject.optString("name");
            countryName = cityJObject.optString("country");
            if (cityJObject.has("coord")) {
                latitude = cityJObject.getJSONObject("coord").getDouble("lat");
                longitude = cityJObject.getJSONObject("coord").getDouble("lon");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
