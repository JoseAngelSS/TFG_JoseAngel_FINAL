package com.example.tfg_joseangel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tfg_joseangel.RecyclerView.VentaViewHolder;
import com.example.tfg_joseangel.clases.Venta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetalleVentaActivity extends AppCompatActivity {

    public static final String EXTRA_POSICION_DEVUELTA =  "com.example.tfg_joseangel.detalleventaactivity.posicion";
    public static final String EXTRA_TIPO = "com.example.tfg_joseangel.detalleventaactivity.tipo";

    EditText edt_det_idcomp = null;
    EditText edt_det_cantvend= null;
    EditText edt_det_refventa = null;

    int posicion = -1;
    String id_previo = "";

    private FirebaseAuth Auth;

    public void onStart() {
        super.onStart();
        Auth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = Auth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(this, "debes autenticarte primero", Toast.LENGTH_SHORT).show();
            FirebaseUser user = Auth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_venta);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

       edt_det_refventa = (EditText) findViewById(R.id.edt_det_refventa);
       edt_det_idcomp = (EditText) findViewById(R.id.edt_det_idcomp);
       edt_det_cantvend = (EditText) findViewById(R.id.edt_det_stock);

        Intent intent = getIntent();
        if(intent != null){
            Venta v = (Venta)intent.getSerializableExtra(VentaViewHolder.EXTRA_VENTA_ITEM);
            edt_det_refventa.setText(v.getRef());
            edt_det_idcomp.setText(v.getIdCompvend());
            edt_det_cantvend.setText(v.getNunidad());
            id_previo = v.getRef();
        }
    }

    public void devolver(View view){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        String idComp = String.valueOf(edt_det_idcomp.getText());
        String nunidad = String.valueOf(edt_det_cantvend.getText());
        String refventa = String.valueOf(edt_det_refventa.getText());

        Venta v = new Venta(idComp, nunidad, refventa);
        //--------------------------------------------
        if(id_previo.equalsIgnoreCase(refventa))
        {
            myRef.child("ventashashmap").child(id_previo).removeValue();
            Toast.makeText(this,"venta eliminada correctamente",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"error al borrar",Toast.LENGTH_LONG).show();
        }

        // cerramos la ventana y volvemos al recyclerview
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_POSICION_DEVUELTA, posicion);
        replyIntent.putExtra(EXTRA_TIPO, "borrado");
        setResult(RESULT_OK, replyIntent);
        finish();
    }
    public void volver(View view){
        Intent intent = new Intent (this,HomeActivity.class);
        startActivity(intent);
    }
}