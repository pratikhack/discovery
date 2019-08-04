package com.discovery.interstellar.data.repositories;

import com.discovery.interstellar.data.entities.UniversalNode;
import com.discovery.interstellar.data.entities.UniverseRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterStellarRouteRepo extends JpaRepository<UniverseRoute, Long> {

    public UniverseRoute findUniverseRouteByRouteEdgeDestAndRouteEdgeOrigin(UniversalNode origin, UniversalNode dest);
    public List<UniverseRoute> findUniverseRouteByRouteEdgeDest(UniversalNode dest);
    public List<UniverseRoute> findUniverseRouteByRouteEdgeOrigin(UniversalNode origin);

}
