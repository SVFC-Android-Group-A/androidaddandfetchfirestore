package com.example.androidaddandfetchfirestore.employee;

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

public class employeepage extends MainActivity {

    EditText etName, etDepartment, etEmail;
    Button btnAddItem, btnGetItem;
    TextView tvResults;

    employee employeeObject;
    StringBuilder stringBuilder;
    FirebaseFirestore firestore; // Added FirebaseFirestore instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee);

        employeeObject = new employee();
        stringBuilder = new StringBuilder();
        firestore = FirebaseFirestore.getInstance(); // Initialize Firestore instance

        etName = findViewById(R.id.et_name);
        etDepartment = findViewById(R.id.et_department);
        etEmail = findViewById(R.id.et_email);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);
        tvResults = findViewById(R.id.tv_results);

        btnAddItem.setOnClickListener(v -> addFunction());

        btnGetItem.setOnClickListener(v -> getFunction());
    }

    // Added firestore in this activity
    private void addFunction() {
        employeeObject.setName(etName.getText().toString());
        employeeObject.setDepartment(etDepartment.getText().toString());
        employeeObject.setEmail(etEmail.getText().toString());

        Log.d("MAIN", "Name: " + employeeObject.getName() + ", Department: " + employeeObject.getDepartment() + ", Email: " + employeeObject.getEmail());

        firestore.collection("employee")
                .add(employeeObject)
                .addOnSuccessListener(documentReference -> {
                    stringBuilder.append("\n\n Name: ").append(employeeObject.getName());
                    stringBuilder.append("\n Department: ").append(employeeObject.getDepartment());
                    stringBuilder.append("\n Email: ").append(employeeObject.getEmail());

                    tvResults.setText(stringBuilder.toString());
                })
                .addOnFailureListener(e -> Log.e("MAIN", e.getMessage()));
    }

    private void getFunction() {
        StringBuilder stringBuilder1 = new StringBuilder();

        firestore.collection("employee")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            employee employee = document.toObject(employee.class);
                            stringBuilder1.append("\n\n Name: ").append(employee.getName());
                            stringBuilder1.append("\n Department: ").append(employee.getDepartment());
                            stringBuilder1.append("\n Email: ").append(employee.getEmail());
                        }
                        tvResults.setText(stringBuilder1.toString());

                    } else {
                        Log.e("MAIN", task.getException().getMessage());
                    }
                })
                .addOnFailureListener(e -> Log.e("MAIN", e.getMessage()));
    }
}
