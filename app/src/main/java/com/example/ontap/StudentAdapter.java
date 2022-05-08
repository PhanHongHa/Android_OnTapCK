package com.example.ontap;




import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    Context context;
    ArrayList<Student> students;
    FirebaseFirestore db;


    public StudentAdapter(Context context, ArrayList<Student> students) {
        this.context = context;
        this.students = students;
    }

    public void updateData(int position){
        Student student = students.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("IdU", student.getId());
        bundle.putString("TenU", student.getTen());
        bundle.putString("LopU", student.getLop());
        bundle.putString("EmailU", student.getEmail());
        Intent intent = new Intent(context, CreateActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void deleteData(int position){
        Student student = students.get(position);
        db.collection("students").document(student.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved(int posotion){
        students.remove(posotion);
        notifyRemoved(posotion);
        RecyclerViewActivity recyclerViewActivity = new RecyclerViewActivity();
        recyclerViewActivity.ReadData();
    }
    @NonNull
    @Override
    public StudentAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item,parent,false);




        return new StudentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewHolder holder, int position) {

        Student student = students.get(position);
        holder.ten.setText(student.ten);
        holder.lop.setText(student.lop);
        holder.email.setText(student.email);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView ten, lop, email;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            ten = itemView.findViewById(R.id.txtTen_item);
            lop = itemView.findViewById(R.id.txtLop_item);
            email = itemView.findViewById(R.id.txtEmail_item);
        }
    }

}