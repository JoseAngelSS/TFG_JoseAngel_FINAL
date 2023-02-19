package com.example.tfg_joseangel.RecyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.tfg_joseangel.DetallesProductoActivity;
import com.example.tfg_joseangel.R;
import com.example.tfg_joseangel.clases.Componente;

public class ComponenteFirebaseUIViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static final String EXTRA_PRODUCTO_ITEM = "com.componenteviewholder.componente";

    private TextView txt_item_nombre;
    private TextView txt_marca_item;
    private TextView txt_stock_item;
    private TextView txt_precio_item;

    private ListaComponentesAdapter lca;

    private Context contexto;


    public ComponenteFirebaseUIViewHolder(@NonNull View itemView, Context contexto) {
        super(itemView);
        txt_item_nombre = (TextView) itemView.findViewById(R.id.txt_item_nombre);
        txt_marca_item = (TextView) itemView.findViewById(R.id.txt_marca_item);
        txt_stock_item = (TextView) itemView.findViewById(R.id.txt_stock_item);
        txt_precio_item = (TextView) itemView.findViewById(R.id.txt_precio_item);

        //lpa = listaProductosAdapter;
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



    @Override
    public void onClick(View view) {
        Componente c = new Componente(String.valueOf(txt_item_nombre.getText()),String.valueOf(txt_precio_item.getText()),String.valueOf(txt_marca_item.getText()), String.valueOf(txt_stock_item.getText()));
        Intent intent = new Intent(this.contexto , DetallesProductoActivity.class);
        intent.putExtra(EXTRA_PRODUCTO_ITEM,c);
        this.contexto.startActivity(intent);
    }
}