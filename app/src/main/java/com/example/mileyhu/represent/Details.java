package com.example.mileyhu.represent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;



public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        TextView name = findViewById(R.id.name);
        TextView party = findViewById(R.id.party);
        TextView type = findViewById(R.id.type);
        TextView address = findViewById(R.id.address);
        Button website = findViewById(R.id.website);
        Button contact = findViewById(R.id.contact_form);

        name.setText(Results.name);
        party.setText(Results.party);
        if (Results.party.equals("Democrat")) {
            party.setTextColor(
                    getResources().getColor(R.color.democrats));
        } else if (Results.party.equals("Republican")) {
            party.setTextColor(
                    getResources().getColor(R.color.republican));
        } else {
            party.setTextColor(
                    getResources().getColor(R.color.colorPrimary));
        }
        type.setText(Results.type);
        address.setText(Results.address);
        /*website.setText(Results.url);*/

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl(Results.url);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Results.contact.equals("null")) {
                    goToUrl(Results.contact);
                } else {
                    goToUrl(Results.url);
                }
            }
        });
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
