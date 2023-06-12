package com.example.tfg_joseangel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tfg_joseangel.clases.Componente;
import com.example.tfg_joseangel.utilidades.ImagenesFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

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

    ImageView image_newproduct;
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
        //Auth = FirebaseAuth.getInstance();

        edt_numSerie = (EditText) findViewById(R.id.edt_numSerieC);
        edt_nombreProd = (EditText) findViewById(R.id.edt_nombreProdC);
        edt_precio = (EditText) findViewById(R.id.edt_precioC);
        edt_stock = (EditText) findViewById(R.id.edt_stockC);
        edt_marca = (EditText) findViewById(R.id.edt_marcaC);
        image_newproduct = (ImageView) findViewById(R.id.image_newproduct);
    }

    public void new_product(View view){
        final String idComp = String.valueOf(edt_numSerie.getText());

        // Referencia a la base de datos
        DatabaseReference compshashmapRef = FirebaseDatabase.getInstance().getReference("compshashmap/" + idComp);
        compshashmapRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    // El producto con el mismo número de serie ya existe
                    Toast.makeText(nuevo_producto.this, "Ya existe un producto con el mismo número de serie", Toast.LENGTH_SHORT).show();
                } else {
                    // El producto no existe, proceder a guardarlo en la base de datos
                    String nombre = String.valueOf(edt_nombreProd.getText());
                    String idComp = String.valueOf(edt_numSerie.getText());
                    String precio = String.valueOf(edt_precio.getText());
                    String cantidad = String.valueOf(edt_stock.getText());
                    String idMar = String.valueOf(edt_marca.getText());
                    Componente c = new Componente(idComp, nombre,  precio, cantidad, idMar);


                    // Guardar el producto en la base de datos
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                    myRef.child("compshashmap").child(c.getIdComp()).setValue(c);
                    Toast.makeText(nuevo_producto.this, "Producto añadido con éxito", Toast.LENGTH_LONG).show();

                    if(imagen_seleccionada != null) {
                        String carpeta = c.getNombre();
                        ImagenesFirebase.subirFoto(carpeta,c.getNombre(), image_newproduct);
                    }
                    Intent intent = new Intent(nuevo_producto.this, HomeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de la consulta si es necesario
            }
        });
    }

    //------------------------------------------
    public void cambiar_imagen(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Selecciona una imagen");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, NUEVA_IMAGEN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NUEVA_IMAGEN && resultCode == Activity.RESULT_OK) {
            imagen_seleccionada = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagen_seleccionada);
                image_newproduct.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void volver(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}