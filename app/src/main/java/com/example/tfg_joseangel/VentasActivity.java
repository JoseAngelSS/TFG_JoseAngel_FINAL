package com.example.tfg_joseangel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tfg_joseangel.RecyclerView.ListaComponentesAdapter;
import com.example.tfg_joseangel.RecyclerView.ListaVentasAdapter;
import com.example.tfg_joseangel.clases.Componente;
import com.example.tfg_joseangel.clases.Venta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VentasActivity extends AppCompatActivity {

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

    private RecyclerView rv_venta = null;
    private EditText edt_buscarVenta = null;
    private ListaVentasAdapter adaptadorVentas = null;
    private DatabaseReference myRefventas = null;
    private DatabaseReference myRefventas1 = null;

    private ArrayList<Venta> ventas;

    public static int PETICION1 = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);
        rv_venta = (RecyclerView) findViewById(R.id.rv_venta);
        edt_buscarVenta = (EditText) findViewById(R.id.edt_buscarVenta);
        //-------------------------------------------------------------
        Auth = FirebaseAuth.getInstance();
        ventas = new ArrayList<Venta>();
        //-----------------------------------------------------------
        adaptadorVentas = new ListaVentasAdapter(this, ventas);
        rv_venta.setAdapter(adaptadorVentas);

        myRefventas = FirebaseDatabase.getInstance().getReference("ventashashmap");
        myRefventas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                adaptadorVentas.getVentas().clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Venta v = (Venta) dataSnapshot.getValue(Venta.class);
                    ventas.add(v);
                }
                adaptadorVentas.setComponentes(ventas);
                adaptadorVentas.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("firebase1", String.valueOf(error.toException()));
            }

        });

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            rv_venta.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            // In portrait
            rv_venta.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public void buscarventa(View view) {
        String texto = String.valueOf(edt_buscarVenta.getText());
        myRefventas1= FirebaseDatabase.getInstance().getReference("ventashashmap");
        myRefventas1.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> keys1 = new ArrayList<String>();
                ArrayList<Venta> ventas1 = new ArrayList<Venta>();
                for (DataSnapshot keynode : snapshot.getChildren()) {
                    keys1.add(keynode.getKey());
                    Venta v = keynode.getValue(Venta.class);
                    if (v.getRef().contains(texto)) {
                        ventas1.add(keynode.getValue(Venta.class));
                    }
                }
                adaptadorVentas.setComponentes(ventas1);
                adaptadorVentas.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("firebase1", String.valueOf(error.toException()));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PETICION1 && resultCode == Activity.RESULT_OK) {
            int posicion = data.getIntExtra(DetalleVentaActivity.EXTRA_POSICION_DEVUELTA,-1);
            String tipo = data.getStringExtra(DetalleVentaActivity.EXTRA_TIPO);
            if(tipo.equalsIgnoreCase("edicion"))
            {
                adaptadorVentas.notifyItemChanged(posicion);
                adaptadorVentas.notifyDataSetChanged();
            }
            else if(tipo.equalsIgnoreCase("borrado"))
            {
                adaptadorVentas.notifyItemRemoved(posicion);
                adaptadorVentas.notifyDataSetChanged();
            }
            else{
                adaptadorVentas.notifyDataSetChanged();
            }
        }
    }

    public void volver(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}