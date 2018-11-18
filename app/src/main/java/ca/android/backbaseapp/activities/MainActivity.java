package ca.android.backbaseapp.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ca.android.backbaseapp.Fragment.CityFragment;
import ca.android.backbaseapp.R;
import ca.android.backbaseapp.model.City;

public class MainActivity extends AppCompatActivity implements CityFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment(new CityFragment());
    }

    public void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            finish();
        }
    }

    @Override
    public void onListFragmentInteraction(City city) {
        Toast.makeText(getBaseContext(),city.getCityName(),Toast.LENGTH_LONG).show();
    }
}
