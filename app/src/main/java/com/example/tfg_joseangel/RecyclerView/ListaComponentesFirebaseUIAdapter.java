package com.example.tfg_joseangel.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.tfg_joseangel.R;
import com.example.tfg_joseangel.clases.Componente;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ListaComponentesFirebaseUIAdapter extends FirebaseRecyclerAdapter<Componente, ComponenteFirebaseUIViewHolder> {

    private Context contexto;

    public ListaComponentesFirebaseUIAdapter(@NonNull FirebaseRecyclerOptions<Componente> options, Context contexto) {
        super(options);
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public ComponenteFirebaseUIViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_componente, parent, false);
        ComponenteFirebaseUIViewHolder evh = new ComponenteFirebaseUIViewHolder(mItemView, contexto);
        return null;
    }

    @Override
    protected void onBindViewHolder(@NonNull ComponenteFirebaseUIViewHolder holder, int position, @NonNull Componente model) {
        Componente c = (Componente) model;
        holder.getTxt_item_nombre();
        holder.getTxt_item_marca();
        holder.getTxt_item_precio();
        holder.getTxt_item_stock();
    }

    @Override
    public void onDataChanged(){
        super.onDataChanged();
        notifyDataSetChanged();
    }

}
