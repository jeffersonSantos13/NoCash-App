package com.example.luiz1.nocash.service;

import com.example.luiz1.nocash.Model.Movimento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MovimentoService {

    public static final String BASE_URL = "https://carteiravirtualws.azurewebsites.net/nocash/ws/";

    /**
     *  Serviço para recarga da carteira
     */
    @POST("movimento/carga")
    Call<Void> cargaMovimento(@Body Movimento movimento);

    @POST("movimento")
    Call<Void> inserirMovimento(@Body Movimento movimento);
    /**
    * Serviço para resgatar as transações do cliente
     */
    @GET("movimento/destino/{param}")
    Call<List<Movimento>> destino(@Path("param")int destino);


}
