package com.example.luiz1.nocash;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class BoardActivity extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setPrevText("Anterior"); // Previous button text
        setNextText("Próximo"); // Next button text
        setFinishText("Usar o aplicativo"); // Finish button text
        setCancelText("Cancelar"); // Cancel button text

        addFragment(new Step.Builder().setTitle("Bem vindo ao NoCash!")
                .setContent("Aqui você conseguirá verificar o seu saldo, adicionar créditos à sua carteira e conferir diversas promoções!")
                .setBackgroundColor(Color.parseColor("#121212")) // int background color
                .setDrawable(R.drawable.logonocash) // int top drawable
                .build());


        addFragment(new Step.Builder().setTitle("Facilidade nas suas compras!")
                .setContent("Com a NoCash, você conta com promoções exclusivas para você! ")
                .setBackgroundColor(Color.parseColor("#121212")) // int background color
                .setDrawable(R.drawable.imgcompra) // int top drawable
                .build());


        addFragment(new Step.Builder().setTitle("Faça o login!")
                .setContent("Com o login efetuado, terá acesso total à plataforma, obtendo descontos, promoções e muito mais!")
                .setBackgroundColor(Color.parseColor("#121212")) // int background color
                .setDrawable(R.drawable.imguser) // int top drawable
                .build());


    }

    @Override
    public void onBackPressed() {   }
    @Override
    public void finishTutorial() {
        Intent i = new Intent(BoardActivity.this, LoginActivity.class);
        startActivity(i);
        finish();

    }

}
