CREATE TABLE IF NOT EXISTS tipo_movimento (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    valore TEXT NOT NULL
);

INSERT INTO tipo_movimento (valore)
SELECT 'entrata'
WHERE NOT EXISTS (
    SELECT 1 FROM tipo_movimento WHERE valore = 'entrata'
);

INSERT INTO tipo_movimento (valore)
SELECT 'uscita'
WHERE NOT EXISTS (
    SELECT 1 FROM tipo_movimento WHERE valore = 'uscita'
);

CREATE TABLE IF NOT EXISTS livello_importanza (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    valore TEXT NOT NULL
);

INSERT INTO livello_importanza (valore)
SELECT 'primaria'
WHERE NOT EXISTS (
    SELECT 1 FROM livello_importanza WHERE valore = 'primaria'
);

INSERT INTO livello_importanza (valore)
SELECT 'secondaria'
WHERE NOT EXISTS (
    SELECT 1 FROM livello_importanza WHERE valore = 'secondaria'
);

CREATE TABLE IF NOT EXISTS categoria_movimento (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    valore TEXT NOT NULL,
    importanza_id INTEGER,
    FOREIGN KEY (importanza_id) REFERENCES livello_importanza(id)
);

CREATE TABLE IF NOT EXISTS provenienza (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    valore TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS prodotto (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    valore TEXT NOT NULL,
    peso INTEGER
);

CREATE TABLE IF NOT EXISTS movimento_magazzino (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    tipo_movimento_id INTEGER,
    categoria_movimento_id INTEGER,
    provenienza_id INTEGER,
    prodotto_id INTEGER,
    data TEXT NOT NULL,              -- in SQLite si usa TEXT per le date in formato ISO (YYYY-MM-DD)
    valutazione INTEGER,
    valore REAL NOT NULL,
    FOREIGN KEY (tipo_movimento_id)      REFERENCES tipo_movimento(id),
    FOREIGN KEY (categoria_movimento_id)  REFERENCES categoria_movimento(id),
    FOREIGN KEY (provenienza_id)          REFERENCES provenienza(id),
    FOREIGN KEY (prodotto_id)             REFERENCES prodotto(id)
);