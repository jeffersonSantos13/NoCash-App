package com.example.luiz1.nocash.service;

import com.example.luiz1.nocash.Model.Carteira;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CarteiraService {

    public static final String BASE_URL = "https://carteiravirtualws.azurewebsites.net/nocash/ws/";

    /**
     *  Serviço de cadastro da carteira do cliente
     */
    @POST("carteira")
    Call<Void> inserirCarteira(@Body Carteira carteira);

    /**
     *  Serviço que verifica se a carteira está criado
     */
    @GET("carteira/obter/{param}")
    Call<Carteira> obter(@Path("param") int id);
}
