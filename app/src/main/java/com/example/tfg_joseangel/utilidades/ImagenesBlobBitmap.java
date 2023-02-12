package com.example.tfg_joseangel.utilidades;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.SQLException;

public class ImagenesBlobBitmap {
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    // la forma de utilizarlo sería:
    // imageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.id.myimage, 100, 100));
    // mas información aquí: -> https://developer.android.com/topic/performance/graphics/load-bitmap#java


    public static Bitmap decodeSampledBitmapFrombyteArray(byte[] fotob, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(fotob, 0, fotob.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(fotob, 0, fotob.length, options);
    }
//---------------------------------------------------------------------------------------------------------------------

    //método que convierte un blob a un Bitmap sin comprimir
    public static Bitmap blob_to_bitmap(Blob b){
        Bitmap  bitmap = null;
        try{

            int blobLength = (int) b.length();
            byte[] blobAsBytes = b.getBytes(1, blobLength);
            bitmap = BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length);
        }catch (Exception e){
            return null;
        }
        return bitmap;
    }

    //método que convierte un blob a byte[] sin comprimir
    public static byte[] blob_to_bytes(Blob b){
        int blobLength = 0;
        byte[] blobAsBytes = new byte[0];
        try {
            blobLength = (int) b.length();
            blobAsBytes = b.getBytes(1, blobLength);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return blobAsBytes;
    }

    //método que convierte byte[] a bitmap
    public static Bitmap bytes_to_bitmap(byte[] b, int width, int height){
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = Bitmap.createBitmap(width, height,config);
        try{
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        } catch (Exception e){
        }
        return bitmap;
    }

    public static byte[] bitmap_to_bytes_png(Bitmap foto_bitmap)
    {
        if(foto_bitmap != null)
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            foto_bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            return bArray;
        }
        return null;
    }
}
