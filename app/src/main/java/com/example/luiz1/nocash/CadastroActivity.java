package com.example.luiz1.nocash;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.luiz1.nocash.Model.Cliente;
import com.example.luiz1.nocash.service.ClienteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CadastroActivity extends AppCompatActivity {

    private static final String TAG = "Cadastro";

    private EditText txtnome;
    private EditText txtmail;
    private EditText txtcpf;
    private EditText txtrg;
    private EditText txtsenha;
    private Button btnok;
    private Toolbar toolbar;
    private WebView webView;
    private Functions functions = new Functions();

    private ProgressBar loading;
    Handler handler;
    Runnable runnable;
    Timer timer;

    SweetAlertDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtnome = findViewById(R.id.txtnome);
        txtmail = findViewById(R.id.txtmail);
        txtcpf = findViewById(R.id.txtcpf);
        txtrg = findViewById(R.id.txtrg);
        txtsenha = findViewById(R.id.txtsenhacad);
        btnok = findViewById(R.id.btnsubmit);

        txtcpf.addTextChangedListener(Mask.insert("###.###.###-##", txtcpf));
        txtrg.addTextChangedListener(Mask.insert("##.###.###-#", txtrg));


        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarcad);
        toolbar.setTitle("Cadastro de Usuário");
        toolbar.setNavigationIcon(R.drawable.ic_clear_white_24dp);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent i = new Intent(CadastroActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
            }
        });



        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Declaração das variáveis
                String nome = txtnome.getText().toString().trim();
                String email = txtmail.getText().toString().trim();
                String cpf = txtcpf.getText().toString().trim();
                String rg = txtrg.getText().toString().trim();
                String senha = txtsenha.getText().toString().trim();

                if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() ||
                        rg.isEmpty() || senha.isEmpty() )
                {
                    erro("Os campos com * são de preenchimento obrigatórios.");
                }
                else if (functions.isCPF(cpf) == false) {
                    erro("O CPF informado é inválido, verifique o CPF informado e tente novamente.");
                } else {

                    boolean isEmail = Functions.isEmail(email);

                    if(!isEmail) {

                        load.hide();
                        erro("E-mail infomado inválido!");

                    } else {

                        load=new SweetAlertDialog(CadastroActivity.this,SweetAlertDialog.PROGRESS_TYPE);
                        load.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        load.setTitleText("Aguarde...");
                        load.setCancelable(true);
                        load.show();

                        Cliente cliente = new Cliente();

                        cliente.setNome(nome);
                        cliente.setEmail(email);
                        cliente.setSenha(senha);
                        cliente.setRg(rg);
                        cliente.setCpf(cpf);

                        try {
                            // Verifica se o e-mail não está cadastrado
                            Session session = new Session();

                            //if(!session.verificaEmail(email, CadastroActivity.this)) {
                                // Efetua o cadastro
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(ClienteService.BASE_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                ClienteService service = retrofit.create(ClienteService.class);
                                Call<Void> response = service.inserirCliente(cliente);

                                response.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                        if (!response.isSuccessful()) {
                                            load.hide();

                                            erro("Não possível cadastrar, verifique os campos ou\n" +
                                                    "tente utilizar outro e-mail!");

                                            Log.i(TAG, "Erro: " + response.code());

                                        } else {
                                            load.hide();

                                            loginok();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        load.hide();

                                        Log.e(TAG, "Erro: " + t.getMessage());
                                        erro(t.getMessage());
                                    }
                                });
                            /*} else {
                                load.hide();

                                erro("E-mail já cadastro!");
                            }*/

                        } catch(Exception e){
                            // Terminar o loading aqui
                            load.hide();

                            erro("Houve um erro: " + e.getMessage());
                            Log.e(TAG, "Erro: " + e.getMessage());
                        }
                    }
                }
            }
        });

    }

    // Mensagens de aviso
    private void erro(String mensagem) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Opa, houve um erro!")
                .setContentText(mensagem)
                .show();
    }

    // Caso o cadastro seja realizado com sucesso, cadastra o cliente e
    // redireciona para realizar o Login
    private void loginok() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Bem vindo!")
                .setContentText("Cadastro realizado com\n sucesso!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        sweetAlertDialog.dismissWithAnimation();
                        Intent i = new Intent(CadastroActivity.this, LoginActivity.class);
                        i.putExtra("Login", "false");
                        startActivity(i);
                    }

                })
                .show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Armazena os e-mails
        new Session().SessaoClienteDelete(CadastroActivity.this);

        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ClienteService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ClienteService service = retrofit.create(ClienteService.class);
            Call<List<Cliente>> response = service.getClientes();

            response.enqueue(new Callback<List<Cliente>>() {
                @Override
                public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {

                    if(response.isSuccessful()) {

                        List<Cliente> clientes = new ArrayList<>();
                        clientes.addAll(response.body());

                        Session session = new Session();
                        session.SessaoCliente(CadastroActivity.this, clientes);
                        Log.e(TAG, "Clientes carregados: " + response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Cliente>> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                }
            });

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }
}