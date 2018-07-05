package com.example.luiz1.nocash;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.luiz1.nocash.Adapter.ParcasListview;
import com.example.luiz1.nocash.Model.Imagens;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaParceirosFragment extends Fragment {


    public ListaParceirosFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
               // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_lista_parceiros, container, false);

        // Parceiros Fakes (Mais originais que Modern Warfare 4)
        final String parca1 = "Lojas Americanas";
        final String parca2 = "Netshoes";
        final String parca3 = "Walmart";
        final String parca4 = "Shoptime";

        final String[] nomeparca={parca1,parca2,parca3, parca4};

        final String[] descdoparca={"É uma empresa brasileira do segmento de varejo fundada em 1929 na cidade de Niterói, no Rio de Janeiro, pelo austríaco Max Landesmann e pelos norte-americanos John Lee, Glen Matson, James Marshall e Batson Borger."
                                    ,"Netshoes é um comércio eletrônico brasileiro de artigos esportivos fundado em fevereiro de 2000 por Marcio Kumruian e Hagop Chabab.",
                                    "Wal-Mart Stores, Inc., conhecida como Walmart desde 2008 e Wal-Mart antes disso, é uma multinacional estadunidense de lojas de departamento. A companhia foi eleita a maior multinacional de 2010.",
                                     "Shoptime é uma empresa brasileira de varejo, criada em 1995, que possui um canal de televisão, a TV Shoptime, e um site de comércio eletrônico. Desde 2005, pertence ao grupo B2W Digital."};

        final Integer[] imgparca={R.drawable.logoamericanas, R.drawable.logonetshoes,R.drawable.logowalmart,R.drawable.logoshoptime}; // Imagens, se for adicionar, use PNG

      //  Imagens[] imgprods;
      //  String[] imagens = imgprods[0].getImg();

        // Listener de cada item da lista
        final ListView lista =  v.findViewById(R.id.listaparcas);
        final ParcasListview parcasListview = new ParcasListview(getActivity(), nomeparca, descdoparca, imgparca);
        lista.setAdapter(parcasListview);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String s = lista.getItemAtPosition(i).toString();
                // Toast só pra teste
                //  Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();

                // Passando os dados dos produtos via Bundle para FragmentParceiroProduto
                Bundle bundle = new Bundle();

                if(s == parca1){

                    Integer[] imgprod={R.drawable.aprod1, R.drawable.aprod2, R.drawable.aprod3, R.drawable.aprod4};


                    final String[] produtos = {"Esteira", "Smart TV Samsung", "SmartPhone Galaxy J5", "Geladeira Frost Free"};

                    bundle.putString("nomedoparceiro", parca1);
                    bundle.putString("nomeparca", s);
                    bundle.putString("nomeproduto1", produtos[0]);
                    bundle.putString("nomeproduto2", produtos[1]);
                    bundle.putString("nomeproduto3", produtos[2]);
                    bundle.putString("nomeproduto4", produtos[3]);

                    bundle.putString("descdoparca", descdoparca[0]);
                    bundle.putInt("imgparca", imgparca[0]);

                    bundle.putInt("Prod1", imgprod[0]);
                    bundle.putInt("Prod2", imgprod[1]);
                    bundle.putInt("Prod3", imgprod[2]);
                    bundle.putInt("Prod4", imgprod[3]);

                }else if(s == parca2){

                    Integer[] imgprod={R.drawable.bprod1, R.drawable.bprod2, R.drawable.bprod3, R.drawable.bprod4};

                    final String[] produtos = {"Bola de Basquete MvP", "Camisa Seleção Brasil 2018", "Bota Gonew Fenix", "Caixa de Som JBL Charge 3"};
                    bundle.putString("nomedoparceiro", parca2);
                    bundle.putString("nomeproduto1", produtos[0]);
                    bundle.putString("nomeproduto2", produtos[1]);
                    bundle.putString("nomeproduto3", produtos[2]);
                    bundle.putString("nomeproduto4", produtos[3]);


                    bundle.putString("nomeparca", s);
                    bundle.putString("descdoparca", descdoparca[1]);
                    bundle.putInt("imgparca", imgparca[1]);

                    bundle.putInt("Prod1", imgprod[0]);
                    bundle.putInt("Prod2", imgprod[1]);
                    bundle.putInt("Prod3", imgprod[2]);
                    bundle.putInt("Prod4", imgprod[3]);

                }else if(s == parca3){
                    bundle.putString("nomedoparceiro", parca3);
                    Integer[] imgprod={R.drawable.cprod1, R.drawable.cprod2, R.drawable.cprod3, R.drawable.cprod4};
                    final String[] produtos = {"Notebook Asus X556ur Intel Core I5 8Gb 1Tb ",
                                               "Google Chromecast 2 Hdmi Full HD Wireless Para Android PC MAC e IOS",
                                               "Smartphone Samsung Galaxy S9 G9600 128GB Desbloqueado Preto - Android 8.0 Oreo, Câmera 12MP, Tela 5.8'",
                                               "Console Playstation 4 PS4 Slim 1TB + 1 Controle Dualshock 4"};

                    bundle.putString("nomeparca", s);
                    bundle.putString("descdoparca", descdoparca[2]);
                    bundle.putInt("imgparca", imgparca[2]);


                    bundle.putString("nomeproduto1", produtos[0]);
                    bundle.putString("nomeproduto2", produtos[1]);
                    bundle.putString("nomeproduto3", produtos[2]);
                    bundle.putString("nomeproduto4", produtos[3]);

                    bundle.putInt("Prod1", imgprod[0]);
                    bundle.putInt("Prod2", imgprod[1]);
                    bundle.putInt("Prod3", imgprod[2]);
                    bundle.putInt("Prod4", imgprod[3]);


                }else if(s == parca4){

                    bundle.putString("nomedoparceiro", parca4);
                    Integer[] imgprod={R.drawable.dprod1, R.drawable.dprod2, R.drawable.dprod3, R.drawable.dprod4};
                    final String[] produtos = {"Tapete De Sala Peludo 2,00x2,40 Bege Com Marrom",
                                                "Colcha Cobre Leito Riviera Casal Queen 5 Peças Vermelho",
                                                "Cobertor Queen Flannel Colors com Borda em Percal - Casa & Conforto",
                                                "Cobertor Queen Flannel Hit com Borda em Percal - Casa & Conforto"};




                    bundle.putString("nomeparca", s);
                    bundle.putString("descdoparca", descdoparca[3]);
                    bundle.putInt("imgparca", imgparca[3]);

                    bundle.putString("nomeproduto1", produtos[0]);
                    bundle.putString("nomeproduto2", produtos[1]);
                    bundle.putString("nomeproduto3", produtos[2]);
                    bundle.putString("nomeproduto4", produtos[3]);

                    bundle.putInt("Prod1", imgprod[0]);
                    bundle.putInt("Prod2", imgprod[1]);
                    bundle.putInt("Prod3", imgprod[2]);
                    bundle.putInt("Prod4", imgprod[3]);

                }

                FragmentParceiroProduto frag1 = new FragmentParceiroProduto();
                frag1.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.corpo, frag1);
                fragmentTransaction.commit();

            }
        });


        return v;
    }

}
