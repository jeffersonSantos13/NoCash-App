package com.example.luiz1.nocash;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luiz1.nocash.Adapter.HomeListview;
import com.example.luiz1.nocash.Adapter.ProdParcasListview;

public class FragmentParceiroProduto extends Fragment {

    private ImageView logoparca;
    private ImageView imgprod;
    private TextView nomeparca;
    private TextView descparca;


    public FragmentParceiroProduto() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_parceiro_produto, container, false);

        logoparca = v.findViewById(R.id.logoparca);
        nomeparca = v.findViewById(R.id.nomeprod);
        descparca = v.findViewById(R.id.descparca);
        imgprod = v.findViewById(R.id.imgprodparca);

        // Pegando os dados dos produtos (ListaParceirosFragment)
        Bundle bundle = this.getArguments();
        if(bundle !=null){
            String nomedoparca = bundle.getString("nomeparca");
            String descdoparca = bundle.getString("descdoparca");

            final Integer logodoparca = bundle.getInt("imgparca");


            final Integer imgprod1 = bundle.getInt("Prod1");
            final Integer imgprod2 = bundle.getInt("Prod2");
            final Integer imgprod3 = bundle.getInt("Prod3");
            final Integer imgprod4 = bundle.getInt("Prod4");



            final String nomeprod1 = bundle.getString("nomeproduto1");
            final String nomeprod2 = bundle.getString("nomeproduto2");
            final String nomeprod3 = bundle.getString("nomeproduto3");
            final String nomeprod4 = bundle.getString("nomeproduto4");


            nomeparca.setText(nomedoparca); // Nome do Parceiro
            descparca.setText(descdoparca); // Descrição do Parça
            logoparca.setImageResource(logodoparca); // Imagem do Parceiro


         // Definindo o que será passado: nome da promoção, descrição, e imagem nessa ordem:
            final String[] nomeprod={nomeprod1,nomeprod2,nomeprod3, nomeprod4};


            final Double[] antigosprecos = {30.00, 80.00, 150.00, 250.00};
            final Double[] precos={20.00 ,50.00 ,100.00 ,200.00};



            Integer[] imgid={R.drawable.aprod1, R.drawable.aprod2, R.drawable.aprod3, R.drawable.aprod4};


            if(nomedoparca == "Lojas Americanas"){
                imgid= new Integer[]{R.drawable.aprod1, R.drawable.aprod2, R.drawable.aprod3, R.drawable.aprod4};
            }else if(nomedoparca == "Netshoes") {
                 imgid= new Integer[]{R.drawable.bprod1, R.drawable.bprod2, R.drawable.bprod3, R.drawable.bprod4};
            }else if(nomedoparca == "Walmart") {
                 imgid= new Integer[]{R.drawable.cprod1, R.drawable.cprod2, R.drawable.cprod3, R.drawable.cprod4};
            }else if(nomedoparca == "Shoptime") {
                imgid= new Integer[]{R.drawable.dprod1, R.drawable.dprod2, R.drawable.dprod3, R.drawable.dprod4};

            }

            // Listener de cada item da lista
            final ListView lista =  v.findViewById(R.id.listaprodparceiro);
            final ProdParcasListview prodParcasListview = new ProdParcasListview(getActivity(), nomeprod, imgid);
            lista.setAdapter(prodParcasListview);


            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String s = lista.getItemAtPosition(i).toString();
                    // Toast só pra teste
                    //  Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();

                    // Passando os dados dos produtos via Bundle para FragmentHomeProduto
                    Bundle bundle = new Bundle();

                    if(s == nomeprod1){

                        antigosprecos[0] = 1599.99;
                        precos[0] = 1259.90;

                        antigosprecos[1] = 1299.99;
                        precos[1] = 1099.99;

                        antigosprecos[2] = 749.99;
                        precos[2] = 699.99;

                        antigosprecos[3] = 1899.99;
                        precos[3] = 1699.99;

                        bundle.putString("nomedoproduto", s);
                        bundle.putDouble("precoproduto", precos[0]);
                        bundle.putDouble("antigosprecos", antigosprecos[0]);
                        bundle.putInt("Imagem", imgprod1);

                    }else if(s == nomeprod2){


                        Integer[] imgid={R.drawable.aprod1, R.drawable.aprod2, R.drawable.aprod3, R.drawable.aprod4};

                        antigosprecos[0] = 79.99;
                        precos[0] = 49.99;

                        antigosprecos[1] = 129.99;
                        precos[1] = 109.99;

                        antigosprecos[2] = 129.99;
                        precos[2] = 99.99;

                        antigosprecos[3] = 699.99;
                        precos[3] = 659.99;


                        bundle.putString("nomedoproduto", s);
                        bundle.putDouble("precoproduto", precos[1]);
                        bundle.putDouble("antigosprecos", antigosprecos[1]);

                        bundle.putInt("Imagem", imgprod2);

                    }else if(s == nomeprod3){

                        Integer[] imgid={R.drawable.aprod1, R.drawable.aprod2, R.drawable.aprod3, R.drawable.aprod4};

                        antigosprecos[0] = 1899.99;
                        precos[0] = 1699.99;

                        antigosprecos[1] = 149.99;
                        precos[1] = 129.99;

                        antigosprecos[2] = 3499.99;
                        precos[2] = 3199.99;

                        antigosprecos[3] = 1599.99;
                        precos[3] = 1399.99;

                        bundle.putString("nomedoproduto", s);
                        bundle.putDouble("precoproduto", precos[2]);
                        bundle.putDouble("antigosprecos", antigosprecos[2]);


                        bundle.putInt("Imagem", imgprod3);

                    }else if(s == nomeprod4){

                        Integer[] imgid={R.drawable.aprod1, R.drawable.aprod2, R.drawable.aprod3, R.drawable.aprod4};

                        antigosprecos[0] = 129.99;
                        precos[0] = 119.99;

                        antigosprecos[1] = 149.99;
                        precos[1] = 129.99;

                        antigosprecos[2] = 3499.99;
                        precos[2] = 3199.99;

                        antigosprecos[3] = 1599.99;
                        precos[3] = 1399.99;


                        bundle.putString("nomedoproduto", s);
                        bundle.putDouble("precoproduto", precos[3]);
                        bundle.putDouble("antigosprecos", antigosprecos[3]);


                        bundle.putInt("Imagem", imgprod4);

                    }

                    FragmentHomeProduto frag1 = new FragmentHomeProduto();
                    frag1.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.corpo, frag1);

                    fragmentTransaction.commit();





                }
            });


        }


        return v;
    }

}
