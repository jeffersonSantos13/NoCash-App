package com.example.luiz1.nocash;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.luiz1.nocash.Model.Carteira;
import com.example.luiz1.nocash.Model.Compra;
import com.example.luiz1.nocash.Model.Movimento;
import com.example.luiz1.nocash.service.MovimentoService;
import com.google.gson.Gson;

import cn.pedant.SweetAlert.ProgressHelper;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CompraActivity extends AppCompatActivity {
    private static final String TAG = "Compra";
    private ProgressHelper tempoload;
    SweetAlertDialog load;

    private EditText txtVl;
    private EditText txtCD;
    private Button btnEfetuarPagamento;
    private int cdCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        txtVl = findViewById(R.id.txtVL_COMPRA);
        txtVl.addTextChangedListener(Mask.insert("###", txtVl));
        txtCD = findViewById(R.id.txtCOD_USER);




        //btnEfetuarPagamento = findViewById(R.id.btnsubmit);

       // btnEfetuarPagamento.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {

                load=new SweetAlertDialog(CompraActivity.this,SweetAlertDialog.PROGRESS_TYPE);
                load.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                load.setTitleText("Aguarde...");
                load.setCancelable(true);
                load.show();

                try {

                    final Session session = new Session();
                    final Gson gson = new Gson();

                    String object = session.getSessionPagamento(CompraActivity.this);
                    final Compra compra = gson.fromJson(object, Compra.class);



                    // Carteira Destino
                    String objCarteira = session.getSessionCarteira(CompraActivity.this);
                    Carteira carteiraDestino = gson.fromJson(objCarteira, Carteira.class);

                    //Carteira Origem
                    Carteira carteiraOrigem = new Carteira();
                    carteiraOrigem.setId(compra.getOrigem());


                    // Parte do Movimento
                    Movimento movimento = new Movimento();
                    movimento.setCarteiraOrigem(carteiraDestino);
                    movimento.setCarteiraDestino(carteiraOrigem);
                    movimento.setNrDocumento(compra.getDesccricao());
                    movimento.setVlBruto(compra.getValor());
                    movimento.setVlLiquido(compra.getValor());
                    movimento.setVlDesc(0);

                    //Date date = new Date();
                    //movimento.setDtMovimento(date);

                    Gson g = new Gson();
                    Log.e("JSON", g.toJson(movimento));

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(MovimentoService.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    MovimentoService service = retrofit.create(MovimentoService.class);
                    Call<Void> request = service.inserirMovimento(movimento);


                    request.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                load.hide();
                                Log.i(TAG, "Erro: " + response.code());

                                new SweetAlertDialog(CompraActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Sucesso!")
                                    .setContentText("Compra efetuada com\n sucesso!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        sweetAlertDialog.dismissWithAnimation();
                                        Intent i = new Intent(CompraActivity.this, HomeActivity.class);


                                        // Atualiza valor da carteira
                                        String carteiraObj = session.getSessionCarteira(CompraActivity.this);
                                        Carteira carteira = gson.fromJson(carteiraObj, Carteira.class);
                                        double saldo = carteira.getSaldo() - compra.getValor();
                                        carteira.setSaldo(saldo);
                                        session.SessaoCarteira(CompraActivity.this, carteira);

                                        if (i.resolveActivity(getPackageManager()) == null) {
                                            loading("Aguarde por favor...");
                                        }
                                        else{
                                            startActivity(i);
                                            finish();
                                        }
                                        }

                                    })
                                    .show();
                            } else {
                                load.hide();
                                Log.i(TAG, "Erro: " + response.code());
                                erro("Erro", "" +
                                        "Houve um erro, não foi possível realizar a compra!");
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            load.hide();
                            erro("Erro", "" +
                                    "Houve um erro, não foi possível realizar a compra!");
                            Log.e(TAG, "Erro: " + t.getMessage());
                        }
                    });

                } catch (Exception e){
                    load.hide();
                    erro("Erro", "Houve um erro: " + e.getMessage());
                    Log.e(TAG, "Erro: " + e.getMessage());
                }

            }
      //  });

    //}

    private void erro(String title, String mensagem) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(mensagem)
                .show();
    }

    public void loading(String msg){
        SweetAlertDialog load = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        load.getProgressHelper().setBarColor(Color.parseColor("#6cb907"));
        tempoload = new ProgressHelper(CompraActivity.this);
        load.setTitleText(msg);
        load.setCancelable(false);
        load.show();
    }

}
