package com.btl.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.btl.R;

import com.btl.fragment.MainFragmentDirections;
import com.btl.model.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private List listStudent;

    public StudentAdapter(List<Student> list) {
        this.listStudent = list;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = (Student) listStudent.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = MainFragmentDirections.actionMainFragmentToManagerFragment(student.getId());
                Navigation.findNavController(v).navigate(action);
            }
        });

        holder.tvt_name.setText(student.getName());
        holder.tvt_date.setText(student.getDate());
        holder.tvt_gender.setText(student.getGender());
        holder.tvt_gpa.setText(student.getGPA() + "");
    }

    @Override
    public int getItemCount() {
        return listStudent.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView tvt_name;
        private TextView tvt_date;
        private TextView tvt_gender;
        private TextView tvt_gpa;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvt_name = itemView.findViewById(R.id.tvt_name);
            tvt_date = itemView.findViewById(R.id.tvt_date_of_birth);
            tvt_gender = itemView.findViewById(R.id.tvt_gender);
            tvt_gpa = itemView.findViewById(R.id.tvt_GPA);
        }
    }
}