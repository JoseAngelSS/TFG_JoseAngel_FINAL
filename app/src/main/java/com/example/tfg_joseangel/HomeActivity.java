package com.example.tfg_joseangel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    FirebaseDatabase database;
    private FirebaseAuth Auth;
    private FirebaseUser currentUser;

    @Override
    public void onStart(){
        super.onStart();
        currentUser = Auth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }
    public void mostrar_componentes(View view){
        Intent intent = new Intent(this, MostrarProducto.class);
        startActivity(intent);
    }
    public void crear_componentes(View view){
        Intent intent = new Intent(this, nuevo_producto.class);
        startActivity(intent);
    }
}