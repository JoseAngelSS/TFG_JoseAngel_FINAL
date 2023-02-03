package com.example.tfg_joseangel;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg_joseangel.clases.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class fragment_register extends Fragment {

    Button btn_reg;
    EditText edt_nombre, edt_mail, edt_pass;
    TextView txsing;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_register, container, false);
        btn_reg =root.findViewById(R.id.btn_reg);
        edt_mail = root.findViewById(R.id.edt_mail);
        edt_pass = root.findViewById(R.id.edt_pass);
        edt_nombre = root.findViewById(R.id.edt_nombre);
        txsing = root.findViewById(R.id.txsing);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registrar();
            }
        });

        txsing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {}
        });

        return root;
    }

    private void registrar(){

        String name = edt_nombre.getText().toString();
        String email =  edt_mail.getText().toString().trim();
        String password =  edt_pass.getText().toString().trim();

        if(name.isEmpty()){
            edt_nombre.setError("El campo no puede estar vacio");
            return;
        }
        if(password.isEmpty()){
            edt_pass.setError("El campo no puede estar vacio");
            return;
        }
        if(email.isEmpty()){
            edt_mail.setError("El campo no puede estar vacio");
            return;
        }
        if(password.length() < 4){
            edt_pass.setError("La contraseña debe tener al menos 4 caracteres");
            return;
        }

        //Creacion de Usuario
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Usuario usuario = new Usuario(name, email, password);
                    String id = task.getResult().getUser().getUid();
                    firebaseDatabase.getReference().child("Usuario").child(id).setValue(usuario);
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getActivity(), "ERROR ", Toast.LENGTH_SHORT).show();
                    Log.e("ERROR", "onComplete: ", task.getException() );
                }
            }

        });

    }
}