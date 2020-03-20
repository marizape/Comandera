package com.example.comandera.modelo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comandera.R;

import java.util.ArrayList;

public class RecyclerViewDatosOrdenes extends RecyclerView.Adapter<RecyclerViewDatosOrdenes.PaletteViewHolderVenta>  implements View.OnClickListener {

    private View.OnClickListener listener;
    ArrayList<datosOrdenes> lista;


    public RecyclerViewDatosOrdenes(@NonNull ArrayList<datosOrdenes> clasificacion) { this.lista = clasificacion; }

    @NonNull
    @Override
    public PaletteViewHolderVenta onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View producto = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_rows_datos_ordenes, viewGroup, false);
        producto.setOnClickListener(this);
        return new  PaletteViewHolderVenta(producto);

    }

    @Override
    public void onBindViewHolder(@NonNull PaletteViewHolderVenta paletteViewHolderVenta, int i) {
        paletteViewHolderVenta.bind(lista.get(i));

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

    class PaletteViewHolderVenta extends RecyclerView.ViewHolder {
        public TextView numMesa;
        public TextView nOrden;
        public TextView estatus;


        public PaletteViewHolderVenta(View itemView) {
            super(itemView);
            numMesa =  itemView.findViewById(R.id.numMesa);
            nOrden =  itemView.findViewById(R.id.nOrden);
              estatus =  itemView.findViewById(R.id.estatus);

        }

        public void bind(datosOrdenes clasificacion) {
            numMesa.setText(clasificacion.getNumMesa());
            nOrden.setText(clasificacion.getOrdenId());
            estatus.setText(clasificacion.getEstatus());
        }
    }
}