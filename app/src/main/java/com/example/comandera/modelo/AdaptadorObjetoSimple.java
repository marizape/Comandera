package com.example.comandera.modelo;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comandera.R;

import java.util.LinkedList;
import java.util.List;

public class AdaptadorObjetoSimple extends RecyclerView.Adapter<AdaptadorObjetoSimple.ViewHolder>{
    private List<ObjetoSimple> datos;
    private AppCompatActivity context;
    private int resource;
    private boolean modoSeleccion;
    private SparseBooleanArray seleccionados;

    public AdaptadorObjetoSimple(AppCompatActivity context, LinkedList<ObjetoSimple> datos) {
        this.context = context;
        this.datos = datos;
        seleccionados = new SparseBooleanArray();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_rows, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ObjetoSimple os = datos.get(position);
        holder.bindView(os);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**VIEWHOLDER*/
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_texto;
        private View item;

        public ViewHolder(View itemView) {
            super(itemView);
            this.item = itemView;
        }

        public void bindView(ObjetoSimple os){
/*
            tv_texto = (TextView) item.findViewById(R.id.tv_texto);
            tv_texto.setText(os.getTexto());*/

            //Selecciona el objeto si estaba seleccionado
            if (seleccionados.get(getAdapterPosition())){
                item.setSelected(true);
            } else
                item.setSelected(false);
            /**Activa el modo de selección*/
            item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (!modoSeleccion){
                        modoSeleccion = true;
                        v.setSelected(true);
                        seleccionados.put(getAdapterPosition(), true);
                    }
                    return true;
                }
            });

            /**Selecciona/deselecciona un ítem si está activado el modo selección*/
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (modoSeleccion) {
                        if (!v.isSelected()) {
                            v.setSelected(true);
                            seleccionados.put(getAdapterPosition(), true);
                        } else {
                            v.setSelected(false);
                            seleccionados.put(getAdapterPosition(), false);
                            if (!haySeleccionados())
                                modoSeleccion = false;
                        }
                    }
                }
            });
        }
    }

    public boolean haySeleccionados() {
        for (int i = 0; i <= datos.size(); i++) {
            if (seleccionados.get(i))
                return true;
        }
        return false;
    }
    /**Devuelve aquellos objetos marcados.*/
    public LinkedList<ObjetoSimple> obtenerSeleccionados(){
        LinkedList<ObjetoSimple> marcados = new LinkedList<>();
        for (int i = 0; i < datos.size(); i++) {
            if (seleccionados.get(i)){
                marcados.add(datos.get(i));
            }
        }
        return marcados;
    }

}
