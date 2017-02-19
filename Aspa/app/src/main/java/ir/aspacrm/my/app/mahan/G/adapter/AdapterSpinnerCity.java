package ir.aspacrm.my.app.mahan.G.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ir.aspacrm.my.app.mahan.G.G;
import ir.aspacrm.my.app.mahan.G.R;
import ir.aspacrm.my.app.mahan.G.classes.Logger;
import ir.aspacrm.my.app.mahan.G.gson.CityResponse;

import java.util.ArrayList;

public class AdapterSpinnerCity extends ArrayAdapter<CityResponse> {

    ArrayList<CityResponse> cities;
    public AdapterSpinnerCity(ArrayList<CityResponse> cities) {
        super(G.context, R.layout.s_item_white,cities);
        this.cities = cities;
        Logger.d("AdapterSpinnerCity : cities size is " + cities.size());
    }
    @Override
    public int getCount() {
        return cities.size();
    }
    @Override
    public CityResponse getItem(int position) {
        return cities.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.s_item_white, parent, false);
        TextView txtUserSelect = (TextView) view.findViewById(R.id.txtName);
        if(position != 0){
            txtUserSelect.setText("شهر : " + cities.get(position).Name);
        }else
            txtUserSelect.setText(cities.get(position).Name);
        txtUserSelect.setGravity(Gravity.RIGHT);
        return view;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.s_item_black, parent, false);
        TextView txtChoice = (TextView) view.findViewById(R.id.txtName);
        txtChoice.setText(cities.get(position).Name);
        txtChoice.setGravity(Gravity.RIGHT);
        return view;
    }
    public void updateList(ArrayList<CityResponse> data){
        cities = data;
        notifyDataSetChanged();
    }
}
