package com.example.tfg_joseangel.RecyclerView;

import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_joseangel.DetalleVentaActivity;
import com.example.tfg_joseangel.MostrarProducto;
import com.example.tfg_joseangel.R;
import com.example.tfg_joseangel.VentasActivity;
import com.example.tfg_joseangel.clases.Venta;
import com.example.tfg_joseangel.utilidades.ImagenesBlobBitmap;


public class VentaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public static final String EXTRA_VENTA_ITEM = "com.tfg_joseangel.ventaviewholder.venta";
    public static final String EXTRA_VENTA_IMAGEN = "com.tfg_joseangel.ventaviewholder.imagenventa";
    public static final String EXTRA_POS_VENTA = "com.tfg_joseangel.ventaviewholder.posicion";

    private TextView txt_ref_rvI;
    private TextView txt_idComp_rvI;
    private TextView txt_udvend_rvI;
    private ImageView imgventa;

    private ListaVentasAdapter lva;
    private Context contexto;

    public ImageView getImg_item_prod() {
        return imgventa;
    }

    public void setImg_item_prod(ImageView imgventa) {
        this.imgventa = imgventa;
    }

    public VentaViewHolder(@NonNull View itemView, ListaVentasAdapter listaVentasAdapter) {
        super(itemView);

        txt_ref_rvI = (TextView) itemView.findViewById(R.id.txt_ref_rvI);
        txt_idComp_rvI = (TextView) itemView.findViewById(R.id.txt_idComp_rvI);
        txt_udvend_rvI = (TextView) itemView.findViewById(R.id.txt_udvend_rvI);

        imgventa = (ImageView) itemView.findViewById(R.id.imgventa);
        //-----------------------------------------------------------------------------
        lva = listaVentasAdapter;
        itemView.setOnClickListener(this);

    }

    public TextView getTxt_ref_rv() {
        return txt_ref_rvI;
    }

    public void setTxt_ref_rv(TextView txt_ref_rv) {
        this.txt_ref_rvI = txt_ref_rv;
    }

    public TextView getTxt_idComp_rv() {
        return txt_idComp_rvI;
    }

    public void setTxt_idComp_rv(TextView txt_idComp_rv) {
        this.txt_idComp_rvI = txt_idComp_rv;
    }

    public TextView getTxt_udvend_rv() {
        return txt_udvend_rvI;
    }

    public void setTxt_udvend_rv(TextView txt_udvend_rv) {
        this.txt_udvend_rvI= txt_udvend_rv;
    }

    public ListaVentasAdapter getLva() {
        return lva;
    }

    public void setLva(ListaVentasAdapter lva) {
        this.lva = lva;
    }

    @Override
    public void onClick(View view) {
        int posicion = getLayoutPosition();
        Venta v = lva.getVentas().get(posicion);
        Intent intent = new Intent(lva.getContexto(), DetalleVentaActivity.class);
        intent.putExtra(EXTRA_VENTA_ITEM,v);
        imgventa.buildDrawingCache();
        Bitmap foto_bm = imgventa.getDrawingCache();
        intent.putExtra(EXTRA_VENTA_IMAGEN, ImagenesBlobBitmap.bitmap_to_bytes_png(foto_bm));
        intent.putExtra(EXTRA_POS_VENTA, posicion);
        Context contexto = lva.getContexto();
        ((VentasActivity) contexto).startActivityForResult(intent, VentasActivity.PETICION1);
    }
}
