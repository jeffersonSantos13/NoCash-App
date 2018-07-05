package com.example.luiz1.nocash;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import com.example.luiz1.nocash.Model.Carteira;
import com.example.luiz1.nocash.Model.Cliente;
import com.example.luiz1.nocash.Model.Compra;
import com.example.luiz1.nocash.Model.Pagamento;
import com.example.luiz1.nocash.service.CarteiraService;
import com.example.luiz1.nocash.service.ClienteService;
import com.google.gson.Gson;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Session {

    private static final String TAG = "Session";
    private static boolean RETORNO = false;
    private SharedPreferences prefs = null;

    /*
    ------------------------------------- Sessão do Cliente  ------------------------------------
     */

    // Retorno do cliente
    public String getSession(Context context){
        prefs = context.getSharedPreferences("SessionLogin", context.MODE_PRIVATE);
        return prefs.getString("Session", "");
    }

    // Armazenando sessão
    public void SessaoLogin(Context context, Cliente cliente){
        Gson gson = new Gson();
        String json = gson.toJson(cliente);

        prefs = context.getSharedPreferences("SessionLogin", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Session", json);
        editor.apply();
    }

    // Deleta sessão
    public void Logoff(Context context){
        prefs = context.getSharedPreferences("SessionLogin", context.MODE_PRIVATE);
        prefs.edit().remove("Session").commit();
    }

    // Armazenando sessão
    public void SessaoCliente(Context context, List<Cliente> clientes){
        try {
            Gson gson = new Gson();
            String cliente = gson.toJson(clientes);

            prefs = context.getSharedPreferences("SessionCliente", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("SessionCliente", cliente);
            editor.apply();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public String getSessionCliente(Context context){
        prefs = context.getSharedPreferences("SessionCliente", context.MODE_PRIVATE);
        return prefs.getString("SessionCliente", "");
    }

    public void SessaoClienteDelete(Context context){
        prefs = context.getSharedPreferences("SessionCliente", context.MODE_PRIVATE);
        prefs.edit().remove("SessionCliente").commit();
    }

    /*
    ------------------------------------- Sessão da Carteira ------------------------------------
     */

    // Armazena a carteira para atualizar o valor
    public void SessaoCarteira(Context context, Carteira carteira){
        Gson gson = new Gson();
        try {
            String object = gson.toJson(carteira);

            prefs = context.getSharedPreferences("SessionWallet", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Wallet", object);
            editor.apply();

        } catch (Exception e){
            Log.e(TAG, "Erro: " + e.getMessage());
        }
    }

    // Deleta sessão da Carteira
    public void SessionCarteiraDelete(Context context){
        prefs = context.getSharedPreferences("SessionWallet", context.MODE_PRIVATE);
        prefs.edit().remove("Wallet").commit();
    }

    // Retorno da carteira
    public String getSessionCarteira(Context context){
        prefs = context.getSharedPreferences("SessionWallet", context.MODE_PRIVATE);
        Log.e(TAG, "Carteira: " + prefs.getString("Wallet", ""));
        return prefs.getString("Wallet", "");
    }

    /*
    ------------------------------------- Sessão do Movimento ------------------------------------
     */

    // Armazena sessão da recarga
    public void SessaoPagamento(Context context, Pagamento pagamento){
        Gson gson = new Gson();
        try {
            String object = gson.toJson(pagamento);

            prefs = context.getSharedPreferences("SessionPay", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Pay", object);
            editor.apply();

        } catch (Exception e){
            Log.e(TAG, "Erro: " + e.getMessage());
        }
    }

    public void SessaoComprar(Context context, Compra compra){
        Gson gson = new Gson();
        try {
            String object = gson.toJson(compra);

            prefs = context.getSharedPreferences("SessionPay", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Pay", object);
            editor.apply();

        } catch (Exception e){
            Log.e(TAG, "Erro: " + e.getMessage());
        }
    }

    // Retorno da carteira
    public String getSessionPagamento(Context context){
        prefs = context.getSharedPreferences("SessionPay", context.MODE_PRIVATE);
        Log.e(TAG, "Pagamento: " + prefs.getString("Pay", ""));
        return prefs.getString("Pay", "");
    }

    public void SessionPagamentoDelete(Context context){
        prefs = context.getSharedPreferences("SessionPay", context.MODE_PRIVATE);
        prefs.edit().remove("Pay").commit();
    }


    /*
    ------------------------------------- Validações ------------------------------------
     */

    // Verifica se já existe o e-mail cadastrado
    public boolean verificaEmail(String email, final Context context){

        try {

            Gson g = new Gson();

            try {
                // verificar pq não está validando
                String emailUnico = "{\"email\":" + "\"" + email + "\"}";

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ClienteService.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ClienteService service = retrofit.create(ClienteService.class);
                Call<Boolean> response = service.verificaEmail(emailUnico);

                response.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful()){
                            prefs = context.getSharedPreferences("RETORNO", context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean("RETORNO", true);
                            editor.apply();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

        } catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        prefs = context.getSharedPreferences("RETORNO", context.MODE_PRIVATE);
        RETORNO =  prefs.getBoolean("RETORNO", false);

        return RETORNO;
    }

    // Verifica o primeiro acesso
    public boolean primeiroAcesso(Activity activity){

        try {

            String json = getSession(activity);
            Log.i(TAG, "Session: " + json);
            Gson gson = new Gson();

            try {

                Cliente cliente = gson.fromJson(json, Cliente.class);
                int id = cliente.getId();

                retornoCarteira(id, activity);
                Session session = new Session();

                String object = session.getSessionCarteira(activity);

                Carteira carteira = gson.fromJson(object, Carteira.class);

                if(carteira.getId() > 0)
                    RETORNO = true;
                else
                    RETORNO = false;

            } catch (Exception e) {
                Log.i(TAG, "Session: " + e.getMessage());
            }

        } catch (Exception e){
            Log.e(TAG, "Erro: " + e.getMessage());
        }

        return RETORNO;
    }

    // Busca a carteira do cliente
    public void retornoCarteira(int id, final Activity activity){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CarteiraService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CarteiraService service = retrofit.create(CarteiraService.class);
        Call<Carteira> response = service.obter(id);

        response.enqueue(new Callback<Carteira>() {
            @Override
            public void onResponse(Call<Carteira> call, Response<Carteira> response) {
                if (response.isSuccessful()) {
                    Carteira carteira = new Carteira();
                    carteira = response.body();
                    SessaoCarteira(activity, carteira);
                } else {
                    Log.e(TAG, "Erro: " + response.isSuccessful());
                }
            }

            @Override
            public void onFailure(Call<Carteira> call, Throwable t) {
                Log.e(TAG, "Erro: " + t.getMessage());
            }
        });

    }

    // Insere a carteira do cliente
    public void inserirCarteira(final Activity activity) {

        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CarteiraService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CarteiraService service = retrofit.create(CarteiraService.class);
            String object =  new Session().getSession(activity);

            Gson gson = new Gson();
            final Cliente cliente = gson.fromJson(object, Cliente.class);

            final Carteira[] carteira = {new Carteira()};
            carteira[0].setNome(cliente.getNome());
            carteira[0].setSenha(cliente.getSenha());
            carteira[0].setSaldo(0.0);
            carteira[0].setSenhaOpcional((short) 0);

            carteira[0].setCliente(cliente);

            Call<Void> response = service.inserirCarteira(carteira[0]);
            response.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (!response.isSuccessful()) {
                        Log.i(TAG, "Erro: " + response.code());
                    } else {
                        Gson g = new Gson();
                        Session session = new Session();

                        String object = session.getSessionCarteira(activity);

                        Carteira carteira = g.fromJson(object, Carteira.class);

                        SessaoCarteira(activity, carteira);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(TAG, "Erro: " + t.getMessage());
                }
            });

        } catch (Exception e){
            Log.e(TAG, "Erro: " + e.getMessage());
        }

    }

    // Verifica se o cliente não possuí uma carteira e cria
    public void verificaCarteira(Activity activity){

        String object = getSessionCarteira(activity);
        Gson gson = new Gson();
        Carteira carteira = gson.fromJson(object, Carteira.class);

        if(carteira.getId() == 0){
            if(!primeiroAcesso(activity)){
                SweetAlertDialog load;
                load = new SweetAlertDialog(activity,SweetAlertDialog.PROGRESS_TYPE);
                load.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                load.setTitleText("Aguarde...");
                load.setCancelable(true);
                load.show();

                // Insere a carteira do cliente
                inserirCarteira(activity);
                load.hide();
            }
        }
    }



}
