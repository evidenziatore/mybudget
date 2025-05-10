package com.evidenziatore.mybudget.database.entity;

import javafx.scene.layout.HBox;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Movimento {
    private int id;
    private Tipologia tipologia;
    private Categoria categoria;
    private Provenienza provenienza;
    private Prodotto prodotto;
    private Date data;
    private int valutazione;

    public Movimento(int id, Tipologia tipologia, Categoria categoria, Provenienza provenienza, Prodotto prodotto, Date data, int valutazione) {
        this.id = id;
        this.tipologia = tipologia;
        this.categoria = categoria;
        this.provenienza = provenienza;
        this.prodotto = prodotto;
        this.data = data;
        this.valutazione = valutazione;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tipologia getTipologia() {
        return tipologia;
    }

    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Provenienza getProvenienza() {
        return provenienza;
    }

    public void setProvenienza(Provenienza provenienza) {
        this.provenienza = provenienza;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getValutazione() {
        return valutazione;
    }

    public void setValutazione(int valutazione) {
        this.valutazione = valutazione;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return (prodotto != null ? prodotto + " " : "")
                +(provenienza != null ? provenienza + " " : "")
                +(tipologia != null ? tipologia + " " : "")
                +(categoria != null ? categoria + " " : "")
                +(data != null ? sdf.format(data) + " " : "");
    }
}