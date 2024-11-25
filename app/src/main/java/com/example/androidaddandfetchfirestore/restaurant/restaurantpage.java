package com.example.androidaddandfetchfirestore.restaurant;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidaddandfetchfirestore.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class restaurantpage extends AppCompatActivity {

    EditText etName, etType, etLocation;
    Button btnAddItem, btnGetItem;
    TextView tvResults;

    restaurant restaurantObject; // Renamed from restaurant to avoid conflict
    StringBuilder stringBuilder;
    FirebaseFirestore firestore; // Firestore instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);

        restaurantObject = new restaurant(); // Initialize restaurant object
        stringBuilder = new StringBuilder();
        firestore = FirebaseFirestore.getInstance(); // Initialize Firestore

        etName = findViewById(R.id.et_name);
        etType = findViewById(R.id.et_type);
        etLocation = findViewById(R.id.et_location);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);
        tvResults = findViewById(R.id.tv_results);

        btnAddItem.setOnClickListener(v -> addFunction());

        btnGetItem.setOnClickListener(v -> getFunction());
    }

    private void addFunction() {
        restaurantObject.setName(etName.getText().toString());
        restaurantObject.setType(etType.getText().toString());
        restaurantObject.setLocation(etLocation.getText().toString());

        Log.d("MAIN", "Name: " + restaurantObject.getName() + ", Type: " + restaurantObject.getType() + ", Location: " + restaurantObject.getLocation());

        firestore.collection("restaurant")
                .add(restaurantObject)
                .addOnSuccessListener(documentReference -> {
                    stringBuilder.append("\n\n Restaurant Name: ").append(restaurantObject.getName());
                    stringBuilder.append("\n Type: ").append(restaurantObject.getType());
                    stringBuilder.append("\n Location: ").append(restaurantObject.getLocation());

                    tvResults.setText(stringBuilder.toString());
                })
                .addOnFailureListener(e -> Log.e("MAIN", e.getMessage()));
    }

    private void getFunction() {
        StringBuilder stringBuilder1 = new StringBuilder();

        firestore.collection("restaurant")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            restaurant restaurantFetched = document.toObject(restaurant.class); // Renamed to avoid conflicts
                            stringBuilder1.append("\n\n Restaurant Name: ").append(restaurantFetched.getName());
                            stringBuilder1.append("\n Type: ").append(restaurantFetched.getType());
                            stringBuilder1.append("\n Location: ").append(restaurantFetched.getLocation());
                        }
                        tvResults.setText(stringBuilder1.toString());

                    } else {
                        Log.e("MAIN", task.getException().getMessage());
                    }
                })
                .addOnFailureListener(e -> Log.e("MAIN", e.getMessage()));
    }
}
