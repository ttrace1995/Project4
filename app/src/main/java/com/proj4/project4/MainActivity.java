package com.proj4.project4;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    public static String stringURL = "http://www.tetonsoftware.com/bikes/bikes.json";
    ListAdapter customAdapter;
    ArrayList<BikeData> bikeArray;
    ListView list;
    Toolbar toolbar;
    Spinner spinner;
    Dialog dialog = null;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.spinner);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        list = (ListView) findViewById(R.id.lv);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                BikeData currentBike = bikeArray.get(position);
                showPopup(currentBike);
            }
        });

        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                url = pref.getString("URLPref", "");
                Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();
                String jsonURL = url + "bikes.json";
                stringURL = jsonURL;
                getURL();
            }
        };
        pref.registerOnSharedPreferenceChangeListener(listener);

        getURL();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent myIntent = new Intent(this, SettingsFragment.class);
                startActivity(myIntent);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void getURL() {
        JSONTask task = new JSONTask(new AsyncResponse() {
            @Override
            public void processFinish(ArrayList<BikeData> output) {
            bikeArray = output;
            setAdapter();
            }
        });
        task.execute(stringURL);
    }

    public void setAdapter() {
        customAdapter = new ListAdapter(this, R.layout.listview_row_layout, bikeArray);
        list.setAdapter(customAdapter);
        setupSimpleSpinner();
    }

    private void setupSimpleSpinner() {
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.sortable_fields,R.layout.spinner_item);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public static final int SELECTED_ITEM = 0;
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long rowid) {
                if (arg0.getChildAt(SELECTED_ITEM) != null) {
                    ((TextView) arg0.getChildAt(SELECTED_ITEM)).setTextColor(Color.WHITE);
                    String text = spinner.getSelectedItem().toString();
                    Collections.sort(bikeArray, new MyComparator(text));
                    adapter.notifyDataSetChanged();
                    customAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void showPopup(BikeData bike) {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup);
        dialog.setTitle(R.string.dialogTitle);

        TextView company = (TextView) dialog.findViewById(R.id.company);
        String companyStr = getString(R.string.company) + bike.getCompany();
        company.setText(companyStr);

        TextView model = (TextView) dialog.findViewById((R.id.model));
        String modelStr = getString(R.string.model) + bike.getModel();
        model.setText(modelStr);

        TextView price = (TextView) dialog.findViewById((R.id.price));
        String priceStr = getString(R.string.price) + String.valueOf(bike.getPrice());
        price.setText(priceStr);

        TextView location = (TextView) dialog.findViewById((R.id.location));
        String locationStr = getString(R.string.location) + bike.getLocation();
        location.setText(locationStr);

        TextView date = (TextView) dialog.findViewById((R.id.date));
        String dateStr = getString(R.string.datePosted) + bike.getDate();
        date.setText(dateStr);

        TextView description = (TextView) dialog.findViewById((R.id.description));
        String descStr = getString(R.string.desc) + bike.getDescription();
        description.setText(descStr);

        TextView link = (TextView) dialog.findViewById((R.id.link));
        String linkStr = getString(R.string.link) + bike.getLink();
        link.setText(linkStr);

        dialog.show();
    }

    public void dismissIt(View v) {
        dialog.dismiss();
    }

    public void refresh(View v) {
        Toast.makeText(this, "Resetting...", Toast.LENGTH_SHORT).show();
        bikeArray = null;
        customAdapter.notifyDataSetChanged();
        getURL();
    }


}

class MyComparator implements Comparator<BikeData> {
    static final int COMPANY = 0, PRICE = 1, LOCATION = 2;
    int type;

    public MyComparator(String type) {
        if(type.equalsIgnoreCase("company")) {
            this.type = COMPANY;
        } else if(type.equalsIgnoreCase("price")) {
            this.type = PRICE;
        } else if(type.equalsIgnoreCase("location")) {
            this.type = LOCATION;
        }
    }

    @Override
    public int compare(BikeData bikeData, BikeData t1) {
        if (type == COMPANY) {
            return bikeData.getCompany().compareTo(t1.getCompany());
        } else if (type == PRICE) {
            if (bikeData.getPrice() > t1.getPrice())
                return 1;
            else if (bikeData.getPrice() == t1.getPrice())
                return 0;
            else
                return -1;
        } else if (type == LOCATION) {
            return bikeData.getLocation().compareTo(t1.getLocation());
        }
        return 0;
    }
}
