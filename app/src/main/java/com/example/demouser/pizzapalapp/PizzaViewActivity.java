package com.example.demouser.pizzapalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class PizzaViewActivity extends AppCompatActivity {

    private TextView location;
    private TextView vendor;
    private TextView toppings;
    private CheckBox vegan;
    private CheckBox vegetarian;
    private CheckBox gf;
    private CheckBox kosher;
    private CheckBox glutenFree;
    private Button doneButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_view);

        Intent intent = getIntent();
        //String loc = intent.getStringExtra(Pizza.getBuilding()) + " " + intent.getStringExtra(Pizza.getRoom());


        location = (TextView) findViewById(R.id.edRoomNumText);
        vendor = (TextView) findViewById(R.id.edVendorText);
        toppings = (TextView) findViewById(R.id.toppingsEd);
        vegan = (CheckBox) findViewById(R.id.veganBox);
        vegetarian = (CheckBox) findViewById(R.id.vegBox);
        kosher = (CheckBox) findViewById(R.id.kosherBox);
        glutenFree = (CheckBox) findViewById(R.id.gfBox);
        doneButton = (Button) findViewById(R.id.doneButton);

        //TODO: set all UI elements to correct fields


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
