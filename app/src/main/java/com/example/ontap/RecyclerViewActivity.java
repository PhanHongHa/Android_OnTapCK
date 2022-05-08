package com.example.ontap;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StudentAdapter studentAdapter;
    ArrayList<Student> students;
    private FirebaseFirestore db;
    ProgressDialog dialog;
    Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView= findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        students = new ArrayList<>();
        studentAdapter = new StudentAdapter(RecyclerViewActivity.this, students);
        recyclerView.setAdapter(studentAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelper(studentAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        ReadData();

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener((view) -> {
            Intent intent = new Intent(this, CreateActivity.class);
            startActivity(intent);
        });
    }

    public void ReadData() {

        db.collection("students").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                students.clear();
                for (DocumentSnapshot snapshot : task.getResult()){
                    Student student = new Student(snapshot.getString("id"), snapshot.getString("ten"), snapshot.getString("lop"), snapshot.getString("email"));
                    students.add(student);
                }
                studentAdapter.notifyDataSetChanged();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RecyclerViewActivity.this, "Firebase error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}