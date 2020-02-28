package com.example.comandera.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comandera.Globales;
import com.example.comandera.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RecyclerViewLL extends RecyclerView.Adapter<RecyclerViewLL.PaletteViewHolderVenta>  {
    ArrayList<ProductosDatos> listClasificacion;
    private View.OnClickListener listener;



    public RecyclerViewLL(@NonNull ArrayList<ProductosDatos> clasificacion) {
        this.listClasificacion = clasificacion;
    }

    @NonNull
    @Override
    public PaletteViewHolderVenta onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View producto = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_rowsproductoscp, viewGroup, false);
        // producto.setOnClickListener(this);
        return new  PaletteViewHolderVenta(producto);
    }

    @Override
    public void onBindViewHolder(@NonNull PaletteViewHolderVenta paletteViewHolderVenta, int i) {
        paletteViewHolderVenta.bind(listClasificacion.get(i));
        paletteViewHolderVenta.setOnClickListener();


    }



    @Override
    public int getItemCount() {
        return listClasificacion.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {

        this.listener = listener;
    }


    class PaletteViewHolderVenta extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nombreClasificacion;
        public ImageView imagenClasificacion;
        public TextView precio;
        public  String id="";
        ImageButton botonAgregarProductoC;
        ImageButton botonQuitarProductoC;


        public PaletteViewHolderVenta(View itemView) {
            super(itemView);
            nombreClasificacion = (TextView) itemView.findViewById(R.id.nombreClasificacion2);
            imagenClasificacion =  itemView.findViewById(R.id.imagenClasificacion2);
            precio = (TextView) itemView.findViewById(R.id.clasi);
            botonAgregarProductoC= itemView.findViewById(R.id.botonMasP);
            botonQuitarProductoC= itemView.findViewById(R.id.botonMenosP);
        }

        void setOnClickListener(){
            botonAgregarProductoC.setOnClickListener(this);
            botonQuitarProductoC.setOnClickListener(this);
        }

        public void bind(ProductosDatos clasificacion) {
            nombreClasificacion.setText(clasificacion.getNombreProducto());
            //  imagenClasificacion.setText(clasificacion.getImagenProductoe());

            nombreClasificacion.getText();
            imagenClasificacion.setImageBitmap(clasificacion.getImagenProductoe());
            id= clasificacion.getId();
            precio.setText(clasificacion.getClasificacion());

        }
   //     ddMMyyyy
        private String fecha() {
           // final SimpleDateFormat fe = new SimpleDateFormat("dd/MM/yyyy");
            final SimpleDateFormat fe = new SimpleDateFormat("ddMMyyyy");
            Calendar calendar = Calendar.getInstance();
            return fe.format(calendar.getTime());
        }
        private String hora() {
            final Calendar c = Calendar.getInstance();
           // SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");

            String datetime = dateFormat.format(c.getTime());
            return datetime;
        }

        private void existe(Context contexto,String query) {
            ConexionSQLiteHelper conn;
            conn=new ConexionSQLiteHelper(contexto);
            SQLiteDatabase db = conn.getReadableDatabase();
            String id="";
            Cursor cursor2 =db.rawQuery("SELECT prd_fk,docd_cantprod,doc_fk FROM documento_det WHERE prd_fk ='"+query+"'" , null);
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
        public void onClick(View view) {
            final Ingresarsql sq = new Ingresarsql();
            Context contexto= view.getContext();
            String iden=id;
            String  precioE= String.valueOf( precio.getText());

            switch (view.getId()){
                case  R.id.botonMasP:
                    String ordet_cant="";   //cantidad de productos en el carrito
                    sq.existeCanti(contexto,id);
                    if(  Globales.getInstance().vExisteCantidad.equals("existeCantidad")){
                        int v= Integer.parseInt(Globales.getInstance().vCPE);
                        int ordet_cant1=  v+1;
                        ordet_cant= String.valueOf(ordet_cant1);
                    }else {
                        if(  Globales.getInstance().vExisteCantidad.equals("noExisteCantidad")) {
                            ordet_cant= "1";
                        }
                    }
                   // String docd_precven=precioE; // precio unitario del producto agregado.          <
                  //  String docd_preccom= precioE;  //precio de venta del producto ingresado
                      String ordet_precio=precioE;
                   // String docd_descuento= "0";  //OFICIAL descuento
                    String prd_fk=iden; //  id del producto agregado al carrito
                  //  String ext_fk= "0"; //OFICIAL


                //  String imp_fk ="0"; //OFICIAL


                    String f1=sq.generarFolio(contexto);
                  //  String f1= "123456789101";
                    System.out.println(" f1 "+ f1);
                    Globales.getInstance().folioVenta= f1;
                    Globales.getInstance().idFolio= f1;


             /*       String ord_fecha=fecha();
                    String  ord_hora= hora();
                    String  ord_iva="0";//No oficial dato desconocido
                   // String  docd_cosind ="0"; //No oficial dato desconocido            costo indirecto           <
                    String  ordet_observa="0";//OFICIAL
                   // String doc_saldo="0";//OFICIAL
                    String ord_total="0";
*/
                  //  sq.consultarIDVentaPublico(contexto);DUDa
                   // String prs_fk= String.valueOf(Globales.getInstance().idVentaalPublico);

                   // String prs_fk="545";//"Venta al público";//OFICIAL pero preguntar si solo es el id
                    //CAMBIAR
                    //String usr_fk= Globales.getInstance().id_usuario; //modificar valor  llave foranea de la tabla persona
                   //////////////////////////////////////// String usr_fk= Globales.getInstance().usuario; //modificar valor  llave foranea de la tabla persona
                   // String cja_fk= Globales.getInstance().idCajaLau; //modificar valor  llave foranea de la tabla caja
                    String est_fk= Globales.getInstance().idEstablecimientoLau; //modificar valor  llave foranea de la tabla establecimietno
                    sq.consultaestatus(contexto);
                    //String esta_fk= String.valueOf(Globales.getInstance().idEstatusLau);//OFICIAL

/*
                    String ordet_importe ="0"; //OFICIAL
                    String prepro_fk=iden; //  id del producto agregado al carrito
                    String esta_fk_ordet=String.valueOf(Globales.getInstance().idEstatusLau);//OFICIAL
                   // String ord_folio=sq.generarFolio(contexto);
                    String ord_folio="1";
                    String mesa_fk="1";
                    String cjus_fk= Globales.getInstance().idCajaLau;
                    String esta_fk_ord=String.valueOf(Globales.getInstance().idEstatusLau);//OFICIAL

*/

                    //  sq.consultartpdVenta(contexto);
                 //   String tpd_fk= String.valueOf(Globales.getInstance().idTpdVenta);
               //     sq.registrarVenta(contexto,ordet_cant,ordet_precio, ordet_importe, ordet_observa,prepro_fk,esta_fk_ordet,ord_folio,ord_fecha,ord_hora,ord_total,ord_iva,mesa_fk,cjus_fk,esta_fk_ord);
                   // sq.registrarVentaProceso(docd_cantprod,docd_precven,docd_preccom, docd_descuento,prd_fk,ext_fk, imp_fk ,Globales.getInstance().idFolio, doc_fecha,doc_hora, doc_iva ,docd_cosind ,doc_observ,doc_saldo,prs_fk,usr_fk,cja_fk,est_fk,esta_fk,tpd_fk,contexto);
                   // sq.registrarVentaProceso(ordet_cant,docd_precven,docd_preccom, docd_descuento,prd_fk,ext_fk, imp_fk ,Globales.getInstance().idFolio, ord_fecha,ord_hora, ord_iva ,ordet_observa,ord_total,est_fk,esta_fk,contexto);

                    break;

                case  R.id.botonMenosP:
                    String prd_fk2=iden;
                    String docd_cantprod2="";
                    String docd_precven2=precioE;
                    sq.existeCanti(contexto,id);

                    if(  Globales.getInstance().vExisteCantidad.equals("existeCantidad")){
                        int v = Integer.parseInt(Globales.getInstance().vCPE);
                        if(v==1){
                            existe(contexto,prd_fk2);
                           sq.elementosExistentesEnDDe(contexto);
                            int elem= Integer.parseInt(Globales.getInstance().elementosExistentesEnDD);

                            if(elem>1){
                                sq.ActualizarVentaEli(contexto,prd_fk2);//elimino el producto  de la tabla detalle
                            }

                            if(elem==1){
                                String doc_id= Globales.getInstance().idDocUl;
                                //solo elimina el producto que se le esta restanodo
                                sq.ActualizarV(contexto,prd_fk2,doc_id);//elimino el producto  de la tabla detalle
                            }
                        }
                        else {
                            int docd_cantprod1 = v - 1;
                            docd_cantprod2 = String.valueOf(docd_cantprod1);
                            sq.ActualizarVenta(contexto,prd_fk2, docd_cantprod2, docd_precven2);
                        }
                    }else {
                        if(  Globales.getInstance().vExisteCantidad.equals("noExisteCantidad")) {

                            System.out.println("El producto no ha sido insertado no hay necesidad de eliminar "+ docd_cantprod2);
                        }
                    }

                    break;



            }
        }




    }
}