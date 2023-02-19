package com.example.tfg_joseangel.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_joseangel.R;
import com.example.tfg_joseangel.clases.Componente;
import com.example.tfg_joseangel.utilidades.ImagenesFirebase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ListaComponentesAdapter extends RecyclerView.Adapter<ComponenteViewHolder>{

    private Context contexto = null;
    private ArrayList<Componente> componentes = null;
    private LayoutInflater inflate = null;

    private FirebaseAuth Auth;

    public ListaComponentesAdapter(Context contexto, ArrayList<Componente> componentes ) {
        this.contexto = contexto;
        this.componentes = componentes;
        inflate =  LayoutInflater.from(this.contexto);
    }
    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }

    public ArrayList<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(ArrayList<Componente> componentes) {
        this.componentes = componentes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ComponenteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = inflate.inflate(R.layout.item_rv_componente,parent,false);
        ComponenteViewHolder evh = new ComponenteViewHolder(mItemView,this);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ComponenteViewHolder holder, int position) {
        Componente c = this.getComponentes().get(position);
        //        cargo la imagen desde la base de datos
        //-----------------------------------------------------------------
       /* String carpeta = c.getNombre();
        ImageView imagen = holder.getImg_item_prod);
        ImagenesFirebase.descargarFoto(carpeta,c.getNombre(),imagen);
        ImageView imagen1 = imagen;
        holder.setImg_item_prod(imagen);*/
        //----------------------------------------------------------------------
        holder.getTxt_item_nombre().setText("nombre: " + c.getNombre());
        holder.getTxt_item_stock().setText("stock: " + c.getCantidad());
        holder.getTxt_item_marca().setText("marca: " + c.getIdMar());
        holder.getTxt_item_precio().setText("precio: " + c.getPrecio());

    }


    @Override
    public int getItemCount() {
        return this.componentes.size();
    }

    public void addComponente(Componente componenteAñadido) {
        componentes.add(componenteAñadido);
        notifyDataSetChanged();
    }
}
