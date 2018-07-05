package com.example.luiz1.nocash;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luiz1.nocash.Adapter.ContaListView;
import com.example.luiz1.nocash.Model.Movimento;
import com.example.luiz1.nocash.SQL.DatabaseTransacao;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTransacao extends Fragment {
    private static final String TAG = "TRANSACAO";
    private TextView txtdata;
    private TextView nomeprod;

    public FragmentTransacao() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_transacao, container, false);
        nomeprod = v.findViewById(R.id.nomeprod);

        // Definindo o que será passado: descrição da transação, e valor
        try {

            // Lista das transações
            DatabaseTransacao myDb = new DatabaseTransacao(getContext());
            List<Movimento> list = new ArrayList<>();
            list.addAll(myDb.getAllData());

            int size = list.size();

            String[] vtrans = new String[size];
            String[] desctrans = new String[size];
            String[] vdata = new String[size];

            for (int i = 0; i < list.size(); i++){
                vtrans[i] = new DecimalFormat("R$ ###,###,##0.00").format(list.get(i).getVlBruto());
                desctrans[i] = list.get(i).getNrDocumento();

                Date d = new Date();
                d = list.get(i).getDtMovimento();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
                String strDate = dateFormat.format(d);

                vdata[i] = strDate;
            }

            // Listener de cada item da lista
            ListView lista =  v.findViewById(R.id.listatrans);
            ContaListView contaListview = new ContaListView(getActivity(), vtrans, desctrans, vdata);
            lista.setAdapter(contaListview);


            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    FragmentTransacao frag1 = new FragmentTransacao();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.corpo, frag1);
                    fragmentTransaction.commit();


                }
            });
        } catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        
        return v;
    }

}
