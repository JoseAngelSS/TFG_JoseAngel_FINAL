package com.example.tfg_joseangel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tfg_joseangel.RecyclerView.ComponenteFirebaseUIViewHolder;
import com.example.tfg_joseangel.RecyclerView.ListaComponentesAdapter;
import com.example.tfg_joseangel.RecyclerView.ListaComponentesFirebaseUIAdapter;
import com.example.tfg_joseangel.clases.Componente;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MostrarProducto extends AppCompatActivity {

    private FirebaseAuth Auth;

    @Override
    public void onStart() {
        super.onStart();
        Auth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = Auth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        } else {
            Toast.makeText(this, "debes autenticarte primero", Toast.LENGTH_SHORT).show();
            FirebaseUser user = Auth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    //private Context contexto = null;
    private RecyclerView rv_producto = null;
    private ListaComponentesAdapter adaptadorComponentes = null;
    private DatabaseReference myRefproductos = null;
    private DatabaseReference myRefproductos1 = null;

    //FirebaseRecyclerOptions<Componente> options = null;
    //FirebaseRecyclerAdapter<Componente, ComponenteFirebaseUIViewHolder> adapter;
    private ArrayList<Componente> componentes;
    private EditText edt_buscar;
    public static int PETICION1 = 1;

    /*@Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_producto);
        rv_producto = (RecyclerView) findViewById(R.id.rv_producto);
        edt_buscar = (EditText) findViewById(R.id.edt_buscar);
        //-------------------------------------------------------------
        Auth = FirebaseAuth.getInstance();
        componentes = new ArrayList<Componente>();
        //-----------------------------------------------------------
        adaptadorComponentes = new ListaComponentesAdapter(this,componentes);
        rv_producto.setAdapter(adaptadorComponentes);

        myRefproductos = FirebaseDatabase.getInstance().getReference("compshashmap");
        //Query query = databaseReference.child("productoshashmap")
        //options = new FirebaseRecyclerOptions.Builder<Alumno>().setQuery(query, Alumno.class).build();
        //adapter = new ListaAlumnosFirebaseUIAdapter(options, this);
        myRefproductos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                adaptadorComponentes.getComponentes().clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Componente c = (Componente) dataSnapshot.getValue(Componente.class);
                    componentes.add(c);
                    adaptadorComponentes.setComponentes(componentes);
                    adaptadorComponentes.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i( "firebase1", String.valueOf(error.toException()));
            }
        });

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            rv_producto.setLayoutManager(new GridLayoutManager(this,2));
        } else {
            // In portrait
            rv_producto.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public void addProducto(View view) {
        Intent intent = new Intent(this, nuevo_producto.class);
        startActivity(intent);
    }
    public void buscarAlumnos1(View view) {

        String texto = String.valueOf(edt_buscar.getText());
        myRefproductos1 = FirebaseDatabase.getInstance().getReference("compshashmap");
        myRefproductos1.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> keys1 = new ArrayList<String>();
                ArrayList<Componente> componentes1 = new ArrayList<Componente>();
                for (DataSnapshot keynode : snapshot.getChildren()) {
                    keys1.add(keynode.getKey());
                    Componente c = keynode.getValue(Componente.class);
                    if(c.getNombre().contains(texto)) {
                        componentes1.add(keynode.getValue(Componente.class));
                    }
                }
                adaptadorComponentes.setComponentes(componentes1);
                adaptadorComponentes.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i( "firebase1", String.valueOf(error.toException()));
            }
        });

    }
    //---------------------------------------------------------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PETICION1 && resultCode == Activity.RESULT_OK) {
            int posicion = data.getIntExtra(DetallesProductoActivity.EXTRA_POSICION_DEVUELTA,-1);
            String tipo = data.getStringExtra(DetallesProductoActivity.EXTRA_TIPO);
            if(tipo.equalsIgnoreCase("edicion"))
            {
                adaptadorComponentes.notifyItemChanged(posicion);
                adaptadorComponentes.notifyDataSetChanged();
            }
            else if(tipo.equalsIgnoreCase("borrado"))
            {
                adaptadorComponentes.notifyItemRemoved(posicion);
                adaptadorComponentes.notifyDataSetChanged();
            }
            else{
                adaptadorComponentes.notifyDataSetChanged();
            }
            // this.recreate();
            //  getWindow().getDecorView().findViewById(android.R.id.content).invalidate();
        }
    }
}