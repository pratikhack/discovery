package com.discovery.interstellar.service;


import com.discovery.interstellar.data.entities.UniversalNode;

import java.util.List;
import java.util.Set;

public interface UniversalPathwayService {

   String getShortestPath(String source , String dest);
}
