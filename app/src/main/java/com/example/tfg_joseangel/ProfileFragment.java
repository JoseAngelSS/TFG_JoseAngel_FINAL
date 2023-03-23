package com.example.tfg_joseangel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfg_joseangel.clases.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    TextView profile_name,profile_mail,profile_pass;
    EditText editarpersona, editarcorreo, editarpass;
    ImageView img_edt_user;
    Button btneditaruser,btnguardar;
    String pass;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference Ref;
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        btnguardar = root.findViewById(R.id.btnguardar);
        btneditaruser = root.findViewById(R.id.btneditaruser);

        editarpersona = root.findViewById(R.id.editarpersona);
        editarpass = root.findViewById(R.id.editarpass);
        editarcorreo = root.findViewById(R.id.editarcorreo);

        img_edt_user = root.findViewById(R.id.img_edt_user);

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

        return root;
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
}