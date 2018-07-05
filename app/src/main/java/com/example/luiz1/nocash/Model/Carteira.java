package com.example.luiz1.nocash.Model;

public class Carteira {

    private int id;
    private Cliente cliente;
    private double saldo;
    private String nome;
    private String senha;
    private short senhaOpcional;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente(){
        return cliente;
    }

    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public short getSenhaOpcional() {
        return senhaOpcional;
    }

    public void setSenhaOpcional(short senhaOpcional) {
        this.senhaOpcional = senhaOpcional;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
