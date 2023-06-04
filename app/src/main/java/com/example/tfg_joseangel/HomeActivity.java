package com.example.tfg_joseangel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    FirebaseDatabase database;
    public static ImageButton imageButton;
    private FirebaseAuth Auth;
    FirebaseUser logged_user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
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

        //Inicio Firebase auth
        Auth = FirebaseAuth.getInstance();

        imageButton= (ImageButton)findViewById(R.id.imgButton);

    }
    public void mostrar_componentes(View view){
        Intent intent = new Intent(this, MostrarProducto.class);
        startActivity(intent);
    }
    public void crear_componentes(View view){
        Intent intent = new Intent(this, nuevo_producto.class);
        startActivity(intent);
    }
    public void cerrarsesion(View view){
        Auth.signOut();
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void verperfil(View view){
        Intent intent = new Intent(this, ProfileUserActivity.class);
        startActivity(intent);
    }

    public void verventas(View view){
        Intent intent = new Intent(this, VentasActivity.class);
        startActivity(intent);
    }
}