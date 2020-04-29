package com.example.comandera.modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.comandera.R;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {


  private Context context;
  private  ArrayList<String> arrayList;
  private int imagens[];

  public  GridAdapter(Context context, int imagens[], ArrayList<String> arrayList){
    this.context = context;
    this.imagens = imagens;
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

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null){
      LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
      convertView = layoutInflater.inflate(R.layout.item_grid, null);
    }

    TextView tituloTv = (TextView) convertView.findViewById(R.id.ig_tv_titulo);
    tituloTv.setText(arrayList.get(position));

    ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView2);
    imageView.setImageResource(imagens[position]);


    return convertView;
  }
}
