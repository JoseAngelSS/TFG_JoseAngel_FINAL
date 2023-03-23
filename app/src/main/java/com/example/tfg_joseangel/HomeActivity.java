package com.example.tfg_joseangel;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    FirebaseDatabase database;
    public static ImageView imageView;
    private FirebaseAuth Auth;
    Toolbar toolbar;
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
        myRef = firebaseDatabase.getReference("User");
        logged_user = Auth.getCurrentUser();

        imageView = (ImageView)findViewById(R.id.img_perfil);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbar.setTitle("Perfil");
                ProfileFragment fp = new ProfileFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,fp)
                        .addToBackStack(null)
                        .commit();
            }
        });
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
}