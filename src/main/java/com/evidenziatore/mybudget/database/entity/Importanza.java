package com.evidenziatore.mybudget.database.entity;

public class Importanza {
    private int id;
    private String valore;

    // Costruttore
    public Importanza(int id, String valore) {
        this.id = id;
        this.valore = valore;
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
}