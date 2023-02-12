package com.example.tfg_joseangel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tfg_joseangel.clases.Componente;
import com.example.tfg_joseangel.utilidades.ImagenesFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class nuevo_producto extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private EditText edt_nombreProd;
    private EditText edt_numSerie;
    private EditText edt_precio;
    private EditText edt_stock;
    private EditText edt_marca;
    public static final int NUEVA_IMAGEN = 1;
    Uri imagen_seleccionada = null;
    ImageView img_newproduct;
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance(); //crea instancia Firebase para autenticar usuarios
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();  //obtener el usuario actualmente autenticado en Firebase.
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(this, "debes autenticarte primero", Toast.LENGTH_SHORT).show();
            FirebaseUser user = firebaseAuth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);

        edt_numSerie = (EditText) findViewById(R.id.edt_numSerie);
        edt_nombreProd = (EditText) findViewById(R.id.edt_nombreProd);
        edt_precio = (EditText) findViewById(R.id.edt_precio);
        edt_stock = (EditText) findViewById(R.id.edt_stock);
        edt_marca = (EditText) findViewById(R.id.edt_marca);
        img_newproduct = (ImageView) findViewById(R.id.img_newproduct);
    }

    public void new_product(View view){
        String nombre = String.valueOf(edt_nombreProd.getText());
        String idComp = String.valueOf(edt_numSerie.getText());
        String precio = String.valueOf(edt_precio.getText());
        String cantidad = String.valueOf(edt_stock.getText());
        String idMar = String.valueOf(edt_marca.getText());
        Componente c = new Componente(nombre, idComp, precio, cantidad, idMar);
        //-----------------------------------
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("compshashmap").child(c.getNombre()).setValue(c);
        Toast.makeText(this,"producto añadido con exito",Toast.LENGTH_LONG).show();
        // codigo para guardar la imagen del usuario en firebase store
        if(imagen_seleccionada != null) {
            String carpeta = c.getNombre();
            ImagenesFirebase.subirFoto(carpeta,c.getNombre(), img_newproduct);
        }
    }

}