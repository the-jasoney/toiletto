package io.github.the_jasoney.toiletto.repository;


import org.locationtech.jts.geom.Point;
import io.github.the_jasoney.toiletto.entity.Toilet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToiletRepository extends JpaRepository<Toilet, String> {
    @Query(value =
        "SELECT * FROM toilets " +
        "WHERE ST_Distance(location, :p) < :distanceM " +
        "ORDER BY ST_DistanceSphere(location, :p) DESC " +
        "LIMIT :take OFFSET :skip"
        , nativeQuery = true
    )
    List<Toilet> findNearWithinDistance(Point p, double distanceM, int skip, int take);

    boolean existsByLocation(Point location);
}
