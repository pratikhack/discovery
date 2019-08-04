package com.discovery.interstellar.web.rest.controllers;

import com.discovery.interstellar.data.entities.UniversalNode;
import com.discovery.interstellar.service.UniversalPathwayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/paths/")
public class PathController {

    @Autowired
    UniversalPathwayService universalPathwayService;
    @RequestMapping("/getShortestPath")
    public String getShortestPath(@RequestParam String source, @RequestParam String dest){
       return universalPathwayService.getShortestPath(source, dest);
    }
}
