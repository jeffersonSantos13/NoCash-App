package com.example.luiz1.nocash;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.view.View.OnClickListener;

import com.example.luiz1.nocash.Model.Pagamento;

import java.text.DecimalFormat;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FragmentRecarga extends Fragment {

    private Button btn10;
    private Button btn20;
    private Button btn50;
    private Button btn100;
    private Button btnconfrecarga;
    private TextView txtval;
    private TextView txtlimpa;
    private EditText edtIdentificacao;
    private Double soma=0.00;

    public FragmentRecarga() {
        // Required empty public constructor
    }

    private void AddSoma(Double valor) {
        soma+=valor;
        txtval.setText(new DecimalFormat("###,###,##0.00").format(soma));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_recarga, container, false);

        btn10=(Button) view.findViewById(R.id.btn1);
        btn20=(Button) view.findViewById(R.id.btn3);
        btn50=(Button) view.findViewById(R.id.button4);
        btn100=(Button)view.findViewById(R.id.button5);
        btnconfrecarga = (Button) view.findViewById(R.id.btnconfrecarga);
        txtval = view.findViewById(R.id.textView2);


        txtval.setText(new DecimalFormat("###,###,##0.00").format(0));
        btn10.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSoma(10.00);
            }

        });
        btn20.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSoma(20.00);
            }
        });
        btn50.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSoma(50.00);
            }
        });
        btn100.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSoma(100.00);
            }
        });

        txtlimpa = view.findViewById(R.id.txtlimpa);

        txtlimpa.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                soma = 0.00;
                txtval.setText(new DecimalFormat("###,###,##0.00").format(soma));
            }
        });

        btnconfrecarga.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (soma <= 0.00) {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Opa, houve um erro!")
                            .setContentText("A recarga mínima é de R$ 10,00.")
                            .show();


                }else if(soma > 5000.00){
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Opa, houve um erro!")
                            .setContentText("A recarga máxima é de R$ 5.000,00.")
                            .show();

                }else {
                    String descricao = "Recarga da carteira";
                    Pagamento pagamento = new Pagamento(soma, descricao);

                    Session session = new Session();
                    session.SessaoPagamento(getActivity(), pagamento);

                    Intent intent = new Intent(getActivity(), PagamentoActivity.class);
                    startActivity(intent);
                }
            }
        });
        return view;

    }


}
