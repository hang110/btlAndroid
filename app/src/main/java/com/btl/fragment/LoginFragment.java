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
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.btl.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private TextView btn_login, btn_create_acc;
    private EditText edt_user, edt_password;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

   @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        getCurrentUser(view);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAccess(v);
            }
        });

        btn_create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    void getCurrentUser(View view) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToMainFragment(currentUser.getEmail());
            Navigation.findNavController(view).navigate(action);
        }
    }

    void getAccess(View view) {
        String email = edt_user.getText().toString();
        String password = edt_password.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        NavDirections action = LoginFragmentDirections.actionLoginFragmentToMainFragment(email);
                            Navigation.findNavController(view).navigate(action);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Register failed!", Toast.LENGTH_LONG).show();
                    }
                });
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            NavDirections action = LoginFragmentDirections.actionLoginFragmentToMainFragment(email);
//                            Navigation.findNavController(view).navigate(action);
//                        } else {
//                            Toast.makeText(getContext(), "Login failed!", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
    }

    void init() {
        edt_user = getView().findViewById(R.id.edt_username);
        edt_password = getView().findViewById(R.id.edt_password);
        btn_login = getView().findViewById(R.id.btn_login);
        btn_create_acc = getView().findViewById(R.id.url_create_account);
        firebaseAuth = FirebaseAuth.getInstance();
    }
}
