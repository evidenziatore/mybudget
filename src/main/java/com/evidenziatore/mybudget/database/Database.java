package com.evidenziatore.mybudget.database;

import com.evidenziatore.mybudget.database.entity.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String DB_FOLDER = System.getProperty("user.home") + File.separator + "mybudget";
    private static final String DB_PATH = DB_FOLDER + File.separator + "mybudget.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initialize() {
        try {
            File folder = new File(DB_FOLDER);
            if (!folder.exists() && folder.mkdirs()) {
                System.out.println("Cartella database creata: " + DB_FOLDER);
            }

            try (Connection conn = connect()) {
                InputStream in = Database.class.getResourceAsStream("/init.sql");
                if (in == null) {
                    throw new IOException("init.sql non trovato nelle risorse!");
                }
                String sql = new String(in.readAllBytes(), StandardCharsets.UTF_8);
                try (Statement stmt = conn.createStatement()) {
                    for (String statement : sql.split(";")) {
                        String trimmed = statement.trim();
                        if (!trimmed.isEmpty()) {
                            stmt.execute(trimmed);
                        }
                    }
                }

                System.out.println("Database inizializzato da file: " + DB_PATH);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Movimento> getAllMovimentiCompletamente() {
        List<Movimento> movimenti = new ArrayList<>();

        String sql = """
            SELECT 
                m.id AS movimento_id,
                m.data AS data,
                m.valutazione AS valutazione,
                m.valore AS valore,
                t.id AS tipo_movimento_id, t.valore AS tipo_movimento_valore,
                c.id AS categoria_movimento_id, c.valore AS categoria_movimento_valore,
                i.id AS livello_importanza_id, i.valore AS livello_importanza_valore,
                p.id AS provenienza_id, p.valore AS provenienza_valore,
                pr.id AS prodotto_id, pr.valore AS prodotto_valore, pr.peso AS prodotto_peso
            FROM movimento_magazzino m
            LEFT JOIN tipo_movimento t ON m.tipo_movimento_id = t.id
            LEFT JOIN categoria_movimento c ON m.categoria_movimento_id = c.id
            LEFT JOIN livello_importanza i ON c.importanza_id = i.id
            LEFT JOIN provenienza p ON m.provenienza_id = p.id
            LEFT JOIN prodotto pr ON m.prodotto_id = pr.id
            """;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            while (rs.next()) {
                Tipologia tipologia = new Tipologia(
                        rs.getInt("tipo_movimento_id"),
                        rs.getString("tipo_movimento_valore")
                );

                Importanza importanza = new Importanza(
                        rs.getInt("livello_importanza_id"),
                        rs.getString("livello_importanza_valore")
                );

                Categoria categoria = new Categoria(
                        rs.getInt("categoria_movimento_id"),
                        rs.getString("categoria_movimento_valore"),
                        importanza
                );

                Provenienza provenienza = new Provenienza(
                        rs.getInt("provenienza_id"),
                        rs.getString("provenienza_valore")
                );

                Prodotto prodotto = new Prodotto(
                        rs.getInt("prodotto_id"),
                        rs.getString("prodotto_valore"),
                        rs.getInt("prodotto_peso")
                );

                Movimento movimento = new Movimento(
                        rs.getInt("movimento_id"),
                        tipologia,
                        categoria,
                        provenienza,
                        prodotto,
                        sdf.parse(rs.getString("data")),
                        rs.getInt("valutazione"),
                        rs.getDouble("valore")
                );

                movimenti.add(movimento);
            }

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return movimenti;
    }

    public static List<Prodotto> getAllProdotti() {
        List<Prodotto> prodotti = new ArrayList<>();

        String sql = "SELECT id, valore, peso FROM prodotto";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Prodotto prodotto = new Prodotto(rs.getInt("id"), rs.getString("valore"), rs.getInt("peso"));
                prodotti.add(prodotto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prodotti;
    }

    public static List<Importanza> getAllImportanze() {
        List<Importanza> importanze = new ArrayList<>();

        String sql = "SELECT id, valore FROM livello_importanza";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Importanza importanza = new Importanza(rs.getInt("id"), rs.getString("valore"));
                importanze.add(importanza);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return importanze;
    }

    public static List<Tipologia> getAllTipologie() {
        List<Tipologia> tipologie = new ArrayList<>();

        String sql = "SELECT id, valore FROM tipo_movimento";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Tipologia tipologia = new Tipologia(rs.getInt("id"), rs.getString("valore"));
                tipologie.add(tipologia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipologie;
    }

    public static List<Categoria> getAllCategorie() {
        List<Categoria> categorie = new ArrayList<>();

        String sql = """
            SELECT 
                c.id AS categoria_movimento_id, 
                c.valore AS categoria_movimento_valore, 
                i.id AS livello_importanza_id, 
                i.valore AS livello_importanza_valore
            FROM categoria_movimento c
            LEFT JOIN livello_importanza i ON c.importanza_id = i.id
            """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Importanza importanza = new Importanza(
                        rs.getInt("livello_importanza_id"),
                        rs.getString("livello_importanza_valore")
                );

                Categoria categoria = new Categoria(
                        rs.getInt("categoria_movimento_id"),
                        rs.getString("categoria_movimento_valore"),
                        importanza
                );
                categorie.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorie;
    }

    public static List<Provenienza> getAllProvenienze() {
        List<Provenienza> provenienze = new ArrayList<>();

        String sql = "SELECT id, valore FROM provenienza";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Provenienza provenienza = new Provenienza(rs.getInt("id"), rs.getString("valore"));
                provenienze.add(provenienza);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return provenienze;
    }

    public static void eliminaRecord(String nomeTabella, int id) {
        String sql = "DELETE FROM " + nomeTabella + " WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inserisciRecord(String nomeTabella, String[] colonne, Object[] valori) {
        StringBuilder sql = new StringBuilder("INSERT INTO " + nomeTabella + " (");
        for (int i = 0; i < colonne.length; i++) {
            sql.append(colonne[i]);
            if (i < colonne.length - 1) {
                sql.append(", ");
            }
        }
        sql.append(") VALUES (");
        for (int i = 0; i < valori.length; i++) {
            sql.append("?");
            if (i < valori.length - 1) {
                sql.append(", ");
            }
        }
        sql.append(");");

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < valori.length; i++) {
                pstmt.setObject(i + 1, valori[i]);
            }
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void aggiornaRecord(String nomeTabella, String[] colonne, Object[] valori, String id) {
        StringBuilder sql = new StringBuilder("UPDATE " + nomeTabella + " SET ");
        for (int i = 0; i < colonne.length; i++) {
            sql.append(colonne[i]).append(" = ?");
            if (i < colonne.length - 1) {
                sql.append(", ");
            }
        }
        sql.append(" WHERE id = ").append(id);

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            for (Object valore : valori) {
                pstmt.setObject(index++, valore);
            }
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}