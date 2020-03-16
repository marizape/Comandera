package com.example.comandera.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comandera.Globales;
import com.example.comandera.MenuAdministrador;
import com.example.comandera.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



public class GridAdapterProductos extends BaseAdapter  {
    public  String id="";
    consultas consul= new consultas();
    private Context context;

    private ArrayList<ProductosDatos> arrayList;
    //private  ArrayList<String> arrayList;

    private View.OnClickListener listener;

    public GridAdapterProductos(Context context, ArrayList<ProductosDatos>  arrayList){
        this.context = context;

        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
/*
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
*/

    private String fecha() {
        final SimpleDateFormat fe = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        return fe.format(calendar.getTime());
    }
    private String hora() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String datetime = dateFormat.format(c.getTime());
        return datetime;
    }
    private void existe(Context contexto,String query) {
        ConexionSQLiteHelper conn;
        conn=new ConexionSQLiteHelper(contexto);
        SQLiteDatabase db = conn.getReadableDatabase();
        String id="";
        Cursor cursor2 =db.rawQuery("SELECT prepro_fk,docd_cantprod,doc_fk FROM documento_det WHERE prepro_fk ='"+query+"'" , null);
        try {
            if (cursor2 != null) {
                cursor2.moveToFirst();
                int index = 0;
                while (!cursor2.isAfterLast()) {
                    Globales.getInstance().vNumDoc2= String.valueOf( cursor2.getString(cursor2.getColumnIndex("doc_fk")));
                    Globales.getInstance().vExisteP2 = "existeElProducto";
                    index++;
                    cursor2.moveToNext();
                }
                if (index != 0) {
                    System.out.println("F  existeD" );
                }
                else
                {
                    if(id.equals("")) {
                        Globales.getInstance().vExisteP2= "noExisteElProducto";
                    }
                }
            }
        }catch(Exception e){
            Log.println(Log.ERROR,"",e.getMessage());
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_rowsproductoscs, null);
            holder = new ViewHolder();


            ProductosDatos productos= arrayList.get(position);
            final TextView tituloTv = convertView.findViewById(R.id.nombre);
            tituloTv.setText(productos.getNombreProducto());
            String v9= productos.getNombreProducto();
            final TextView precio =  convertView.findViewById(R.id.clasiP);
            precio.setText(productos.getClasificacion());
            String v3= productos.getClasificacion();
            ImageView imageView =  convertView.findViewById(R.id.imagen);
            imageView.setImageBitmap(productos.getImagenProductoe());


            TextView presentacion= convertView.findViewById(R.id.presentacionProducto);
            presentacion.setText(productos.getPresentacion());

            String presentacion1= productos.getPresentacion();


            TextView id= convertView.findViewById(R.id.idid);
            id.setText(productos.getId());
            // id= productos.getId();
            String v2= productos.getId();


            holder.botonAgregarProductoC= convertView.findViewById(R.id.botonMasP1);
            holder.botonQuitarProductoC=  convertView.findViewById(R.id.botonMenosP1);
            final View finalConvertView = convertView;




           holder.botonAgregarProductoC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ProductosDatos productos= arrayList.get(position);
                    final TextView tituloTv = finalConvertView.findViewById(R.id.nombre);
                    tituloTv.setText(productos.getNombreProducto());
                    String v9= productos.getNombreProducto();
                    final TextView precio =  finalConvertView.findViewById(R.id.clasiP);
                    precio.setText(productos.getClasificacion());
                    String v3= productos.getClasificacion();
                    ImageView imageView =  finalConvertView.findViewById(R.id.imagen);
                    imageView.setImageBitmap(productos.getImagenProductoe());

                    TextView presentacion= finalConvertView.findViewById(R.id.presentacionProducto);
                    presentacion.setText(productos.getPresentacion());
                    String pre=productos.getPresentacion();


                    TextView id= finalConvertView.findViewById(R.id.idid);
                    id.setText(productos.getId());
                    String id4= productos.getId();


                    final Ingresarsql sq = new Ingresarsql();
                    Context contexto= view.getContext();
                    final String iden=id.getText().toString();
                    String  ordet_precio= String.valueOf( precio.getText());

                    String ordet_cant="";   //cantidad de productos en el carrito
                    sq.existeCanti(contexto,id.getText().toString());
                    if(  Globales.getInstance().vExisteCantidad.equals("existeCantidad")){
                        int v= Integer.parseInt(Globales.getInstance().vCPE);
                        int ordet_cant1=  v+1;
                        ordet_cant= String.valueOf(ordet_cant1);
                    }else {
                        if(  Globales.getInstance().vExisteCantidad.equals("noExisteCantidad")) {
                            ordet_cant= "1";
                        }
                    }
                    String prepro_fk=iden; //  id del producto agregado al carrito
                    String ordet_importe =String.valueOf( precio.getText());
                    String ord_fecha=fecha();
                    String  ord_hora= hora();
                    String  ord_iva="0";//No oficial dato desconocido
                    String  ordet_observa="";//OFICIAL
                    String ord_total=String.valueOf( precio.getText());
                    String cjus_fk= Globales.getInstance().idCajaLau;

                    String est_fk= Globales.getInstance().idEstablecimientoLau;
                    sq.consultaestatus(contexto);
                    String esta_fk_ordet=String.valueOf(Globales.getInstance().idEstatusLau);//OFICIAL


                    sq.consultaestatus(contexto);
                    String esta_fk_ord=String.valueOf(Globales.getInstance().idEstatusLau);//OFICIAL

                   // String ord_folio=sq.generarFolio(contexto);
                   // String ord_folio=consul.generarFolio();

                    String ord_folio=Globales.getInstance().folioEnviar;
                   //Toast.makeText(context,"ord_folio" +ord_folio,Toast.LENGTH_LONG).show();
                    String mesa=Globales.getInstance().idMesa;

                    String mesa_fk=consul.consultarIdMesa(contexto,mesa);


                    Toast.makeText(contexto,"11111111",Toast.LENGTH_LONG).show();
                    sq.registrarVenta(contexto,ordet_cant,ordet_precio, ordet_importe, ordet_observa,prepro_fk,esta_fk_ordet,ord_folio,ord_fecha,ord_hora,ord_total,ord_iva,mesa_fk,cjus_fk,esta_fk_ord);
                    Toast.makeText(contexto,"222222222222",Toast.LENGTH_LONG).show();
                  //sq.registrarVentaProceso(ordet_cant,docd_preccom, docd_descuento,prepro_fk,ext_fk, ordet_import ,Globales.getInstance().idFolio, ord_fecha,ord_hora, ord_iva ,docd_cosind ,ordet_observa,doc_saldo,prs_fk,usr_fk,cjus_fk,est_fk,esta_fk,tpd_fk,contexto);
                    sq.contabilizaLosProdAgreCarr(contexto);

                }
            });

            holder.botonQuitarProductoC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
