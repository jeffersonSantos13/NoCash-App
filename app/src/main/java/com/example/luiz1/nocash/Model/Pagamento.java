package com.example.luiz1.nocash.Model;

public class Pagamento {
    double valor;
    int origem;
    String desccricao;

    public Pagamento(double valor, String desccricao) {
        this.valor = valor;
        this.desccricao = desccricao;
        this.origem = 0;
    }

    public Pagamento(double valor, String desccricao, int origem) {
        this.valor = valor;
        this.desccricao = desccricao;
        this.origem = origem;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDesccricao() {
        return desccricao;
    }

    public void setDesccricao(String desccricao) {
        this.desccricao = desccricao;
    }
}
