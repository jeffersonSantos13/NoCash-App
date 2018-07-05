package com.example.luiz1.nocash;

import android.app.Activity;
import android.util.Log;

import com.example.luiz1.nocash.Model.Carteira;
import com.example.luiz1.nocash.Model.Movimento;
import com.example.luiz1.nocash.SQL.DatabaseTransacao;
import com.example.luiz1.nocash.service.MovimentoService;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Transacoes {

    private static String TAG = "Transações";
    private Functions functions = new Functions();
    DatabaseTransacao myDb;

    // Retorna lista de transações
    public void Lista(final Activity activity){
        final List<Movimento> list = new ArrayList<Movimento>();

        // Banco de dados do movimento
        myDb = new DatabaseTransacao(activity);

        try {

            Gson g = new Gson();

            Session session = new Session();
            Carteira carteira = g.fromJson(session.getSessionCarteira(activity), Carteira.class);
            int destino = carteira.getId();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MovimentoService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MovimentoService service = retrofit.create(MovimentoService.class);
            Call<List<Movimento>> request = service.destino(destino);

            request.enqueue(new Callback<List<Movimento>>() {
                @Override
                public void onResponse(Call<List<Movimento>> call, Response<List<Movimento>> response) {

                    list.addAll(response.body());

                    Movimento movimento = new Movimento();
                    Carteira carteiraOrigem = new Carteira();
                    Carteira carteiraDestino = new Carteira();

                    myDb.deleteAllData();

                    for (int i = 0; i < list.size(); i++) {
                        // Adicionando ao SQLLite
                        movimento = list.get(i);

                        // Carteira origem
                        carteiraOrigem = movimento.getCarteiraOrigem();
                        int origem = carteiraOrigem.getId();

                        carteiraDestino = movimento.getCarteiraDestino();
                        int destino = carteiraDestino.getId();

                        String documento = movimento.getNrDocumento();
                        double bruto = movimento.getVlBruto();
                        double liquido = movimento.getVlBruto();
                        double desc = movimento.getVlBruto();

                        String data = null;
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        try {
                            data = dateFormat.format(movimento.getDtMovimento());
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                        }

                        boolean inserted = myDb.insertData(origem, destino, documento, bruto, liquido, desc, data);
                        if(!inserted){
                            Log.e(TAG, "O valor da transações não foi inserido:");
                        } else {
                            Log.e(TAG, "O valor da transações foi inserido:");
                        }
                    }

                }

                @Override
                public void onFailure(Call<List<Movimento>> call, Throwable t) {
                    Log.e(TAG, "Erro: " + t.getMessage());
                }
            });

        } catch (Exception e){
            Log.e(TAG, "Erro no retorno das transações: " + e.getMessage());
        }

    }

}
