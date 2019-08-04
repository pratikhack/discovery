package com.discovery.interstellar.data.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "universal_node")
public class UniversalNode implements Serializable {

    /**
     * Default Node
     */
    public UniversalNode(){}

    /** All args constructor
     *
     */
    public UniversalNode(String node, String nodeName) {
        this.node = node;
        this.nodeName = nodeName;
    }

    @Id
    @Column(name="node")
    private String node;

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @Column(name = "node_name")
    private String nodeName;

    public String getNode() {
        return node;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((node == null) ? 0 : node.hashCode());
        return result;
    }

    /* TODO Should doe these boilerplate stuff through third party libs like Lombok.But,
     Lombok has some issues with jackson libraries so serialization can be an issue at time*/

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UniversalNode other = (UniversalNode) obj;
        if (node == null) {
            if (other.node != null)
                return false;
        } else if (!node.equals(other.node))
            return false;
        return true;
    }
    public void setNode(String node) {
        this.node = node;
    }

    public String getNodeName() {
        return nodeName;
    }

}
