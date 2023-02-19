package com.example.tfg_joseangel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tfg_joseangel.RecyclerView.ComponenteFirebaseUIViewHolder;
import com.example.tfg_joseangel.RecyclerView.ListaComponentesFirebaseUIAdapter;
import com.example.tfg_joseangel.clases.Componente;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MostrarProducto extends AppCompatActivity {

    private FirebaseAuth Auth;
    public void onStart() {
        super.onStart();
        adapter.startListening(); // para que empiece a funcionar el firebaseUI

        Auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = Auth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(this, "debes iniciar sesion", Toast.LENGTH_SHORT).show();
            FirebaseUser user = Auth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
    public static int PETICION1 = 1;

    private Context contexto = null;
    private RecyclerView rv_producto = null;
    DatabaseReference databaseReference = null;
    FirebaseRecyclerOptions<Componente> options = null;
    FirebaseRecyclerAdapter<Componente, ComponenteFirebaseUIViewHolder> adapter;
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_producto);

        rv_producto= (RecyclerView) findViewById(R.id.rv_producto);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Query query = databaseReference.child("alumnoshashmap");
        options = new FirebaseRecyclerOptions.Builder<Componente>().setQuery(query, Componente.class).build();
        adapter = new ListaComponentesFirebaseUIAdapter(options, this);

        rv_producto.setAdapter(adapter);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            rv_producto.setLayoutManager(new GridLayoutManager(this,2));
        } else {
            // In portrait
            rv_producto.setLayoutManager(new LinearLayoutManager(this));
        }
    }
    public void addProductoFirebaseUI(View view) {
        Intent intent = new Intent(this, nuevo_producto.class);
        startActivity(intent);
    }
}