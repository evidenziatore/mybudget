package com.evidenziatore.mybudget.database;

import com.evidenziatore.mybudget.database.entity.*;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
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
            if (!folder.exists()) {
                boolean created = folder.mkdirs();
                if (created) {
                    System.out.println("Cartella database creata: " + DB_FOLDER);
                } else {
                    System.err.println("Errore nella creazione della cartella database.");
                }
            }

            try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
                // Creazione della tabella tipologia
                String sqlTipologia = """
                CREATE TABLE IF NOT EXISTS tipologia (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    valore TEXT NOT NULL
                );
            """;
                stmt.execute(sqlTipologia);

                // Inserimento dei valori 'entrata' e 'uscita' nella tabella tipologia solo se non esistono
                String insertEntrata = """
                INSERT INTO tipologia (valore)
                SELECT 'entrata' WHERE NOT EXISTS (SELECT 1 FROM tipologia WHERE valore = 'entrata');
            """;
                String insertUscita = """
                INSERT INTO tipologia (valore)
                SELECT 'uscita' WHERE NOT EXISTS (SELECT 1 FROM tipologia WHERE valore = 'uscita');
            """;
                stmt.executeUpdate(insertEntrata);
                stmt.executeUpdate(insertUscita);

                // Creazione della tabella importanza
                String sqlImportanza = """
                CREATE TABLE IF NOT EXISTS importanza (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    valore TEXT NOT NULL
                );
            """;
                stmt.execute(sqlImportanza);

                // Inserimento dei valori 'primaria' e 'secondaria' nella tabella importanza solo se non esistono
                String insertPrimaria = """
                INSERT INTO importanza (valore)
                SELECT 'primaria' WHERE NOT EXISTS (SELECT 1 FROM importanza WHERE valore = 'primaria');
            """;
                String insertSecondaria = """
                INSERT INTO importanza (valore)
                SELECT 'secondaria' WHERE NOT EXISTS (SELECT 1 FROM importanza WHERE valore = 'secondaria');
            """;
                stmt.executeUpdate(insertPrimaria);
                stmt.executeUpdate(insertSecondaria);

                // Creazione della tabella categoria (con chiave esterna verso importanza)
                String sqlCategoria = """
                CREATE TABLE IF NOT EXISTS categoria (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    valore TEXT NOT NULL,
                    importanza_id INTEGER,
                    FOREIGN KEY (importanza_id) REFERENCES importanza(id)
                );
            """;
                stmt.execute(sqlCategoria);

                // Creazione della tabella provenienza
                String sqlProvenienza = """
                CREATE TABLE IF NOT EXISTS provenienza (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    valore TEXT NOT NULL
                );
            """;
                stmt.execute(sqlProvenienza);

                // Creazione della tabella prodotto
                String sqlProdotto = """
                CREATE TABLE IF NOT EXISTS prodotto (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    valore TEXT NOT NULL
                );
            """;
                stmt.execute(sqlProdotto);

                // Creazione della tabella movimento (con la colonna data di tipo DATE)
                String sqlMovimento = """
                CREATE TABLE IF NOT EXISTS movimento (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    tipologia_id INTEGER,
                    categoria_id INTEGER,
                    provenienza_id INTEGER,
                    prodotto_id INTEGER,
                    data DATE NOT NULL,  -- Aggiunta della colonna 'data' di tipo DATE
                    valutazione INTEGER,
                    FOREIGN KEY (tipologia_id) REFERENCES tipologia(id),
                    FOREIGN KEY (categoria_id) REFERENCES categoria(id),
                    FOREIGN KEY (provenienza_id) REFERENCES provenienza(id),
                    FOREIGN KEY (prodotto_id) REFERENCES prodotto(id)
                );
            """;
                stmt.execute(sqlMovimento);

                System.out.println("Database pronto: " + DB_PATH);
            }

        } catch (SQLException e) {
            System.err.println("Errore di inizializzazione database:");
            e.printStackTrace();
        }
    }

    public static List<Movimento> getAllMovimentiCompletamente() {
        List<Movimento> movimenti = new ArrayList<>();

        String sql = """
            SELECT 
                m.id AS movimento_id,
                m.data,
                m.valutazione,
                t.id AS tipologia_id, t.valore AS tipologia_valore,
                c.id AS categoria_id, c.valore AS categoria_valore,
                i.id AS importanza_id, i.valore AS importanza_valore,
                p.id AS provenienza_id, p.valore AS provenienza_valore,
                pr.id AS prodotto_id, pr.valore AS prodotto_valore
            FROM movimento m
            INNER JOIN tipologia t ON m.tipologia_id = t.id
            INNER JOIN categoria c ON m.categoria_id = c.id
            INNER JOIN importanza i ON c.importanza_id = i.id
            INNER JOIN provenienza p ON m.provenienza_id = p.id
            INNER JOIN prodotto pr ON m.prodotto_id = pr.id
            """;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Costruisci gli oggetti figli
                Tipologia tipologia = new Tipologia(
                        rs.getInt("tipologia_id"),
                        rs.getString("tipologia_valore")
                );

                Importanza importanza = new Importanza(
                        rs.getInt("importanza_id"),
                        rs.getString("importanza_valore")
                );

                Categoria categoria = new Categoria(
                        rs.getInt("categoria_id"),
                        rs.getString("categoria_valore"),
                        importanza
                );

                Provenienza provenienza = new Provenienza(
                        rs.getInt("provenienza_id"),
                        rs.getString("provenienza_valore")
                );

                Prodotto prodotto = new Prodotto(
                        rs.getInt("prodotto_id"),
                        rs.getString("prodotto_valore")
                );

                // Ora crea il movimento completo
                Movimento movimento = new Movimento(
                        rs.getInt("movimento_id"),
                        tipologia,
                        categoria,
                        provenienza,
                        prodotto,
                        rs.getDate("data"),
                        rs.getInt("valutazione")
                );

                movimenti.add(movimento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movimenti;
    }

}