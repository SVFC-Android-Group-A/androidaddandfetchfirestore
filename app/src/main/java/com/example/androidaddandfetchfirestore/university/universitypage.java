package com.example.androidaddandfetchfirestore.university;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidaddandfetchfirestore.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class universitypage extends AppCompatActivity {

    private EditText etName, etType, etWebsite;
    private Button btnAddItem, btnGetItem;
    private TextView tvResults;

    private university universityObject; // Object to store university data
    private StringBuilder stringBuilder;
    private FirebaseFirestore firestore; // Firestore instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.university);

        // Initialize Firestore and UI components
        firestore = FirebaseFirestore.getInstance();
        universityObject = new university();
        stringBuilder = new StringBuilder();

        etName = findViewById(R.id.et_name);
        etType = findViewById(R.id.et_type);
        etWebsite = findViewById(R.id.et_website);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);
        tvResults = findViewById(R.id.tv_results);

        btnAddItem.setOnClickListener(v -> addFunction());
        btnGetItem.setOnClickListener(v -> getFunction());
    }

    private void addFunction() {
        // Set values from input fields to the university object
        universityObject.setName(etName.getText().toString());
        universityObject.setType(etType.getText().toString());
        universityObject.setWebsite(etWebsite.getText().toString());

        // Log values for debugging
        Log.d("MAIN", "Name: " + universityObject.getName() +
                ", Type: " + universityObject.getType() +
                ", Website: " + universityObject.getWebsite());

        // Add data to Firestore
        firestore.collection("university")
                .add(universityObject)
                .addOnSuccessListener(documentReference -> {
                    stringBuilder.append("\n\n School Name: ").append(universityObject.getName());
                    stringBuilder.append("\n Type: ").append(universityObject.getType());
                    stringBuilder.append("\n School Website: ").append(universityObject.getWebsite());

                    // Update UI with the added data
                    tvResults.setText(stringBuilder.toString());
                })
                .addOnFailureListener(e -> Log.e("MAIN", e.getMessage()));
    }

    private void getFunction() {
        StringBuilder stringBuilder1 = new StringBuilder();

        // Fetch data from Firestore
        firestore.collection("university")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            university universityFetched = document.toObject(university.class);
                            stringBuilder1.append("\n\n School Name: ").append(universityFetched.getName());
                            stringBuilder1.append("\n Type: ").append(universityFetched.getType());
                            stringBuilder1.append("\n School Website: ").append(universityFetched.getWebsite());
                        }
                        // Update UI with the fetched data
                        tvResults.setText(stringBuilder1.toString());
                    } else {
                        Log.e("MAIN", task.getException().getMessage());
                    }
                })
                .addOnFailureListener(e -> Log.e("MAIN", e.getMessage()));
    }
}
