package com.btl.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.btl.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment {

    private TextView btn_register, btn_login;
    private EditText edt_user, edt_password, edt_retype_password;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signup_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAccess(v);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });
    }

    void getAccess(View view) {
        String email = edt_user.getText().toString();
        String password = edt_password.getText().toString();
        String retypepassword = edt_retype_password.getText().toString();
        if(!password.equals(retypepassword)){
            Toast.makeText(getContext(), "Password and Retype Password doesn't match!", Toast.LENGTH_LONG).show();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            Toast.makeText(getContext(), "Successfull",Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).popBackStack();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Register failed!", Toast.LENGTH_LONG).show();
                        }
                    });
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                Navigation.findNavController(view).popBackStack();
//                                Toast.makeText(getContext(), "Successfull",Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(getContext(), "Register failed!", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    });
        }
    }

    void init() {
        edt_user = getView().findViewById(R.id.edt_username);
        edt_password = getView().findViewById(R.id.edt_password);
        edt_retype_password = getView().findViewById(R.id.edt_retype_password);
        btn_register = getView().findViewById(R.id.btn_register);
        btn_login = getView().findViewById(R.id.url_login);

        firebaseAuth = FirebaseAuth.getInstance();
    }
}
