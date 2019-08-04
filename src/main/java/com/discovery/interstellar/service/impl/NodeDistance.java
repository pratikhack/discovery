package com.discovery.interstellar.service.impl;

import com.discovery.interstellar.data.entities.UniversalNode;

public class NodeDistance {

    private final UniversalNode nodeA;
    private final UniversalNode nodeB;
    double distance;

    public UniversalNode getNodeB() {
        return nodeB;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public UniversalNode getNodeA() {
        return nodeA;
    }

    public NodeDistance(UniversalNode nodeA, UniversalNode nodeB) {

        this.nodeA = nodeA;
        this.nodeB = nodeB;
        //Initialize the value to max value permitted to denote infinite distance at beginning.
        this.distance = Double.MAX_VALUE;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((nodeA == null) ? 0 : nodeA.hashCode())
        +((nodeB == null) ? 0 : nodeB.hashCode());
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
        NodeDistance other = (NodeDistance) obj;
        if (nodeA == null) {
            if (other.nodeA != null)
                return false;
        } else if (!nodeA.equals(other.nodeA) && !(nodeA.equals(other.nodeB)))
            return false;

        if (nodeB == null) {
            if (other.nodeB != null)
                return false;
        } else if (!nodeB.equals(other.nodeB) && !nodeB.equals(other.nodeA) )
            return false;
        return true;
    }
}
