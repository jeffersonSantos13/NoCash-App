package com.example.luiz1.nocash;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.luiz1.nocash.Adapter.HomeListview;

import java.text.DecimalFormat;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Produtos Fakes (Mais originais que Modern Warfare 4)
        final String nomeprod1 = "Smart TV Samsung";
        final String nomeprod2 = "SmartPhone Galaxy J5";
        final String nomeprod3 = "Geladeira Frost Free";
        final String nomeprod4 = "Camisa Seleção Brasil 2018";


// Definindo o que será passado: nome da promoção, descrição, e imagem nessa ordem:
        final String[] nomeprod={nomeprod1,nomeprod2,nomeprod3, nomeprod4};
        final String[] proddesc={"Desc1", "Desc2", "Desc3", "Desc4"};
        final Integer[] imgid={R.drawable.aprod2, R.drawable.aprod3,R.drawable.aprod4,R.drawable.bprod2}; // Imagens, se for adicionar, use PNG
        final Double[] antigosprecos = {1199.90, 849.99, 2199.90, 199.90};
        final Double[] precos={1099.00 ,749.90, 1999.90 ,159.90};




// Listener de cada item da lista
        final ListView lista =  view.findViewById(R.id.listahome);
            final HomeListview homeListview = new HomeListview(getActivity(), nomeprod, proddesc, imgid);
            lista.setAdapter(homeListview);


                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        String s = lista.getItemAtPosition(i).toString();
                        // Toast só pra teste
                      //  Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();


                    // Passando os dados dos produtos via Bundle para FragmentHomeProduto
                        Bundle bundle = new Bundle();

                        if(s == nomeprod1){


                            bundle.putString("nomedoproduto", s);
                            bundle.putDouble("precoproduto", precos[0]);
                            bundle.putDouble("antigosprecos", antigosprecos[0]);
                            bundle.putInt("Imagem", imgid[0]);

                        }else if(s == nomeprod2){

                           bundle.putString("nomedoproduto", s);
                             bundle.putDouble("precoproduto", precos[1]);
                             bundle.putDouble("antigosprecos", antigosprecos[1]);
                            bundle.putInt("Imagem", imgid[1]);


                        }else if(s == nomeprod3){

                            bundle.putString("nomedoproduto", s);
                            bundle.putDouble("precoproduto", precos[2]);
                            bundle.putDouble("antigosprecos", antigosprecos[2]);
                            bundle.putInt("Imagem", imgid[2]);


                        }else if(s == nomeprod4){
                            bundle.putString("nomedoproduto", s);
                            bundle.putDouble("precoproduto", precos[3]);
                            bundle.putDouble("antigosprecos", antigosprecos[3]);
                            bundle.putInt("Imagem", imgid[3]);

                        }

                        FragmentHomeProduto frag1 = new FragmentHomeProduto();
                        frag1.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.corpo, frag1);
                        fragmentTransaction.commit();





                    }
                });



    return view;


    }

}
