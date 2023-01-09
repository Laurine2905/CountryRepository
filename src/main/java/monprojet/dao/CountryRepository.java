package monprojet.dao;

import jakarta.persistence.Tuple;
import monprojet.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring 

public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Query("select sum(ci.population) "
            + "from Country co "
            + "inner join City ci on ci.country = co.id "
            + "where co.id = :id")
    public int countryPopulation(@Param("id") int id);

    @Query("select cou.name, (select sum(ci.population) "
            + "from Country co "
            + "inner join City ci on ci.country = co.id "
            + "where co.id = cou.id) "
            + "from Country cou "
            + "order by cou.name")
    public List<Tuple> allCountryPopulations();

}
