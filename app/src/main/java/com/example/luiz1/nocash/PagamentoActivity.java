package com.example.luiz1.nocash;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.luiz1.nocash.Model.Carteira;
import com.example.luiz1.nocash.Model.Movimento;
import com.example.luiz1.nocash.Model.Pagamento;
import com.example.luiz1.nocash.service.MovimentoService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.ProgressHelper;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PagamentoActivity extends AppCompatActivity {
    private static final String TAG = "Pagamento";
    private ProgressHelper tempoload;
    SweetAlertDialog load;

    private EditText txtcard;
    private Spinner spinmes;
    private Spinner spinano;
    private EditText txtcod;
    private Button btnEfetuarPagamento;

    private List<String> mes = new ArrayList<String>();
    private List<String> anos = new ArrayList<String>();

    private TextView txtsaldo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarnav);
        toolbar.setTitle("Adicionar Créditos");
        toolbar.setNavigationIcon(R.drawable.ic_clear_white_24dp);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        txtcard = findViewById(R.id.txtcard);
        txtcard.addTextChangedListener(Mask.insert("####.####.####.####", txtcard));
        txtcod = findViewById(R.id.txtcod);
        txtcod.addTextChangedListener(Mask.insert("###", txtcod));

        btnEfetuarPagamento = findViewById(R.id.btnEfetuarPagamento);



        spinmes=findViewById(R.id.spinmes);
        spinano= findViewById(R.id.spinano);

        mes.add("Janeiro");
        mes.add("Fevereiro");
        mes.add("Março");
        mes.add("Abril");
        mes.add("Maio");
        mes.add("Junho");
        mes.add("Julho");
        mes.add("Agosto");
        mes.add("Setembro");
        mes.add("Outubro");
        mes.add("Novembro");
        mes.add("Dezembro");


        for(int ano = 2015; ano <= 2100; ano++){

            anos.add(Integer.toString(ano));
        }

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(PagamentoActivity.this, android.R.layout.simple_list_item_1, mes);

        ArrayAdapter<String> adapterSpinner2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, anos);
        spinmes.setAdapter(adapterSpinner);
        spinano.setAdapter(adapterSpinner2);

        btnEfetuarPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                load=new SweetAlertDialog(PagamentoActivity.this,SweetAlertDialog.PROGRESS_TYPE);
                load.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                load.setTitleText("Aguarde...");
                load.setCancelable(true);
                load.show();

                try {

                    final Session session = new Session();
                    final Gson gson = new Gson();

                    String object = session.getSessionPagamento(PagamentoActivity.this);
                    final Pagamento pagamento = gson.fromJson(object, Pagamento.class);

                    // Carteira Destino
                    String objCarteira = session.getSessionCarteira(PagamentoActivity.this);
                    Carteira carteiraDestino = gson.fromJson(objCarteira, Carteira.class);

                    // Parte do Movimento
                    final Movimento movimento = new Movimento();
                    movimento.setCarteiraOrigem(null);
                    movimento.setCarteiraDestino(carteiraDestino);
                    movimento.setNrDocumento(pagamento.getDesccricao());
                    movimento.setVlBruto(pagamento.getValor());
                    movimento.setVlLiquido(pagamento.getValor());
                    movimento.setVlDesc(0);

                    String mov = gson.toJson(movimento);
                    Log.e(TAG, "Movimento: " + mov);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(MovimentoService.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    MovimentoService service = retrofit.create(MovimentoService.class);
                    Call<Void> request = service.cargaMovimento(movimento);

                    request.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.i(TAG, "Retorno: " + response.isSuccessful());
                            if (response.isSuccessful()) {
                                load.hide();
                                Log.i(TAG, "Erro: " + response.code());

                                // Atualiza valor da carteira
                                String carteiraObj = session.getSessionCarteira(PagamentoActivity.this);
                                Carteira carteira = gson.fromJson(carteiraObj, Carteira.class);
                                final double saldo = carteira.getSaldo() + pagamento.getValor();
                                carteira.setSaldo(saldo);

                                session.SessaoCarteira(PagamentoActivity.this, carteira);

                                // Deleta pagamento
                                session.SessionPagamentoDelete(PagamentoActivity.this);

                                new SweetAlertDialog(PagamentoActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Sucesso!")
                                    .setContentText("Recarga efetuada com \nsucesso!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {




                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        sweetAlertDialog.dismissWithAnimation();
                                        Intent i = new Intent(PagamentoActivity.this, HomeActivity.class);

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
                                        "Houve um erro, não foi possível realizar a recarga!");
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            load.hide();
                            erro("Erro", "" +
                                    "Houve um erro, não foi possível realizar a recarga!");
                            Log.e(TAG, "Erro: " + t.getMessage());
                        }
                    });

                } catch (Exception e){
                    load.hide();
                    erro("Erro", "Houve um erro: " + e.getMessage());
                    Log.e(TAG, "Erro: " + e.getMessage());
                }

            }
        });

    }

    private void erro(String title, String mensagem) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(mensagem)
                .show();
    }

    public void loading(String msg){
        SweetAlertDialog load = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        load.getProgressHelper().setBarColor(Color.parseColor("#6cb907"));
        tempoload = new ProgressHelper(PagamentoActivity.this);
        load.setTitleText(msg);
        load.setCancelable(false);
        load.show();
    }

}
