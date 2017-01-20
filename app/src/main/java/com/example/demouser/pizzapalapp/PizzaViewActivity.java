package com.example.demouser.pizzapalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PizzaViewActivity extends AppCompatActivity {

    private TextView location;
    private TextView vendor;
    private TextView toppings;
    private CheckBox vegan;
    private CheckBox vegetarian;

    private CheckBox kosher;
    private CheckBox glutenFree;
    private Button doneButton;

    private DatabaseReference mFirebaseDatabase;
    private Pizza pizza;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_view);

        Intent intent = getIntent();
        id = intent.getStringExtra(MainActivity.KEY_VIEW);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabase.child("pizza").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pizza = dataSnapshot.getValue(Pizza.class);
                doSomethingWithPizza();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        location = (TextView) findViewById(R.id.edRoomNumText);
        vendor = (TextView) findViewById(R.id.edVendorText);
        toppings = (TextView) findViewById(R.id.toppingsEd);
        vegan = (CheckBox) findViewById(R.id.veganBox);
        vegetarian = (CheckBox) findViewById(R.id.vegBox);
        kosher = (CheckBox) findViewById(R.id.kosherBox);
        glutenFree = (CheckBox) findViewById(R.id.gfBox);
        doneButton = (Button) findViewById(R.id.doneButton);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: delete pizza
                mFirebaseDatabase.child("pizza").child(id).removeValue();
                //go back to MainActivity
                finish();
            }
        });

    }

    public void doSomethingWithPizza() {
        //TODO: set all UI elements to correct fields
        String loc = pizza.getBuilding() + " " + pizza.getRoom();
        location.setText(loc);
        vendor.setText(pizza.getVendor());
        toppings.setText(pizza.getToppings());
        vegan.setChecked(pizza.isVegan());
        vegetarian.setChecked(pizza.isVeg());
        kosher.setChecked(pizza.isKosher());
        glutenFree.setChecked(pizza.isGF());
    }


}
