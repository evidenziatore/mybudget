package com.evidenziatore.mybudget.database.entity;

public class Importanza {
    private Integer id;
    private String valore;

    // Costruttore
    public Importanza(Integer id, String valore) {
        this.id = id;
        this.valore = valore;
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

    @Override
    public String toString() {
        return (valore != null ? valore + " " : "");
    }
}