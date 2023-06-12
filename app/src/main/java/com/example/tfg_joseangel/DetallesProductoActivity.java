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

import com.example.tfg_joseangel.RecyclerView.ComponenteViewHolder;
import com.example.tfg_joseangel.clases.Componente;
import com.example.tfg_joseangel.clases.Venta;
import com.example.tfg_joseangel.utilidades.ImagenesBlobBitmap;
import com.example.tfg_joseangel.utilidades.ImagenesFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class DetallesProductoActivity extends AppCompatActivity {

    public static final String EXTRA_POSICION_DEVUELTA =  "com.example.tfg_joseangel.detallesproductoactivity.posicion";
    public static final String EXTRA_TIPO = "com.example.tfg_joseangel.detallesproductoactivity.tipo";

    public
    EditText edt_det_nombreproducto = null;
    EditText edt_det_preciocomp = null;
    EditText edt_det_marca = null;
    EditText edt_det_stock = null;
    EditText edt_det_ref = null;
    EditText edt_det_udvend = null;

    EditText edt_det_idventa = null;
    String id_previo = "";
    int posicion = -1;
    public static final int NUEVA_IMAGEN = 1;
    Uri imagen_seleccionada = null;
    ImageView img_det_caja = null;
    private FirebaseAuth Auth;
    private int stock;
    private int udvend;

    @Override
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
        setContentView(R.layout.activity_detalles_producto);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        edt_det_nombreproducto = (EditText) findViewById(R.id.edt_det_nombreproducto);
        edt_det_marca = (EditText) findViewById(R.id.edt_det_marca);
        edt_det_preciocomp = (EditText) findViewById(R.id.edt_det_preciocomp);
        edt_det_stock = (EditText) findViewById(R.id.edt_det_stock);
        edt_det_ref = (EditText) findViewById(R.id.edt_det_ref);
        edt_det_udvend = (EditText) findViewById(R.id.edt_det_udvendDP);
        edt_det_idventa = (EditText) findViewById(R.id.edt_det_idventa);
        img_det_caja = (ImageView) findViewById(R.id.img_det_caja);

        Intent intent = getIntent();
        if(intent != null){
            Componente c = (Componente)intent.getSerializableExtra(ComponenteViewHolder.EXTRA_PRODUCTO_ITEM);
            edt_det_nombreproducto.setText(c.getNombre());
            edt_det_preciocomp.setText(c.getPrecio());
            edt_det_marca.setText(c.getIdMar());
            edt_det_stock.setText(c.getCantidad());
            stock = Integer.valueOf(c.getCantidad());
            edt_det_ref.setText(c.getIdComp());
            id_previo = c.getIdComp();

            //cargar foto
            byte[] fotobinaria = (byte[]) intent.getByteArrayExtra(ComponenteViewHolder.EXTRA_PRODUCTO_IMAGEN);
            Bitmap fotobitmap = ImagenesBlobBitmap.bytes_to_bitmap(fotobinaria, 200,200);
            img_det_caja.setImageBitmap(fotobitmap);
            // obtengo la posicion
            posicion = intent.getIntExtra(ComponenteViewHolder.EXTRA_POS,-1);
        }
    }
    //metodos para cambiar la imagen

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
                img_det_caja.setImageBitmap(bitmap);

                //---------------------------------------------

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //metodo para borrar Componente
    public void detalles_borrar_componente(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        String idComp = String.valueOf(edt_det_ref.getText());
        String nombre = String.valueOf(edt_det_nombreproducto.getText());
        String precio = String.valueOf(edt_det_preciocomp.getText());
        String cantidad = String.valueOf(edt_det_stock.getText());
        String marca = String.valueOf(edt_det_marca.getText());

        Componente c = new Componente(idComp, nombre,  precio, cantidad, marca);
        //--------------------------------------------
        if(id_previo.equalsIgnoreCase(idComp))
        {
            myRef.child("compshashmap").child(id_previo).removeValue();
            Toast.makeText(this,"componente eliminado correctamente",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"error al borrar",Toast.LENGTH_LONG).show();
        }
        // borramos la imagen del firebase store
        String carpeta = c.getNombre();
        ImagenesFirebase.borrarFoto(carpeta,c.getNombre());
        // cerramos la ventana y volvemos al recyclerview
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_POSICION_DEVUELTA, posicion);
        replyIntent.putExtra(EXTRA_TIPO, "borrado");
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    //metodo para editar el componente
    public void detalles_editar_componente(View view) {
        String nombre = String.valueOf(edt_det_nombreproducto.getText());
        String precio = String.valueOf(edt_det_preciocomp.getText());
        String marca = String.valueOf(edt_det_marca.getText());
        String cantidad = String.valueOf(edt_det_stock.getText());
        String idComp = String.valueOf(edt_det_ref.getText());

        Componente c = new Componente(idComp, nombre,  precio, cantidad, marca);
        //--------------------------------------------

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("compshashmap").child(id_previo).removeValue();
        myRef.child("compshashmap").child(c.getIdComp()).setValue(c);
        Toast.makeText(this,"PRODUCTO MODIFICADO",Toast.LENGTH_LONG).show();
        //--------------------------------------------------
        if(imagen_seleccionada != null || !id_previo.equalsIgnoreCase(c.getNombre())) {
            String carpeta = c.getNombre();
            ImagenesFirebase.borrarFoto(id_previo,id_previo);
            ImagenesFirebase.subirFoto(carpeta,c.getNombre(), img_det_caja);
        }
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_POSICION_DEVUELTA, posicion);
        replyIntent.putExtra(EXTRA_TIPO, "edicion");
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    public void vender(View view){

        udvend = Integer.valueOf(String.valueOf(edt_det_udvend.getText()));
        String nombrevendido = String.valueOf(edt_det_ref.getText());
        String cantidad = String.valueOf(edt_det_udvend.getText());
        String ref = String.valueOf(edt_det_idventa.getText());


        Venta v = new Venta(ref, nombrevendido, cantidad);

        int nuevo_stock = stock-udvend;
        if(nuevo_stock<0)
        {
            Toast.makeText(this, "No hay suficiente cantidad", Toast.LENGTH_LONG).show();
            return;
        }
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("ventashashmap").child(v.getRef()).setValue(v);
        myRef.child("compshashmap").child(v.getIdCompvend()).child("cantidad").setValue(String.valueOf(nuevo_stock));

        Toast.makeText(DetallesProductoActivity.this, "Producto añadido con éxito", Toast.LENGTH_LONG).show();

        /*if(imagen_seleccionada != null) {
            String carpeta = v.getIdCompvend();
            //ImagenesFirebase.subirFoto(carpeta,v.getIdCompvend(), image_newproduct);
        }*/
        Intent intent = new Intent(this, VentasActivity.class);
        startActivity(intent);
    }

    public void cambiar_imagen_det(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Selecciona una imagen");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, NUEVA_IMAGEN);
    }

}