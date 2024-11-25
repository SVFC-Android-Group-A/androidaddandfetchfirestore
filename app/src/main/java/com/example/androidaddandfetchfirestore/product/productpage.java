package com.example.androidaddandfetchfirestore.product;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidaddandfetchfirestore.MainActivity;
import com.example.androidaddandfetchfirestore.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class productpage extends MainActivity {

    EditText etName, etPrice, etCategory;
    Button btnAddItem, btnGetItem;
    TextView tvResults;

    StringBuilder stringBuilder;
    FirebaseFirestore firestore; // Firestore instance
    product productObject; // Product object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);

        stringBuilder = new StringBuilder();
        firestore = FirebaseFirestore.getInstance(); // Initialize Firestore
        productObject = new product(); // Initialize product object

        etName = findViewById(R.id.et_name);
        etPrice = findViewById(R.id.et_price);
        etCategory = findViewById(R.id.et_category);

        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);
        tvResults = findViewById(R.id.tv_results);

        btnAddItem.setOnClickListener(v -> addFunction());

        btnGetItem.setOnClickListener(v -> getFunction());
    }

    private void addFunction() {
        productObject.setName(etName.getText().toString());
        productObject.setPrice(Integer.parseInt(etPrice.getText().toString()));
        productObject.setCategory(etCategory.getText().toString());

        Log.d("MAIN", "Name: " + productObject.getName() + ", Price: " + productObject.getPrice() + ", Category: " + productObject.getCategory());

        firestore.collection("product")
                .add(productObject)
                .addOnSuccessListener(documentReference -> { // Corrected spelling
                    stringBuilder.append("\n\n Name: ").append(productObject.getName());
                    stringBuilder.append("\n Price: ").append(productObject.getPrice());
                    stringBuilder.append("\n Category: ").append(productObject.getCategory());

                    tvResults.setText(stringBuilder.toString());
                })
                .addOnFailureListener(e -> Log.e("MAIN", e.getMessage()));
    }

    private void getFunction() {
        StringBuilder stringBuilder1 = new StringBuilder();

        firestore.collection("product")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            product product = document.toObject(product.class);
                            stringBuilder1.append("\n\n Name: ").append(product.getName());
                            stringBuilder1.append("\n Price: ").append(product.getPrice());
                            stringBuilder1.append("\n Category: ").append(product.getCategory());
                        }
                        tvResults.setText(stringBuilder1.toString());

                    } else {
                        Log.e("MAIN", task.getException().getMessage());
                    }
                })
                .addOnFailureListener(e -> Log.e("MAIN", e.getMessage()));
    }
}
