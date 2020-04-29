package com.example.comandera.modelo;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comandera.Globales;
import com.example.comandera.R;

import java.util.ArrayList;

public class ProductoAdapterCocinero extends RecyclerView.Adapter<ProductoAdapterCocinero.PaletteViewHolderVenta>{
    ArrayList<ProductossCocinero> productosC;
    private MiListenerCocinero listener;
    private Context context;
    consultas consul= new consultas();


    public interface MiListenerCocinero {
        void onItemClick(ProductossCocinero productos, EditText cantidadescribir);
        void onItemClickMenos(ProductossCocinero productos);
        void cambiacant(String cadena, int posicion);
    }

    public ProductoAdapterCocinero(MiListenerCocinero l, ArrayList<ProductossCocinero> pro) {
        listener = l;
        productosC = pro;
    }
    public ProductoAdapterCocinero(@NonNull ArrayList<ProductossCocinero> productosC) { this.productosC = productosC; }

    @NonNull
    @Override
    public PaletteViewHolderVenta onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View productos = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_numero_rows_cocinero, viewGroup, false);
        return new PaletteViewHolderVenta(productos);
    }

    @Override
    public void onBindViewHolder(@NonNull PaletteViewHolderVenta paletteViewHolderVenta, int i) {
        paletteViewHolderVenta.bind(productosC.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return productosC.size();
    }

    class PaletteViewHolderVenta extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public TextView nombreProd;
        public EditText cantEdit;
        public TextView precio;
        public TextView presentacionP;


        public PaletteViewHolderVenta(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgProduct);
            nombreProd = (TextView) itemView.findViewById(R.id.txtProducto);
            cantEdit = (EditText) itemView.findViewById(R.id.cant2);
            cantEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
            precio = (TextView) itemView.findViewById(R.id.precio);
            presentacionP=  itemView.findViewById(R.id.presentacionProducto);
        }

        public void bind(final ProductossCocinero productos, final MiListenerCocinero listener) {

            imageView.setImageBitmap(productos.imagen);
            nombreProd.setText(productos.getNombre());
            precio.setText("$ " + Double.toString(productos.getPrecio()));
            cantEdit.setText(Integer.toString(productos.getCantidad()));
            presentacionP.setText(productos.getPresentacionP());
            //presentacionP.setText("HOLAD");
            if(productos.cantidad>0){
                cantEdit.setText(Integer.toString(productos.cantidad));
                Integer.parseInt(cantEdit.getText().toString());
            }
            else {
                cantEdit.setText("");
            }

            if (productos.cantidad<=0){
                cantEdit.setTextColor(Color.parseColor("#F48D12"));
            }

            else
            {
                cantEdit.setTextColor(Color.parseColor("#F48D12"));
            }

            cantEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    listener.cambiacant(s.toString(),getAdapterPosition());
                }
            });
/*
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onItemClick(productos, cantEdit);
                    notifyItemChanged(getAdapterPosition());
                }
            });*/

            itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemClickMenos(productos);
                    notifyItemChanged(getAdapterPosition());
                    return false;
                }
            });
        }
    }
}