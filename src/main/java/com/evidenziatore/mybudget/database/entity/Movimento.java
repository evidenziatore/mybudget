package com.evidenziatore.mybudget.database.entity;

import javafx.scene.layout.HBox;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Movimento {
    private Integer id;
    private Tipologia tipologia;
    private Categoria categoria;
    private Provenienza provenienza;
    private Prodotto prodotto;
    private Date data;
    private Integer valutazione;
    private Double valore;

    public Movimento(Integer id, Tipologia tipologia, Categoria categoria, Provenienza provenienza, Prodotto prodotto, Date data, Integer valutazione, Double valore) {
        this.id = id;
        this.tipologia = tipologia;
        this.categoria = categoria;
        this.provenienza = provenienza;
        this.prodotto = prodotto;
        this.data = data;
        this.valutazione = valutazione;
        this.valore = valore;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getValutazione() {
        return valutazione;
    }

    public void setValutazione(Integer valutazione) {
        this.valutazione = valutazione;
    }

    public Double getValore() {
        return valore;
    }

    public void setValore(Double valore) {
        this.valore = valore;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return (prodotto != null ? prodotto + " " : "")
                +(valore != null ? valore + " " : "")
                +(valutazione != null ? valutazione + " " : "")
                +(provenienza != null ? provenienza + " " : "")
                +(tipologia != null ? tipologia + " " : "")
                +(categoria != null ? categoria + " " : "")
                +(data != null ? sdf.format(data) + " " : "");
    }
}