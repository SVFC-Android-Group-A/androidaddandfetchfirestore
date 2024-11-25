package com.example.androidaddandfetchfirestore.books;

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

public class bookspage extends MainActivity {

    EditText etTitle, etAuthor, etGenre;
    Button btnAddItem, btnGetItem;
    TextView tvResults;

    books bookObject;
    StringBuilder stringBuilder;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books);

        bookObject = new books();
        stringBuilder = new StringBuilder();
        firestore = FirebaseFirestore.getInstance();

        etTitle = findViewById(R.id.et_title);
        etAuthor = findViewById(R.id.et_author);
        etGenre = findViewById(R.id.et_genre);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);
        tvResults = findViewById(R.id.tv_results);

        btnAddItem.setOnClickListener(v -> addFunction());

        btnGetItem.setOnClickListener(v -> getFunction());
    }

    //added firestore firebase in this code
    private void addFunction() {
        bookObject.setTitle(etTitle.getText().toString());
        bookObject.setAuthor(etAuthor.getText().toString());
        bookObject.setGenre(etGenre.getText().toString());

        Log.d("MAIN", "Title: " + bookObject.getTitle() + ", Author: " + bookObject.getAuthor() + ", Genre: " + bookObject.getGenre());

        firestore.collection("book")
                .add(bookObject)
                .addOnSuccessListener(documentReference -> {
                    stringBuilder.append("\n\n Title: ").append(bookObject.getTitle());
                    stringBuilder.append("\n Author: ").append(bookObject.getAuthor());
                    stringBuilder.append("\n Genre: ").append(bookObject.getGenre());

                    tvResults.setText(stringBuilder.toString());
                })
                .addOnFailureListener(e -> Log.e("MAIN", e.getMessage()));
    }

    private void getFunction() {
        StringBuilder stringBuilder1 = new StringBuilder();

        firestore.collection("book") // Removed the incorrect semicolon here
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) { // Fixed spelling of "isSuccessful"

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            books book = document.toObject(books.class);
                            stringBuilder1.append("\n\n Title: ").append(book.getTitle());
                            stringBuilder1.append("\n Author: ").append(book.getAuthor());
                            stringBuilder1.append("\n Genre: ").append(book.getGenre());
                        }

                        tvResults.setText(stringBuilder1.toString());

                    } else {
                        Log.e("MAIN", task.getException().getMessage());
                    }
                })
                .addOnFailureListener(e -> Log.e("MAIN", e.getMessage()));
    }
}
