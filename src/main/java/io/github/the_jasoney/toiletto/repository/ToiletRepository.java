package io.github.the_jasoney.toiletto.repository;


import com.vividsolutions.jts.geom.Point;
import io.github.the_jasoney.toiletto.component.Toilet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToiletRepository extends JpaRepository<Toilet, String> {
    @Query(value = "SELECT * FROM toilets WHERE ST_DistanceSphere(location, :p) < :distanceM ORDER BY ST_Distance(location, :p) DESC", nativeQuery = true)
    List<Toilet> findNearWithinDistance(Point p, double distanceM);
}
