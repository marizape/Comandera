package com.example.comandera.modelo;

import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comandera.Globales;
import com.example.comandera.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewBP extends RecyclerView.Adapter<RecyclerViewBP.PaletteViewHolderVenta> implements View.OnClickListener {
    ArrayList<datosmesa> listClasificacion;
    private View.OnClickListener listener;
    consultas consul= new consultas();

    private Context contextO;

    public RecyclerViewBP(@NonNull ArrayList<datosmesa> clasificacion) {
        this.listClasificacion = clasificacion;
    }

    @NonNull
    @Override
    public PaletteViewHolderVenta onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View producto = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_rows, viewGroup, false);
        producto.setOnClickListener(this);
        this.contextO=viewGroup.getContext();
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
         public LinearLayout fondo3;



        public PaletteViewHolderVenta(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            imagen =  itemView.findViewById(R.id.imagen);
            fondo3 = itemView.findViewById(R.id.relativeLayoutM);
        }

        public void bind(datosmesa clasificacion) {
            nombre.setText(clasificacion.getNombre());
            imagen.setImageDrawable(clasificacion.getImagen());

            String r=clasificacion.getNombre();
            System.out.println(r);
            String[] var2=r.split( " ");
            String var3=var2[1];

            ConexionSQLiteHelper conn;
            conn = new ConexionSQLiteHelper( contextO);
            SQLiteDatabase db = conn.getWritableDatabase();
            final List<datosmesa> arrayList = new ArrayList<datosmesa>();

            Cursor cursor2 =db.rawQuery("SELECT mesa_num,esta_fk,mesa_id FROM mesa WHERE mesa_num='"+var3+"'", null);
            try {
                if (cursor2 != null) {
                    cursor2.moveToFirst();
                    int index = 0;
                    while (!cursor2.isAfterLast()) {
                        String mesa= String.valueOf( cursor2.getString(cursor2.getColumnIndex("mesa_num")));
                        String id= String.valueOf( cursor2.getString(cursor2.getColumnIndex("mesa_id")));
                        int estatu= Integer.parseInt(cursor2.getString(cursor2.getColumnIndex("esta_fk")));

                        if(estatu==4){//HABILITADO rojo
                            fondo3.setBackgroundColor(Color.parseColor("#7EF172"));
                        }
                        if(estatu==5){//desabilitado verde
                            fondo3.setBackgroundColor(Color.parseColor("#F04B27"));
                        }
                        index++;
                        cursor2.moveToNext();
                    }
                    if (index != 0) {


                    }
                }


            }catch(Exception e){

            }


        }




    }

}
