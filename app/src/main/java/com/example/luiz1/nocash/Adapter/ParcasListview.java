package com.example.luiz1.nocash.Adapter;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luiz1.nocash.R;

public class ParcasListview extends ArrayAdapter<String>{

    private String[] nomeparca;
    private String[] descricaoparca;
    private Integer[] imgparca;
    private Activity context;

    public ParcasListview(Activity context, String[] nomeparca, String[] descricaoparca, Integer[] imgparca) {
        super(context,R.layout.layout_parcas,nomeparca);

        this.context = context;
        this.nomeparca = nomeparca;
        this.descricaoparca = descricaoparca;
        this.imgparca = imgparca;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View r = convertView;

        ParcasListview.ViewHolder viewHolder = null;
        if (r == null){

            LayoutInflater layoutInflater = context.getLayoutInflater();
            r= layoutInflater.inflate(R.layout.layout_parcas, null , true);
            viewHolder = new ParcasListview.ViewHolder(r);
            r.setTag(viewHolder);

        }else{
            viewHolder = (ParcasListview.ViewHolder) r.getTag();
        }

        viewHolder.txtnomeparca.setText(nomeparca[position]);
//        viewHolder.txtdescricaoparca.setText(descricaoparca[position]);
        viewHolder.imgparca.setImageResource(imgparca[position]);

        return r;
    }
    class ViewHolder {
        TextView txtnomeparca;
        TextView txtdescricaoparca;
        ImageView imgparca;
        ViewHolder(View v){

             txtnomeparca = (TextView) v.findViewById(R.id.nomeprod);
        //    txtdescricaoparca = (TextView) v.findViewById(R.id.descparca);
            imgparca = (ImageView) v.findViewById(R.id.imgprodparca);
        }

    }
}
