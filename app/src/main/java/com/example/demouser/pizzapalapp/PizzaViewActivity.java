package com.example.demouser.pizzapalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class PizzaViewActivity extends AppCompatActivity {

    private TextView location;
    private TextView vendor;
    private TextView toppings;
    private CheckBox vegan;
    private CheckBox vegetarian;

    private CheckBox gf;

    private CheckBox kosher;
    private CheckBox glutenFree;
    private Button backButton;
    private Button doneButton;
    private DatabaseReference mFirebaseDatabase;
    private Pizza pizza;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_view);

        Intent intent = getIntent();
        String loc = intent.getStringExtra(Pizza.getBuilding()) + " " + intent.getStringExtra(Pizza.getRoom());


        //get it from firebase
        mFirebaseDatabase.child("pizza").child(pizza.getId()).setValue(game);
        //.child pizza

        location = (TextView) findViewById(R.id.edRoomNumText);
        //location.setText(loc);
        vendor = (TextView) findViewById(R.id.edVendorText);
        toppings = (TextView) findViewById(R.id.toppingsEd);
        vegan = (CheckBox) findViewById(R.id.veganBox);
        vegetarian = (CheckBox) findViewById(R.id.vegBox);
        kosher = (CheckBox) findViewById(R.id.kosherBox);
        glutenFree = (CheckBox) findViewById(R.id.gfBox);
        backButton = (Button) findViewById(R.id.backButton);
        doneButton = (Button) findViewById(R.id.doneButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to MainActivity
                finish();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: delete pizza
                //go back to MainActivity
                finish();
            }
        });

    }


}
