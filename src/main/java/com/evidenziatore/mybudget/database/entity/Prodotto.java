package com.evidenziatore.mybudget.database.entity;

public class Prodotto {
    private int id;
    private String valore;
    private String peso;

    // Costruttore
    public Prodotto(int id, String valore, String peso) {
        this.id = id;
        this.valore = valore;
        this.peso = peso;
    }

    // Getter e Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }
}