package monprojet.dao;

import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import monprojet.entity.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

@Log4j2 // Génère le 'logger' pour afficher les messages de trace
@DataJpaTest
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryDAO;

    @Test
    void lesNomsDePaysSontTousDifferents() {
        log.info("On vérifie que les noms de pays sont tous différents ('unique') dans la table 'Country'");
        
        Country paysQuiExisteDeja = new Country("XX", "France");
        try {
            countryDAO.save(paysQuiExisteDeja); // On essaye d'enregistrer un pays dont le nom existe   

            fail("On doit avoir une violation de contrainte d'intégrité (unicité)");
        } catch (DataIntegrityViolationException e) {
            // Si on arrive ici c'est normal, l'exception attendue s'est produite
        }
    }

    @Test
    @Sql("test-data.sql") // On peut charger des donnnées spécifiques pour un test
    void onSaitCompterLesEnregistrements() {
        log.info("On compte les enregistrements de la table 'Country'");
        int combienDePaysDansLeJeuDeTest = 3 + 1; // 3 dans data.sql, 1 dans test-data.sql
        long nombre = countryDAO.count();
        assertEquals(combienDePaysDansLeJeuDeTest, nombre, "On doit trouver 4 pays" );
    }

    @Test
    @Sql("test-data.sql")
    void countryPopTest (){
        log.info("On test la population de la France");
        int Truepopulation = 12+20; // 12 pour la population de Paris et 20 pour la population
        int pop = countryDAO.countryPopulation(1);
        assertEquals(Truepopulation, pop, "On doit trouver une population de 32");

    }

    @Test
    void AllCountryPopTest(){
        log.info("On compte toutes les populations");
        List<Tuple> list = countryDAO.allCountryPopulations();
        assertEquals((long) 12, list.get(0).get(1), "On doit trouver une population de 32");
        assertEquals((long)18, list.get(1).get(1), "On doit trouver une population de 18");
        assertEquals((long)27, list.get(2).get(1), "On doit trouver une population de 27");
    }



}
