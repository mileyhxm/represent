package com.example.mileyhu.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;




public class Results extends AppCompatActivity {
    private RequestQueue districtQueue;
    JSONArray districts = null;
    TextView district;
    //TODO: DISPLAY ADDRESS/DISTRICT AT TOP (district displayed)
    int numOfPeople;
    ArrayList<TextView> names;
    ArrayList<TextView> parties;
    ArrayList<TextView> types;
    ArrayList<Button> buttons;
    ArrayList<String> urls = new ArrayList<>();
    ArrayList<String> addresses = new ArrayList<>();
    ArrayList<String> contacts = new ArrayList<>();

    ArrayList<String> legNames = new ArrayList<>();
    ArrayList<String> legParties = new ArrayList<>();
    ArrayList<String> legTypes = new ArrayList<>();

    public static String name;
    public static String party;
    public static String type;
    public static String url;
    public static String address;
    public static String contact;
    public static boolean isRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_result);
        numOfPeople = 2;
        district = findViewById(R.id.district);
        district.setVisibility(View.INVISIBLE);
        final TextView loading = findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);

        final LinearLayout legislator1 = findViewById(R.id.legislator1);
        final LinearLayout legislator2 = findViewById(R.id.legislator2);
        final LinearLayout legislator3 = findViewById(R.id.legislator3);
        final LinearLayout legislator4 = findViewById(R.id.legislator4);
        final LinearLayout legislator5 = findViewById(R.id.legislator5);
        final LinearLayout legislator6 = findViewById(R.id.legislator6);

        final TextView name1 = findViewById(R.id.name1);
        final TextView party1 = findViewById(R.id.party1);
        final TextView type1 = findViewById(R.id.type1);

        final TextView name2 = findViewById(R.id.name2);
        final TextView party2 = findViewById(R.id.party2);
        final TextView type2 = findViewById(R.id.type2);

        final TextView name3 = findViewById(R.id.name3);
        final TextView party3 = findViewById(R.id.party3);
        final TextView type3 = findViewById(R.id.type3);

        final TextView name4 = findViewById(R.id.name4);
        final TextView party4 = findViewById(R.id.party4);
        final TextView type4 = findViewById(R.id.type4);

        final TextView name5 = findViewById(R.id.name5);
        final TextView party5 = findViewById(R.id.party5);
        final TextView type5 = findViewById(R.id.type5);

        final TextView name6 = findViewById(R.id.name6);
        final TextView party6 = findViewById(R.id.party6);
        final TextView type6 = findViewById(R.id.type6);
        //TODO: HOW TO INSTANTIATE THEM ALL TOGETHER
        final ArrayList<LinearLayout> layouts = new ArrayList<>();
        layouts.add(legislator1);
        layouts.add(legislator2);
        layouts.add(legislator3);
        layouts.add(legislator4);
        layouts.add(legislator5);
        layouts.add(legislator6);

        names = new ArrayList<>();
        names.add(name1);
        names.add(name2);
        names.add(name3);
        names.add(name4);
        names.add(name5);
        names.add(name6);

        parties = new ArrayList<>();
        parties.add(party1);
        parties.add(party2);
        parties.add(party3);
        parties.add(party4);
        parties.add(party5);
        parties.add(party6);

        types = new ArrayList<>();
        types.add(type1);
        types.add(type2);
        types.add(type3);
        types.add(type4);
        types.add(type5);
        types.add(type6);

        final Button detail1 = findViewById(R.id.detail1);
        final Button detail2 = findViewById(R.id.detail2);
        final Button detail3 = findViewById(R.id.detail3);
        final Button detail4 = findViewById(R.id.detail4);
        final Button detail5 = findViewById(R.id.detail5);
        final Button detail6 = findViewById(R.id.detail6);
        buttons = new ArrayList<>();
        buttons.add(detail1);
        buttons.add(detail2);
        buttons.add(detail3);
        buttons.add(detail4);
        buttons.add(detail5);
        buttons.add(detail6);

        for (LinearLayout l : layouts) {
            l.setVisibility(View.INVISIBLE);
        }

        View.OnClickListener detailOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*setContentView(R.layout.local_result);*/
                int index = buttons.indexOf(v);
                launchDetail(index);
            }
        };

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setOnClickListener(detailOnClickListener);
        }

        districtQueue = Volley.newRequestQueue(this);

        final String url;
        if (!isRandom) {
            url = "https://api.geocod.io/v1.3/" + Locate.input + "&fields=cd&api_key=bb4bbbb4ada8b660b3b55dba4856171bbbbb41d";
        } else {
            url = "https://api.geocod.io/v1.3/" + Landing.input + "&fields=cd&api_key=bb4bbbb4ada8b660b3b55dba4856171bbbbb41d";
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            boolean multipleDistricts = false;
                            /*for (int i = 0; i < results.length(); i++ ) {*/
                                JSONObject main = results.getJSONObject(0);
                                JSONObject field = main.getJSONObject("fields");
                                districts = field.getJSONArray("congressional_districts");

                                for (int j = 0; j < districts.length(); j++) {
                                    JSONObject d = districts.getJSONObject(j);
                                    String dName = d.getString("district_number");
                                    district.setText(district.getText().toString() + " " + dName);
                                    district.setVisibility(View.VISIBLE);
                                    JSONArray legislators = d.getJSONArray("current_legislators");

                                    multipleDistricts = (districts.length() > 1) && j > 0;

                                    for (int k = 0; k < legislators.length(); k++) {
                                        JSONObject l = legislators.getJSONObject(k);
                                        String type = l.getString("type");
                                        JSONObject bio = l.getJSONObject("bio");
                                        JSONObject contact = l.getJSONObject("contact");
                                        String last_name = bio.getString("last_name");
                                        String first_name = bio.getString("first_name");
                                        String party = bio.getString("party");
                                        String website = contact.getString("url");
                                        String address = contact.getString("address");
                                        String contact_form = contact.getString("contact_form");
                                        if (!multipleDistricts || !type.equals("senator")) {
                                            urls.add(website);
                                            addresses.add(address);
                                            legNames.add(first_name + " " + last_name);
                                            legParties.add(party);
                                            legTypes.add(type);

                                            if (!contact_form.equals("")) {
                                                contacts.add(contact_form);
                                            } else {
                                                contacts.add("Helloo");
                                            }
                                        }
                                        //TODO: ADD MORE INFO TO DETAIL PAGE
                                        /*if (k < names.size()) {
                                            names.get(k).setText(first_name + " " + last_name);
                                        }
                                        if (k < parties.size()) {
                                            TextView p = parties.get(k);
                                            p.setText(party);
                                            if (party.equals("Democrat")) {
                                                p.setTextColor(
                                                        getResources().getColor(R.color.democrats));
                                            } else if (party.equals("Republican")) {
                                                p.setTextColor(
                                                        getResources().getColor(R.color.republican));
                                            }

                                        }
                                        if (k < types.size()) {
                                            types.get(k).setText(type);
                                        } */
                                        if (type.equals("representative")) {
                                            numOfPeople++;
                                        }
                                    }
                                }


                            for (int i = 0; i < numOfPeople; i++) {
                                /*if (i < layouts.size()) { */
                                    names.get(i).setText(legNames.get(i));

                                    String p = legParties.get(i);
                                    parties.get(i).setText(p);
                                    if (p.equals("Democrat")) {
                                        parties.get(i).setTextColor(
                                                getResources().getColor(R.color.democrats));
                                    } else if (p.equals("Republican")) {
                                        parties.get(i).setTextColor(
                                                getResources().getColor(R.color.republican));
                                    } else {
                                        parties.get(i).setTextColor(
                                                getResources().getColor(R.color.colorPrimary));
                                    }

                                    types.get(i).setText(legTypes.get(i));
                                    layouts.get(i).setVisibility(View.VISIBLE);
                                    loading.setVisibility(View.INVISIBLE);
                                /*} else {
                                    name1.setText(String.valueOf(numOfPeople) + "and"
                                            + String.valueOf(layouts.size()));

                                    // TODO: HOW to accommodate more than 3 representatives
                                } */
                            }
                        } catch (JSONException E) {
                            loading.setText("Invalid zip code!");
                            loading.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setText("Invalid zip code");
                        loading.setVisibility(View.VISIBLE);
                    }
                });

        districtQueue.add(jsonObjectRequest);

    }

    private void launchDetail(int index) {
        Intent intent = new Intent(this, Details.class);
        name = names.get(index).getText().toString();
        party = parties.get(index).getText().toString();
        type = types.get(index).getText().toString();
        url = urls.get(index);
        address = addresses.get(index);
        contact = contacts.get(index);
        intent.putExtra("name", name);
        intent.putExtra("party", party);
        intent.putExtra("type", type);
        intent.putExtra("url", url);
        intent.putExtra("address", address);
        intent.putExtra("contact_form", contact);
        startActivity(intent);
    }

}

