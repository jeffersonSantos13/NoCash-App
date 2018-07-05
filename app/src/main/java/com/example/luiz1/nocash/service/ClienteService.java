package com.example.luiz1.nocash.service;

import com.example.luiz1.nocash.Model.Cliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ClienteService {

    public static final String BASE_URL = "https://carteiravirtualws.azurewebsites.net/nocash/ws/";

    /**
     *  Serviço de login do cliente
     */
    @POST("clientes/login")
    Call<Cliente> Login(@Body Cliente cliente);

    /**
    *  Serviço de cadastro do cliente
    */
    @POST("clientes")
    Call<Void> inserirCliente(@Body Cliente cliente);

    /**
     *  Serviço que verifica se já existe o e-mail cadastrado
     */
    @POST("clientes/email")
    Call<Boolean> verificaEmail(@Body String email);

    /**
    * Serviço de retorno dos clientes
     */
    @GET("clientes")
    Call<List<Cliente>> getClientes();
}