/*
                    /////////////////
                    ProductosDatos productos= arrayList.get(position);
                    final TextView tituloTv = finalConvertView.findViewById(R.id.nombre);
                    tituloTv.setText(productos.getNombreProducto());
                    final TextView precio =  finalConvertView.findViewById(R.id.clasiP);
                    precio.setText(productos.getClasificacion());
                    ImageView imageView =  finalConvertView.findViewById(R.id.imagen);
                    imageView.setImageBitmap(productos.getImagenProductoe());

                    TextView presentacion= finalConvertView.findViewById(R.id.presentacionProducto);
                    presentacion.setText(productos.getPresentacion());

                    TextView id= finalConvertView.findViewById(R.id.idid);
                    id.setText(productos.getId());

                    // id= productos.getId();
                    /////////////////


                    final Ingresarsql sq = new Ingresarsql();
                    Context contexto= view.getContext();
                    final String iden=id.getText().toString();
                    String  ordet_precio= String.valueOf( precio.getText());;
                    String prepro_fk2=iden;
                    String docd_cantprod2="";   //cantidad de productos en el carrito
                    String docd_precven2=ordet_precio;
                    sq.existeCanti(contexto,id.getText().toString());

                    if(  Globales.getInstance().vExisteCantidad.equals("existeCantidad")){
                        int v = Integer.parseInt(Globales.getInstance().vCPE);
                        if(v==1){
                            existe(contexto,prepro_fk2);
                            sq.elementosExistentesEnDDe(contexto);
                            int elem= Integer.parseInt(Globales.getInstance().elementosExistentesEnDD);

                            if  (elem>1){
                                sq.ActualizarVentaEli(contexto,prepro_fk2);//elimino el producto  de la tabla detalle
                            }

                            if(elem==1){
                                String doc_id= Globales.getInstance().idDocUl;
                                //solo elimina el producto que se le esta restanodo
                                sq.ActualizarV(contexto,prepro_fk2,doc_id);//elimino el producto  de la tabla detalle
                            }
                        }
                        else {
                            int docd_cantprod1 = v - 1;
                            docd_cantprod2 = String.valueOf(docd_cantprod1);
                            sq.ActualizarVenta(contexto,prepro_fk2, docd_cantprod2, docd_precven2);
                        }
                    }else {
                        if(  Globales.getInstance().vExisteCantidad.equals("noExisteCantidad")) {

                            System.out.println("El producto no ha sido insertado no hay necesidad de eliminar "+ docd_cantprod2);
                        }
                    }
                    sq.contabilizaLosProdAgreCarr(contexto);*/
                }
            });
            convertView.setTag(holder);
        }
        return convertView;
    }

    class ViewHolder {
        ImageButton botonAgregarProductoC;
        ImageButton botonQuitarProductoC;
    }


}