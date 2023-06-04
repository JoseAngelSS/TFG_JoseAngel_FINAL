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
import com.example.tfg_joseangel.clases.Venta;
import com.example.tfg_joseangel.utilidades.ImagenesFirebase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ListaVentasAdapter extends RecyclerView.Adapter<VentaViewHolder>{

    private Context contexto = null;
    private ArrayList<Venta> ventas = null;
    private LayoutInflater inflate = null;

    private FirebaseAuth Auth;

    public ListaVentasAdapter(Context contexto, ArrayList<Venta> ventas ) {
        this.contexto = contexto;
        this.ventas = ventas;
        inflate =  LayoutInflater.from(this.contexto);
    }
    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }

    public void setComponentes(ArrayList<Venta> ventas) {
        this.ventas = ventas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VentaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = inflate.inflate(R.layout.item_rv_ventas,parent,false);
        VentaViewHolder evh = new VentaViewHolder(mItemView,this);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull VentaViewHolder holder, int position) {
        Venta v = this.getVentas().get(position);
        //        cargo la imagen desde la base de datos
        //-----------------------------------------------------------------
        String carpeta = v.getRef();
        ImageView imagen = holder.getImg_item_prod();
        ImagenesFirebase.descargarFoto(carpeta,v.getRef(),imagen);
        ImageView imagen1 = imagen;
        holder.setImg_item_prod(imagen);
        //----------------------------------------------------------------------
        holder. getTxt_ref_rv().setText("Referencia: " + v.getRef());
        holder.getTxt_idComp_rv().setText("Id Componente: " + v.getIdCompvend());
        holder.getTxt_udvend_rv().setText("Cantidad: " + v.getNunidad());
    }

    @Override
    public int getItemCount() {
        return this.ventas.size();
    }

    public void addVenta(Venta ventaAñadida) {
        ventas.add(ventaAñadida);
        notifyDataSetChanged();
    }
}
