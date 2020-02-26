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

public class RecyclerViewProductos extends RecyclerView.Adapter<RecyclerViewProductos.PaletteViewHolderVenta> implements View.OnClickListener {
    ArrayList<ProductosDatos> listProductos;


    public RecyclerViewProductos(@NonNull ArrayList<ProductosDatos> productos) {
        this.listProductos = productos;
    }

    @NonNull
    @Override
    public PaletteViewHolderVenta onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View producto = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_rowsproductoscs, viewGroup, false);
        return new  PaletteViewHolderVenta(producto);
    }

    @Override
    public void onBindViewHolder(@NonNull PaletteViewHolderVenta paletteViewHolderVenta, int i) {
        paletteViewHolderVenta.bind(listProductos.get(i));

    }

    @Override
    public int getItemCount() {
        return listProductos.size();
    }

    @Override
    public void onClick(View view) {

    }


    class PaletteViewHolderVenta extends RecyclerView.ViewHolder {
        public TextView nombreProducto;
        public ImageView imagenProducto;
        public TextView clasificacion;


        public PaletteViewHolderVenta(View itemView) {
            super(itemView);
            nombreProducto = (TextView) itemView.findViewById(R.id.nombre);
            imagenProducto =  itemView.findViewById(R.id.imagen);
            clasificacion = (TextView) itemView.findViewById(R.id.clasi);


        }

        public void bind(ProductosDatos productos) {
            nombreProducto.setText(productos.getNombreProducto());
            //imagenProducto.setText(productos.getImagenProductoe());
            imagenProducto.setImageBitmap(productos.getImagenProductoe());
            clasificacion.setText(productos.getClasificacion());

        }
    }
}
