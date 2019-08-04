package com.discovery.interstellar.data.repositories;

import com.discovery.interstellar.data.entities.UniversalNode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterStellarNodeRepo extends JpaRepository<UniversalNode, Long> {

    public UniversalNode findUniversalNodeByNode(String node);
}
