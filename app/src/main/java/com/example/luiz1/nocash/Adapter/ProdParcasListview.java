package com.example.luiz1.nocash.Adapter;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luiz1.nocash.R;

public class ProdParcasListview extends ArrayAdapter<String> {

    private String[] nomeprod;
    private Integer[] imgprod;
    private Activity context;

    public ProdParcasListview(Activity context, String[] nomeprod, Integer[] imgprod) {
        super(context, R.layout.layoutprodutosparceiros, nomeprod);
        this.nomeprod = nomeprod;
        this.imgprod = imgprod;
        this.context = context;
    }


    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ProdParcasListview.ViewHolder viewHolder = null;
        if (r == null){

            LayoutInflater layoutInflater = context.getLayoutInflater();
            r= layoutInflater.inflate(R.layout.layoutprodutosparceiros, null , true);
            viewHolder = new ProdParcasListview.ViewHolder(r);
            r.setTag(viewHolder);

        }else{
            viewHolder = (ProdParcasListview.ViewHolder) r.getTag();
        }

        viewHolder.txtprod.setText(nomeprod[position]);
        viewHolder.imgprod.setImageResource(imgprod[position]);


        return r;
    }

    class ViewHolder {

        TextView txtprod;
        ImageView imgprod;
        ViewHolder(View v){

            txtprod = (TextView) v.findViewById(R.id.nomeprod);
            imgprod = (ImageView) v.findViewById(R.id.imgprodparca);
        }

    }



}
