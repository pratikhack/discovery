package com.discovery.interstellar.data.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity class to represent the nodes of a universal route
 */

@Entity
@Table(name = "universal_route")
public class UniverseRoute implements Serializable {

    public UniverseRoute() {}

    @EmbeddedId
    private RouteEdge routeEdge;

    public RouteEdge getRouteEdge() {
        return routeEdge;
    }

    public void setRouteEdge(RouteEdge routeEdge) {
        this.routeEdge = routeEdge;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    private double distance;
}
