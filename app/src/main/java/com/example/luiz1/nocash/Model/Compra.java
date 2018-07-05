package com.example.luiz1.nocash.Model;

public class Compra {
    double valor;
    int origem;
    String desccricao;


    public Compra(double valor, int origem, String desccricao) {
        this.valor = valor;
        this.origem = origem;
        this.desccricao = desccricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getOrigem() {
        return origem;
    }

    public void setOrigem(int origem) {
        this.origem = origem;
    }

    public String getDesccricao() {
        return desccricao;
    }

    public void setDesccricao(String desccricao) {
        this.desccricao = desccricao;
    }
}
