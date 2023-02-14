package com.example.tfg_joseangel.utilidades;

import static com.example.tfg_joseangel.utilidades.ImagenesBlobBitmap.bytes_to_bitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.tfg_joseangel.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ImagenesFirebase {

    public static void subirFoto( String nombre_carpeta, String nombre, ImageView img_add_foto) {
        //----------- convierto el imageView a Bitmap
        img_add_foto.setDrawingCacheEnabled(true);
        img_add_foto.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) img_add_foto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        // StorageReference imagesRef = storageRef.child("imagenes");
        StorageReference foto2Ref = storageRef.child(nombre_carpeta+"/"+String.valueOf(nombre)+".png");
        UploadTask uploadTask = foto2Ref.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.i("firebase1","la foto no se ha subido correctamente");

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Log.i("firebase1","la foto se ha subido correctamente");
                // ...
            }
        });
    }

    public static void descargarFoto(String nombre_carpeta, String nombre, ImageView imagendescargada) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(nombre_carpeta+"/"+nombre+".png");

        final long tam_foto = 10240 * 1024; // tamaño máximo de la descarga de la imagen, si es mayor la descarga falla.
        islandRef.getBytes(tam_foto).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Log.i("firebase1","la foto se ha descargado correctamente");
                Bitmap imagenbitmapdescargada = bytes_to_bitmap(bytes, 200, 200);
                imagendescargada.setImageBitmap(imagenbitmapdescargada);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                byte[] bytes = null;
                Log.i("firebase1","la foto no se pudo descargar");
                int errorCode = ((StorageException) exception).getErrorCode();
                String errorMessage = exception.getMessage();
                Log.i("firebase1",errorMessage);
                Log.i("firebase1","error code" + String.valueOf(errorCode));
                //imagendescargada.setImageResource(R.drawable.caja);
            }
        });
    }
    //--------------------------------------------------------------------------------
    public static void borrarFoto(String nombre_carpeta, String foto) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(nombre_carpeta + "/"+ foto + ".png");
        // StorageReference islandRef = storageRef.child(nombre_carpeta);

        // Delete the file
        islandRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
                Log.i("firebase1","foto borrada correctamente");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
                Log.i("firebase1","la foto no se pudo borrar");
            }
        });
    }
}
