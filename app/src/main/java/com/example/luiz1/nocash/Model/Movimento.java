package com.example.luiz1.nocash.Model;

import java.util.Date;

public class Movimento {

    private int id;
    private Carteira carteiraOrigem;
    private Carteira carteiraDestino;
    private String nrDocumento;
    private double vlBruto;
    private double vlLiquido;
    private double vlDesc;
    private Date dtMovimento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Carteira getCarteiraOrigem() {
        return carteiraOrigem;
    }

    public void setCarteiraOrigem(Carteira carteiraOrigem) {
        this.carteiraOrigem = carteiraOrigem;
    }

    public Carteira getCarteiraDestino() {
        return carteiraDestino;
    }

    public void setCarteiraDestino(Carteira carteiraDestino) {
        this.carteiraDestino = carteiraDestino;
    }

    public String getNrDocumento() {
        return nrDocumento;
    }

    public void setNrDocumento(String nrDocumento) {
        this.nrDocumento = nrDocumento;
    }

    public double getVlBruto() {
        return vlBruto;
    }

    public void setVlBruto(double vlBruto) {
        this.vlBruto = vlBruto;
    }

    public double getVlLiquido() {
        return vlLiquido;
    }

    public void setVlLiquido(double vlLiquido) {
        this.vlLiquido = vlLiquido;
    }

    public double getVlDesc() {
        return vlDesc;
    }

    public void setVlDesc(double vlDesc) {
        this.vlDesc = vlDesc;
    }

    public Date getDtMovimento() {
        return dtMovimento;
    }

    public void setDtMovimento(Date dtMovimento) {
        this.dtMovimento = dtMovimento;
    }

}
