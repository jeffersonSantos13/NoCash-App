package com.example.luiz1.nocash;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.luiz1.nocash.Adapter.ContaListView;

import com.example.luiz1.nocash.Model.Carteira;
import com.example.luiz1.nocash.Model.Movimento;
import com.example.luiz1.nocash.Model.Compra;
import com.example.luiz1.nocash.Model.Pagamento;
import com.example.luiz1.nocash.service.MovimentoService;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.ProgressHelper;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCompra extends Fragment {

    private Button comprar;
    private EditText txtVl;
    private EditText txtOrigem;
    private double valor;
    private int origem;

    public FragmentCompra() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_fragment_compra, container, false);

        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        //String data = currentDate.format(todayDate);





        // Listener de cada item da lista
        comprar = (Button) v.findViewById(R.id.btnsubmit);
        txtOrigem = (EditText) v.findViewById(R.id.txtCOD_USER);
        txtVl = (EditText) v.findViewById(R.id.txtVL_COMPRA);



        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                    FragmentCompra frag1 = new FragmentCompra();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.corpo, frag1);
                fragmentTransaction.commit();

                valor = Double.parseDouble(txtVl.getText().toString().trim());
                origem = Integer.parseInt(txtOrigem.getText().toString().trim());



                String descricao = "Recarga da carteira";
                Compra compra = new Compra(valor, origem, "recarga");

                Session session = new Session();
                session.SessaoComprar(getActivity(), compra);

                Intent intent = new Intent(getActivity(), CompraActivity.class);
                startActivity(intent);
            }
        });




        return v;
    }

}
