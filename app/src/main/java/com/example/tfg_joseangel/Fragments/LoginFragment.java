package com.example.tfg_joseangel.Fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg_joseangel.HomeActivity;
import com.example.tfg_joseangel.MainActivity;
import com.example.tfg_joseangel.databinding.FragmentLoginBinding;

import com.example.tfg_joseangel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginFragment extends Fragment {

    private EditText login_email;
    private TextInputEditText login_pass;
    private FirebaseAuth auth;
    private DatabaseReference database;
    Button bt_login;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();


        if(auth.getCurrentUser() == null){
            auth.signOut();

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login, container, false);
        login_email= root.findViewById(R.id.login_email);
        login_pass = root.findViewById(R.id.login_pass);
        bt_login = root.findViewById(R.id.bt_login);


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        return root;
    }

    public void login(){
        String log_mail = login_email.getText().toString().trim();
        String log_pass = login_pass.getText().toString().trim();

        if(log_mail.isEmpty()){
            login_email.setError("El email tiene que esta lleno");
            return;
        }
        if(log_pass.isEmpty()){
            login_pass.setError("la contraseña tiene que estar llena");
            return;
        }

        auth.signInWithEmailAndPassword(log_mail,log_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //FirebaseUser user = auth.getCurrentUser();
                    String user = auth.getCurrentUser().getUid();
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "Correo o clave INCORRECTOS, revise sus credenciales", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}