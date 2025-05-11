package com.evidenziatore.mybudget.database.entity;

public class Categoria {
    private Integer id;
    private String valore;
    private Importanza importanza;

    public Categoria(Integer id, String valore, Importanza importanza) {
        this.id = id;
        this.valore = valore;
        this.importanza = importanza;
    }

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

    public Importanza getImportanza() {
        return importanza;
    }

    public void setImportanza(Importanza importanza) {
        this.importanza = importanza;
    }

    @Override
    public String toString() {
        return (valore != null ? valore + " " : "") + (importanza != null ? importanza : "");
    }
}