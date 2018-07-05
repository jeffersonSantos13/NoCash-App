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


public class HomeListview extends ArrayAdapter<String> {

    private String[] nomeprod;
    private String[] proddesc;
    private Integer[] imgid;
    private Activity context;

    public HomeListview(Activity context, String[] nomeprod, String[] proddesc, Integer[] imgid) {
       super(context, R.layout.layoutlista,nomeprod);

        this.context = context;
        this.nomeprod = nomeprod;
        this.proddesc = proddesc;
        this.imgid = imgid;
}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
            ViewHolder viewHolder = null;
                if (r == null){

                    LayoutInflater layoutInflater = context.getLayoutInflater();
                    r= layoutInflater.inflate(R.layout.layoutlista, null , true);
                    viewHolder = new ViewHolder(r);
                    r.setTag(viewHolder);

                }else{
                    viewHolder = (ViewHolder) r.getTag();
                }

                viewHolder.txtprod.setText(nomeprod[position]);

                viewHolder.imgprod.setImageResource(imgid[position]);


        return r;
    }

    class ViewHolder {
        TextView txtprod;
        TextView txtdesc;
        ImageView imgprod;
        ViewHolder(View v){

            txtprod = (TextView) v.findViewById(R.id.nomeprod);

            imgprod = (ImageView) v.findViewById(R.id.imgitem);
        }

    }

}
