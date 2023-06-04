package com.example.tfg_joseangel.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_joseangel.DetallesProductoActivity;
import com.example.tfg_joseangel.MostrarProducto;
import com.example.tfg_joseangel.R;
import com.example.tfg_joseangel.clases.Componente;
import com.example.tfg_joseangel.utilidades.ImagenesBlobBitmap;

public class ComponenteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public static final String EXTRA_PRODUCTO_ITEM = "com.tfg_joseangel.componenteviewholder.componente";
    public static final String EXTRA_PRODUCTO_IMAGEN = "com.tfg_joseangel.componenteviewholder.imagencomponente";
    public static final String EXTRA_POS = "com.tfg_joseangel.componenteviewholder.posicion";

    private TextView txt_item_nombre;
    private TextView txt_marca_item;
    private TextView txt_stock_item;
    private TextView txt_precio_item;
    private TextView txt_ref_item;
    private ImageView img_item_prod;

    private ListaComponentesAdapter lca;
    private Context contexto;

    public ImageView getImg_item_prod() {
        return img_item_prod;
    }

    public void setImg_item_prod(ImageView img_item_prod) {
        this.img_item_prod = img_item_prod;
    }

    public ComponenteViewHolder(@NonNull View itemView, ListaComponentesAdapter listaComponentesAdapter) {
        super(itemView);

        txt_item_nombre = (TextView) itemView.findViewById(R.id.txt_item_nombre);
        txt_precio_item = (TextView) itemView.findViewById(R.id.txt_precio_item);
        txt_stock_item = (TextView) itemView.findViewById(R.id.txt_stock_item);
        txt_marca_item = (TextView) itemView.findViewById(R.id.txt_marca_item);
        txt_ref_item = (TextView) itemView.findViewById(R.id.txt_ref_item);

        img_item_prod = (ImageView) itemView.findViewById(R.id.img_item_prod);
        //-----------------------------------------------------------------------------
        lca = listaComponentesAdapter;
        itemView.setOnClickListener(this);
    }

    public TextView getTxt_item_nombre() {
        return txt_item_nombre;
    }
    public void setTxt_item_nombre(TextView txt_item_nombre) {this.txt_item_nombre = txt_item_nombre;}

    public TextView getTxt_item_marca() {return txt_marca_item;}
    public void setTxt_item_marca(TextView txt_marca_item) {
        this.txt_marca_item = txt_marca_item;
    }

    public TextView getTxt_item_stock() {
        return txt_stock_item;
    }
    public void setTxt_stock_item(TextView txt_stock_item) {
        this.txt_stock_item = txt_stock_item;
    }

    public TextView getTxt_item_precio(){return txt_precio_item;}
    public void setTxt_precio_item(TextView txt_precio_item){this.txt_precio_item = txt_precio_item;}

    public TextView getTxt_ref_item(){return txt_ref_item;}
    public void setTxt_ref_item(TextView txt_ref_item){this.txt_ref_item = txt_ref_item;}

    public ListaComponentesAdapter getLpa() {
        return lca;
    }

    public void setLca(ListaComponentesAdapter lca) {
        this.lca = lca;
    }

    @Override
    public void onClick(View view) {
        int posicion = getLayoutPosition();
        Componente c = lca.getComponentes().get(posicion);
        Intent intent = new Intent(lca.getContexto(), DetallesProductoActivity.class);
        intent.putExtra(EXTRA_PRODUCTO_ITEM,c);
        img_item_prod.buildDrawingCache();
        Bitmap foto_bm = img_item_prod.getDrawingCache();
        intent.putExtra(EXTRA_PRODUCTO_IMAGEN, ImagenesBlobBitmap.bitmap_to_bytes_png(foto_bm));
        intent.putExtra(EXTRA_POS, posicion);
        //  lpa.getContexto().startActivity(intent);
        Context contexto = lca.getContexto();
        ((MostrarProducto) contexto).startActivityForResult(intent, MostrarProducto.PETICION1);
    }
}
