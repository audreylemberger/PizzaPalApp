package com.example.demouser.pizzapalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity {


    public static final String KEY_ITEM = "KEY_ITEM";
    private Pizza itemToEdit = null;
    private Button addButton;
    private Button cancelButton;
    private CheckBox gfBox;
    private CheckBox vegBox;
    private CheckBox kosherBox;
    private CheckBox veganBox;
    private Spinner buildings;
    private EditText toppings;
    private EditText roomNumber;
    private EditText venderInfo;
    String[] buildingItems;
    private DatabaseReference mFirebaseDatabase;
    private Pizza itemResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

//        if (getIntent().getSerializableExtra(MainActivity.KEY_EDIT) != null) {
//            itemToEdit = (Pizza) getIntent().getSerializableExtra(MainActivity.KEY_EDIT);
//        }
        addButton = ((Button)findViewById(R.id.backButton));
        cancelButton = ((Button)findViewById(R.id.doneButton));
        gfBox = ((CheckBox) findViewById(R.id.gfBox));
        vegBox = ((CheckBox) findViewById(R.id.vegBox));
        kosherBox = ((CheckBox) findViewById(R.id.kosherBox));
        veganBox = ((CheckBox) findViewById(R.id.veganBox));
        roomNumber = ((EditText)findViewById(R.id.edRoomNumText));
        venderInfo = ((EditText) findViewById(R.id.edVendorText));
        toppings = ((EditText) findViewById(R.id.toppingsEd));



        buildings = (Spinner)findViewById(R.id.spinner1);
        buildingItems = new String[]{"1837", "Abbey", "Brigham", "Buckland", "Creighton", "Dickinson"
                , "Ham", "MacGregor", "North Mandelle", "South Mandelle", "Mead", "Pearsons", "Pearsons Annex"
                , "Porter", "Prospect", "North Rockefeller", "South Rockefeller", "Safford", "Torrey", "Wilder"
                , "Art Building", "Carr Laboratory", "Ciruti", "Clapp", "Cleveland", "Dwight", "Equestrian Center"
                , "StonyBrook", "Kendade", "Kendall Athletic Complex", "Pratt", "Reese", "Rooke Theatre"
                , "Shattuck", "Skinner", "Talcott Greenhouse", "Williston Observatory", "Smith Library"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, buildingItems);

        buildings.setAdapter(adapter);


        //rowspan/columnspan
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                configureDatabase();



                Intent mainIntent = new Intent(AddActivity.this,MainActivity.class);
                AddActivity.this.startActivity(mainIntent);
                AddActivity.this.finish();

            }
        });

//        if (itemToEdit != null) {
//            roomNumber.setText(itemToEdit.getRoom());
//            venderInfo.setText(itemToEdit.getVendor());
//            buildings.setSelection(buildings.ge);
//            vegBox.setChecked(itemToEdit.getIsVegan());
//            veganBox.setChecked(itemToEdit.getIsVegetarian());
//            kosherBox.setChecked(itemToEdit.getIsKosher());
//            gfBox.setChecked(itemToEdit.getIsGF());
//            toppings.setText(itemToEdit.getToppings());
//
//
//        }

    }


    private void configureDatabase(){
        mFirebaseDatabase =  FirebaseDatabase.getInstance().getReference();
        Pizza pizza = new Pizza();
        pizza.setBuilding(buildingItems[buildings.getSelectedItemPosition()]);
        pizza.setRoom(roomNumber.getText().toString());
        pizza.setVegan(veganBox.isChecked());
        pizza.setVeg(vegBox.isChecked());
        pizza.setKosher(kosherBox.isChecked());
        pizza.setGF(gfBox.isChecked());
        pizza.setVendor(venderInfo.getText().toString());


        //TODO: figure out setting ID
        mFirebaseDatabase.child("pizza").push().setValue(pizza);

        mFirebaseDatabase.child("pizza").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Pizza pizza = dataSnapshot.getValue(Pizza.class);
                pizza.setId(s);
                mFirebaseDatabase.child("pizza").child(s).setValue(pizza);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

        //if doesnt exist, adds players as id
        //mFirebaseDatabase.child("pizza").push().setValue(itemResult);
    }

//    private void saveItem() {
//        Intent intentResult = new Intent();
//        Pizza itemResult = null;
//        if (itemToEdit != null) {
//            itemResult = itemToEdit;
//        } else {
//            itemResult = new Pizza();
//
//        }
//
//
//        itemResult.setRoom(roomNumber.getText().toString());
//        itemResult.setBuilding(buildingItems[buildings.getSelectedItemPosition()]);
//        itemResult.setBuilding((buildings.getSelectedItem().toString()));
//        itemResult.setVegan(veganBox.isChecked());
//        itemResult.setVeg(vegBox.isChecked());
//        itemResult.setKosher(kosherBox.isChecked());
//        itemResult.setGF(gfBox.isChecked());
//
//        //instead get from firebase database
//        intentResult.putExtra(KEY_ITEM, itemResult);
//        setResult(RESULT_OK, intentResult);
//        finish();
//    }



}
