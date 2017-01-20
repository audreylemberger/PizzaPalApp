package com.example.demouser.pizzapalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
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
        location = (TextView) findViewById(R.id.edRoomNumText);
        vendor = (TextView) findViewById(R.id.edVendorText);
        toppings = (TextView) findViewById(R.id.toppingsEd);
        vegan = (CheckBox) findViewById(R.id.veganBox);
        vegetarian = (CheckBox) findViewById(R.id.vegBox);
        kosher = (CheckBox) findViewById(R.id.kosherBox);
        glutenFree = (CheckBox) findViewById(R.id.gfBox);
        doneButton = (Button) findViewById(R.id.doneButton);

        Intent intent = getIntent();
        id = intent.getStringExtra(MainActivity.KEY_VIEW);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabase.child("pizza").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                doSomethingElseWithPizza(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                doSomethingElseWithPizza(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mFirebaseDatabase.child("pizza").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //doSomethingElseWithPizza(dataSnapshot);
//                Pizza pizza = new Pizza(dataSnapshot.getValue(Pizza.class).getRoom(), dataSnapshot.getValue(Pizza.class).getBuilding(), dataSnapshot.getValue(Pizza.class).getToppings(), dataSnapshot.getValue(Pizza.class).getVendor(),
//                        dataSnapshot.getValue(Pizza.class).isVegan(), dataSnapshot.getValue(Pizza.class).isVeg(), dataSnapshot.getValue(Pizza.class).isKosher(),
//                        dataSnapshot.getValue(Pizza.class).isGF());

                String loc = dataSnapshot.getValue(Pizza.class).getBuilding() + " " + dataSnapshot.getValue(Pizza.class).getRoom();
                location.setText(loc);
                vendor.setText(dataSnapshot.getValue(Pizza.class).getVendor());
                toppings.setText(dataSnapshot.getValue(Pizza.class).getToppings());
                vegan.setChecked(dataSnapshot.getValue(Pizza.class).isVegan());
                if(dataSnapshot.getValue(Pizza.class).isVegan()) {
                    vegetarian.setChecked(true);
                    kosher.setChecked(true);
                }
                else if(dataSnapshot.getValue(Pizza.class).isVeg()) {
                    kosher.setChecked(true);
                }
                glutenFree.setChecked(dataSnapshot.getValue(Pizza.class).isGF());

                //dataSnapshot.getValue(Pizza.class);
                //doSomethingWithPizza(pizza);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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

    public void doSomethingWithPizza(Pizza pizza) {
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

    public void doSomethingElseWithPizza(DataSnapshot dataSnapshot) {
        Pizza pizza = dataSnapshot.getValue(Pizza.class);
        if(pizza.getId() == id) {
            PizzaViewActivity.this.pizza = pizza;
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



}
