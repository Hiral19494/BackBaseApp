package ca.android.backbaseapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import ca.android.backbaseapp.Fragment.CityFragment;
import ca.android.backbaseapp.R;
import ca.android.backbaseapp.model.City;

public class CityRecyclerViewAdapter extends RecyclerView.Adapter<CityRecyclerViewAdapter.ViewHolder> implements Filterable {

    private ArrayList<City> cityArrayList;
    private ArrayList<City> cityArrayListFiltered;
    private CityFragment.OnListFragmentInteractionListener mListener;

    public CityRecyclerViewAdapter(ArrayList<City> cityArrayList, CityFragment.OnListFragmentInteractionListener listener) {
        this.cityArrayList = cityArrayList;
        this.cityArrayListFiltered = cityArrayList;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.city = cityArrayListFiltered.get(position);
        holder.txtCityName.setText(cityArrayListFiltered.get(position).getCityName() + ", ");
        holder.txtCountryName.setText(cityArrayListFiltered.get(position).getCountryName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.city);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityArrayListFiltered != null ? cityArrayListFiltered.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    cityArrayListFiltered = cityArrayList;
                } else {
                    ArrayList<City> filteredList = new ArrayList<>();
                    for (City city : cityArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (city.getCityName().toLowerCase().startsWith(charString.toLowerCase())) {
                            filteredList.add(city);
                        }
                    }

                    cityArrayListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = cityArrayListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                cityArrayListFiltered = (ArrayList<City>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtCityName;
        public final TextView txtCountryName;
        public City city;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtCityName = (TextView) view.findViewById(R.id.txt_city_name);
            txtCountryName = (TextView) view.findViewById(R.id.txt_country_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtCountryName.getText() + "'";
        }
    }
}

