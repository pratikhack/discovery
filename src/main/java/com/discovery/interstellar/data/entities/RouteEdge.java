package com.discovery.interstellar.data.entities;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class RouteEdge implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="origin")
    private UniversalNode origin;

    public UniversalNode getOrigin() {
        return origin;
    }

    public void setOrigin(UniversalNode origin) {
        this.origin = origin;
    }

    public UniversalNode getDest() {
        return dest;
    }

    public void setDest(UniversalNode dest) {
        this.dest = dest;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="dest")
    private UniversalNode dest;



    public RouteEdge() {}

    public RouteEdge(UniversalNode origin, UniversalNode dest) {

        this.origin = origin;
        this.dest = dest;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((origin == null) ? 0 : origin.hashCode())
        + ((dest == null) ? 0 : dest.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RouteEdge other = (RouteEdge) obj;
        if (origin == null) {
            if (other.origin != null)
                return false;
        } else if (!origin.equals(other.origin))
            return false;
        if (dest != other.dest)
            return false;
        return true;
    }
}
