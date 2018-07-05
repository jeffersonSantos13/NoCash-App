package com.example.luiz1.nocash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.example.luiz1.nocash.Model.Carteira;
import com.google.gson.Gson;

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by luiz1 on 29/04/2018.
 */

public class Functions {

    private static final int TIMER = 0;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isConected(Context cont, final Activity activity){
        ConnectivityManager conmag = (ConnectivityManager)cont.getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( conmag != null ) {
            conmag.getActiveNetworkInfo();

            //Verifica internet pela WIFI
            if (conmag.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
                return true;
            }
            //Verifica se tem internet móvel
            if (conmag.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
                return true;
            }
        }else{
            new SweetAlertDialog(activity)
                    .setTitleText("Falha ao se conectar com a rede!")
                    .setContentText("Verifique sua conexão com a Web, e tente novamente")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();

                            //Falta fragment net!
                                Intent i = new Intent(activity , HomeActivity.class);
                                activity.startActivity(i);


                        }

                    })
                    .show();

        }

        return false;
    }

    public boolean isValidEmail(String email){
        if (null != email) {
            String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                return false;
            } else {
                return true;
            }
        }

        return false;
    }

    public static boolean isCPF(String CPF) {
        CPF = CPF.replace(".", "");
        CPF = CPF.replace("-", "");
        CPF = CPF.replace(" ", "");

        if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)){
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48);
            }

            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

    // Retorna o saldo na carteira
    public double vSaldo(Activity activity) {
        Gson gson = new Gson();
        Session session = new Session();

        String object = session.getSessionCarteira(activity);

        Carteira carteira = gson.fromJson(object, Carteira.class);

        double saldo = carteira.getSaldo();

        return saldo;
    }

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

    public static boolean isEmail(String email){
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void erro(String title, String mensagem, Activity activity) {
        new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(mensagem)
                .show();
    }

    public void TimerCount(Context context, int minutos){
        SharedPreferences prefs = context.getSharedPreferences("Timer", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Timer", minutos);
        editor.apply();
    }

    public boolean atualizaMovimentos(Context context, int minutosT){
        SharedPreferences prefs = context.getSharedPreferences("Timer", context.MODE_PRIVATE);

        int minutos = prefs.getInt("Timer", 0);

        // Atualiza as transações
        if(minutos > TIMER) {
            TimerCount(context, minutosT);
            return true;
        }

        return false;
    }
}
