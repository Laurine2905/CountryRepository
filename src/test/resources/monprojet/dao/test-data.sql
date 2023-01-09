-- Initialisation spécifiques pour un jeu de test
INSERT INTO Country(code, name) VALUES
    ('IT', 'Italie');

INSERT INTO City (name, population, country_id) VALUES
    ('Castres', 20, SELECT id FROM Country WHERE code = 'FR');