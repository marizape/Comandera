package com.example.comandera.modelo;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comandera.R;

import java.util.ArrayList;

public class RecyclerViewBP extends RecyclerView.Adapter<RecyclerViewBP.PaletteViewHolderVenta> implements View.OnClickListener {
    ArrayList<datosmesa> listClasificacion;
    private View.OnClickListener listener;



    public RecyclerViewBP(@NonNull ArrayList<datosmesa> clasificacion) {
        this.listClasificacion = clasificacion;
    }

    @NonNull
    @Override
    public PaletteViewHolderVenta onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View producto = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_rows, viewGroup, false);
        producto.setOnClickListener(this);
        return new  PaletteViewHolderVenta(producto);


    }

    @Override
    public void onBindViewHolder(@NonNull PaletteViewHolderVenta paletteViewHolderVenta, int i) {
        paletteViewHolderVenta.bind(listClasificacion.get(i));

    }

    @Override
    public int getItemCount() {
        return listClasificacion.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {


        this.listener = listener;
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
        Context context=   view.getContext();
    }


    class PaletteViewHolderVenta extends RecyclerView.ViewHolder {
        public TextView nombre;
        public ImageView imagen;



        public PaletteViewHolderVenta(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            imagen =  itemView.findViewById(R.id.imagen);
        }

        public void bind(datosmesa clasificacion) {
            nombre.setText(clasificacion.getNombre());
           imagen.setImageDrawable(clasificacion.getImagen());

        }




    }

}
