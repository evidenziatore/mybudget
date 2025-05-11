package com.evidenziatore.mybudget.database.entity;

public class Prodotto {
    private Integer id;
    private String valore;
    private Integer peso;

    // Costruttore
    public Prodotto(Integer id, String valore, Integer peso) {
        this.id = id;
        this.valore = valore;
        this.peso = peso;
    }

    // Getter e Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return (valore != null ? valore + " " : "")+ (peso != null ? peso + " " : "");
    }
}