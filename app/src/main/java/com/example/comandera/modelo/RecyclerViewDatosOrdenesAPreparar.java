package com.example.comandera.modelo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comandera.R;

import java.util.ArrayList;

public class RecyclerViewDatosOrdenesAPreparar extends RecyclerView.Adapter<RecyclerViewDatosOrdenesAPreparar.PaletteViewHolderVenta>  implements View.OnClickListener {

    private View.OnClickListener listener;
    ArrayList<DatosOrdenesAPreparar> lista2;


    public RecyclerViewDatosOrdenesAPreparar(@NonNull ArrayList<DatosOrdenesAPreparar> clasificacion) { this.lista2 = clasificacion; }

    @NonNull
    @Override
    public PaletteViewHolderVenta onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View producto = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_rows_datos_ordenes_a_preparar, viewGroup, false);
        producto.setOnClickListener(this);
        return new  PaletteViewHolderVenta(producto);

    }

    @Override
    public void onBindViewHolder(@NonNull PaletteViewHolderVenta paletteViewHolderVenta, int i) {
        paletteViewHolderVenta.bind(lista2.get(i));

    }

    @Override
    public int getItemCount() {
        return lista2.size();
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
        public TextView NumeroDeMesa;
        public TextView IdOrdPreparar;
        public TextView NumDeProductos;
        public TextView EstatuAPrepar;

        public PaletteViewHolderVenta(View itemView) {
            super(itemView);
            NumeroDeMesa =  itemView.findViewById(R.id.NumeroDeMesa);
            IdOrdPreparar =  itemView.findViewById(R.id.IdOrdPreparar);
            NumDeProductos =  itemView.findViewById(R.id.NumDeProductos);
            EstatuAPrepar =  itemView.findViewById(R.id.EstatuAPrepar);

        }
        public void bind(DatosOrdenesAPreparar clasificacion) {
            NumeroDeMesa.setText(clasificacion.getNumeroDeMesa());
            IdOrdPreparar.setText(clasificacion.getIdOrdPreparar());
            NumDeProductos.setText(clasificacion.getNumDeProductos());
            EstatuAPrepar.setText(clasificacion.getEstatuAPrepar());
        }
    }
}