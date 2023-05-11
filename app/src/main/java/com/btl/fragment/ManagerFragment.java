package com.btl.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.btl.R;
import com.btl.database.SQLiteStudent;
import com.btl.model.Student;

import java.util.Calendar;
import java.util.List;

public class ManagerFragment extends Fragment {

    private EditText edt_name, edt_gpa;
    private TextView btn_date, btn_insert, btn_update, btn_delete;
    private RadioGroup radioGroup;
    private RadioButton rdMale, rdFemale;
    private SQLiteStudent sqLiteStudent;
    private int id;
    private String name, date, gender;
    private Float gpa;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.manager_student, container, false);
    }

   @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        sqLiteStudent = new SQLiteStudent(getContext());
        List<Student> list = sqLiteStudent.getAllStudent();

        id = ManagerFragmentArgs.fromBundle(getArguments()).getId();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                edt_name.setText(list.get(i).getName());
                btn_date.setText(list.get(i).getDate());
                edt_gpa.setText(list.get(i).getGPA() + "");
                if (list.get(i).getGender().equals("Male")) {
                    rdMale.setChecked(true);
                } else {
                    rdFemale.setChecked(true);
                }
                break;
            }
        }


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        if (id == -1) {
            btn_insert.setEnabled(true);
            btn_update.setEnabled(false);
            btn_update.setBackgroundResource(R.drawable.btn_disable_shape);
            btn_delete.setEnabled(false);
            rdMale.setChecked(true);
            btn_delete.setBackgroundResource(R.drawable.btn_disable_shape);
        } else {
            btn_insert.setEnabled(false);
            btn_insert.setBackgroundResource(R.drawable.btn_disable_shape);
            btn_update.setEnabled(true);
            btn_delete.setEnabled(true);
        }

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btn_date.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, year, month, dayOfMonth).show();
            }
        });

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getDataInput()) {
                    sqLiteStudent.insertStudent(new Student(name, date, gender, gpa));
                    backToPreviousFragment(v);
                }

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getDataInput()) {
                    sqLiteStudent.updateStudent(new Student(id, name, date, gender, gpa));
                    backToPreviousFragment(v);
                }

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteStudent.deleteStudent(id);
                backToPreviousFragment(v);
            }
        });
    }

    void backToPreviousFragment(View view) {
        Navigation.findNavController(view).popBackStack();
    }

    boolean getDataInput() {
        name = edt_name.getText().toString();
        date = btn_date.getText().toString();
        if (radioGroup.getCheckedRadioButtonId() == rdMale.getId()) {
            gender = "Male";
        } else {
            gender = "Female";
        }
        if (name.isEmpty() || date.isEmpty() || edt_gpa.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Fill in a blank.", Toast.LENGTH_LONG).show();
            return false;
        }
        gpa = Float.parseFloat(edt_gpa.getText().toString());

        if (gpa > 4.0f || gpa < 0f) {
            Toast.makeText(getContext(), "GPA is not valid.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    void init(View v) {
        edt_name = v.findViewById(R.id.edt_student_name);
        btn_date = v.findViewById(R.id.btn_student_date);
        radioGroup = v.findViewById(R.id.radio_gender);
        rdMale = v.findViewById(R.id.radio_male);
        rdFemale = v.findViewById(R.id.radio_female);
        edt_gpa = v.findViewById(R.id.edt_student_gpa);

        btn_insert = v.findViewById(R.id.btn_insert);
        btn_update = v.findViewById(R.id.btn_update);
        btn_delete = v.findViewById(R.id.btn_delete);
    }
}
