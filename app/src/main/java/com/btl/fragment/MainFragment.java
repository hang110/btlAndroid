package com.btl.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btl.R;
import com.btl.adapter.StudentAdapter;
import com.btl.database.SQLiteStudent;
import com.btl.model.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainFragment extends Fragment {

    private EditText edt_search;
    private TextView btn_logout, btn_manager;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private SQLiteStudent sqLiteStudent;
    private List<Student> list, sortList;
    private Spinner spinner;
    private FloatingActionButton flt;
    private String name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        list = sqLiteStudent.getAllStudent();
        sortList = list;
        StudentAdapter adapter = new StudentAdapter(list);
        recyclerView.swapAdapter(adapter, true);

        flt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStatistical(v);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        list = new ArrayList<>();
        sortList = new ArrayList<>();
        sqLiteStudent = new SQLiteStudent(getContext());
        list = sqLiteStudent.getAllStudent();
        sortList = list;

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        btn_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = MainFragmentDirections.actionMainFragmentToManagerFragment(-1);
                Navigation.findNavController(v).navigate(action);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Log Out").setMessage("Do you want to log out from " + name + "?")
                        .setCancelable(true)
                        .setIcon(R.drawable.ic_baseline_logout_24)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                firebaseAuth.signOut();
                                Navigation.findNavController(view).popBackStack();
                            }
                        }).show();

            }
        });

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0: {
                        Collections.sort(sortList, new Comparator<Student>() {
                            @Override
                            public int compare(Student o1, Student o2) {
                                return o1.getName().compareTo(o2.getName());
                            }
                        });
                        break;
                    }
                    case 1: {
                        Collections.sort(sortList, new Comparator<Student>() {
                            @Override
                            public int compare(Student o1, Student o2) {
                                Float change1 = o1.getGPA();
                                Float change2 = o2.getGPA();
                                return change1.compareTo(change2);
                            }
                        });
                        break;
                    }
                }
                onItemSelectedHandler(sortList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        StudentAdapter adapter = new StudentAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void onItemSelectedHandler(List<Student> listStudent) {
        StudentAdapter adapter = new StudentAdapter(listStudent);
        recyclerView.swapAdapter(adapter, true);
    }

    void getStatistical(View v) {
//        int yeu = 0, tb = 0, kha = 0, gioi = 0;
//        for (int i = 0; i < list.size(); i++) {
//            float x = list.get(i).getGPA();
//            if (x < 2.0f) {
//                yeu++;
//                continue;
//            }
//            if (x >= 2.0f && x < 2.5f) {
//                tb++;
//                continue;
//            }
//            if (x >= 2.5f && x < 3.2f) {
//                kha++;
//                continue;
//            }
//            if (x >= 3.2) {
//                gioi++;
//                continue;
//            }
//        }
        int male = 0, female = 0;
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getGender().equals("Male")) {
                male ++;
            } else {
                female++;
            }
        }
        NavDirections action = MainFragmentDirections.actionMainFragmentToStatisticalFragment(male, female);
        Navigation.findNavController(v).navigate(action);
    }

    void filterList(String name) {
        list = sqLiteStudent.getStudentByName(name);
        StudentAdapter adapter = new StudentAdapter(list);
        recyclerView.swapAdapter(adapter, true);
    }

    void init(View v) {
        btn_logout = v.findViewById(R.id.btn_logout);
        edt_search = v.findViewById(R.id.edt_search);
        btn_manager = v.findViewById(R.id.btn_manager);
        recyclerView = v.findViewById(R.id.recycler_student);
        flt = v.findViewById(R.id.flt_statistical);
        spinner = v.findViewById(R.id.spn_sort);

        firebaseAuth = FirebaseAuth.getInstance();

        name = MainFragmentArgs.fromBundle(getArguments()).getEmail();
        btn_logout.setText("LOGOUT - " + name);
    }
}
