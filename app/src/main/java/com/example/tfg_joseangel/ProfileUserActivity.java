package com.example.tfg_joseangel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tfg_joseangel.clases.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileUserActivity extends AppCompatActivity {

    //TextView profile_name,profile_mail,profile_pass;
    EditText editarpersona, editarcorreo, editarpass;
    ImageView img_edt_user;
    Button btneditaruser,btnguardar, btnvolver;
    String pass;
    public static final int NUEVA_IMAGEN = 1;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference Ref;
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        firebaseDatabase = FirebaseDatabase.getInstance();
        Ref = firebaseDatabase.getReference("usuarios");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        btnguardar = (Button)findViewById(R.id.btnguardar);
        btneditaruser = (Button) findViewById(R.id.btneditaruser);
        btnvolver = (Button) findViewById(R.id.btn_volver);

        editarpersona = (EditText) findViewById(R.id.editarpersona);
        editarpass = (EditText) findViewById(R.id.editarpass);
        editarcorreo = (EditText) findViewById(R.id.editarcorreo);

        img_edt_user = (ImageView) findViewById(R.id.img_edt_user);

        Log.d("actuliza", "Actualizar " + pass);

        datos_logueado();
        btneditaruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                E01_edit_profile();

            }
        });
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                E02_save_profile();

                String model_editarpersona = editarpersona.getText().toString();
                String model_editarpass = editarpass.getText().toString();
                String model_editarcorreo = editarcorreo.getText().toString();

                Usuario usuario = new Usuario(model_editarpersona, model_editarpass, model_editarcorreo);

                Map<String, Object> updateProfile = new HashMap<String,Object>();
                updateProfile.put(user.getUid(),usuario);

                Ref.updateChildren(updateProfile);
            }
        });
    }

    public void datos_logueado(){

        Ref.addListenerForSingleValueEvent(new ValueEventListener() {   //objeto que escucha un valor para recuperar los datos
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {  //se recurre el bucle para obtener los datos de usuario de la bbdd
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(ds.getKey().equals(user.getUid())){  //Comprueba si la clave de la instantanea coincide con el ID autenticado, si conincide, extrae los datos
                        editarpersona.setText(ds.child("nombre").getValue().toString());
                        editarcorreo.setText(ds.child("email").getValue().toString());
                        editarpass.setText(ds.child("password").getValue().toString());

                        break;
                    }else{  //Si la clave no coincide, se imprime un mensaje de registro en la consola
                        Log.d("facil", "no entro");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void cambiar_imagen_perfil(View view){
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Selecciona una imagen");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        startActivityForResult(chooserIntent, NUEVA_IMAGEN);
    }

    public void E01_edit_profile(){
        editarpersona.setEnabled(true);
        editarcorreo.setEnabled(true);
        editarpass.setEnabled(true);
        btneditaruser.setVisibility(View.INVISIBLE);
        btnguardar.setVisibility(View.VISIBLE);
    }

    public void E02_save_profile() {
        editarpersona.setEnabled(false);
        editarcorreo.setEnabled(false);
        editarpass.setEnabled(false);
        btneditaruser.setVisibility(View.VISIBLE);
        btnguardar.setVisibility(View.INVISIBLE);
    }

    public void volver_menu(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}