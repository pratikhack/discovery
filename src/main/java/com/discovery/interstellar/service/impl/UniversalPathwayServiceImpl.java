package com.discovery.interstellar.service.impl;

import com.discovery.interstellar.data.entities.UniversalNode;
import com.discovery.interstellar.data.entities.UniverseRoute;
import com.discovery.interstellar.data.repositories.InterStellarNodeRepo;
import com.discovery.interstellar.data.repositories.InterStellarRouteRepo;
import com.discovery.interstellar.service.UniversalPathwayService;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UniversalPathwayServiceImpl implements UniversalPathwayService {

    @Autowired
    InterStellarRouteRepo interStellarRouteRepo;
    @Autowired
    InterStellarNodeRepo interStellarNodeRepo;

    private  Map<UniversalNode, UniversalNode> shortestPaths;
    private static Map<UniversalNode, UniversalNode> cacheShortestPaths = null;
    private static UniversalNode previousNode =null;
    @Override
    public String getShortestPath(String source, String dest) {

        UniversalNode sourceNode = interStellarNodeRepo.findUniversalNodeByNode(source);
        UniversalNode destNode = interStellarNodeRepo.findUniversalNodeByNode(dest);
        //fetching cached results first.

        // TODO validate cache.
        if(cacheShortestPaths!=null && previousNode==sourceNode) {
            return createShortestPathBetweenSourceAndDest(cacheShortestPaths, destNode);
        }
        shortestPaths = new LinkedHashMap<>();
        List<UniversalNode> unvisited = interStellarNodeRepo.findAll();
        int noOfNodes = unvisited.size();
        Set<UniversalNode> unvisitedSet = new LinkedHashSet<>(unvisited);

        //Empty visited set initially
        Set<UniversalNode> visitedSet = new LinkedHashSet<>();

        // Add source to the visited set
        visitedSet.add(sourceNode);
        shortestPaths.put(sourceNode, null);
        // Initialize distance nodes.
        Map<NodeDistance, Double> nodeDistanceMap = initializeDistanceNodes(unvisitedSet, sourceNode);
        unvisited.remove(sourceNode);

        UniversalNode currentNode = sourceNode;

        // iterate till we have finished traversing all the  nodes
        while (visitedSet.size() != noOfNodes && unvisitedSet.size() != 0) {

            List<UniverseRoute> adjUniversalRoutesByDest;
            List<UniverseRoute> adjUniversalRoutesByOrigin;

            Set<UniverseRoute> adjUniversalRouteSet = calculateNeighboursForGivenNode(currentNode);

            // create an adjacent universal node set from the adjacent universal node route set
            Set<UniversalNode> adjNodeSet = extractAdjacentNodeSetFromRouteSet(adjUniversalRouteSet, visitedSet, currentNode);

            // traverse adjacent nodes and update distance
            Iterator<UniversalNode> adjNodeIterator = adjNodeSet.iterator();

            while (adjNodeIterator.hasNext()) {
                UniversalNode neighbourNode = adjNodeIterator.next();

                UniverseRoute fromCurrentToNeighbourRoute = interStellarRouteRepo.findUniverseRouteByRouteEdgeDestAndRouteEdgeOrigin(neighbourNode, currentNode);
                if (fromCurrentToNeighbourRoute == null) {
                    fromCurrentToNeighbourRoute = interStellarRouteRepo.findUniverseRouteByRouteEdgeDestAndRouteEdgeOrigin(currentNode, neighbourNode);
                }
                assert (fromCurrentToNeighbourRoute != null);
                // create a new node distance object from origin and dest nodes

                NodeDistance fromSourceToCurrentHopDistance = new NodeDistance(sourceNode, currentNode);
                NodeDistance fromSourceToNeighbourDistance = new NodeDistance(sourceNode, neighbourNode);

                double currentDist = nodeDistanceMap.get(fromSourceToCurrentHopDistance) + fromCurrentToNeighbourRoute.getDistance();

                if (currentDist < nodeDistanceMap.get(fromSourceToNeighbourDistance)) {
                    nodeDistanceMap.put(fromSourceToNeighbourDistance, currentDist);
                  shortestPaths.put(neighbourNode, currentNode);
                }
            }

            UniversalNode auxNode = calculateMinimumFromUnvisitedNodes(nodeDistanceMap, visitedSet, sourceNode);

            currentNode = auxNode;
            visitedSet.add(currentNode);
            unvisitedSet.remove(currentNode);
        }

        cacheShortestPaths = shortestPaths;
        previousNode = sourceNode;
        return createShortestPathBetweenSourceAndDest(cacheShortestPaths, destNode);

    }

    private String createShortestPathBetweenSourceAndDest(Map<UniversalNode, UniversalNode> shortestPaths, UniversalNode destNode) {

        if (shortestPaths.get(destNode) == null) {
            return destNode.getNode();
        }
        return createShortestPathBetweenSourceAndDest(shortestPaths, shortestPaths.get(destNode)) + "->"  + destNode.getNode()  ;
    }

    // TODO make more efficient but relational DB is a constraint , graph DBs are awesome
    private UniversalNode calculateMinimumFromUnvisitedNodes(Map<NodeDistance, Double> nodeDistanceMap, Set<UniversalNode> visitedSet, UniversalNode sourceNode) {

        double dist = Double.MAX_VALUE;
        UniversalNode minimumDistNode = null;
        UniversalNode node = null;
        Set<Map.Entry<NodeDistance, Double>> entries = nodeDistanceMap.entrySet();
        for (Map.Entry<NodeDistance, Double> entry : entries) {
            NodeDistance n = entry.getKey();
            if (n != null && n.getNodeA() == sourceNode) {
                node = n.getNodeB();
            }
            if (n != null && n.getNodeB() == sourceNode) {
                node = n.getNodeA();
            }
            if (visitedSet.contains(node)) {
                continue;
            }

            if (minimumDistNode == null) {
                minimumDistNode = node;
            }
            if (entry.getValue() < dist) {
                dist = entry.getValue();
                minimumDistNode = node;
            }
        }

        return minimumDistNode;

    }

    private Set<UniversalNode> extractAdjacentNodeSetFromRouteSet(Set<UniverseRoute> adjUniversalRouteSet, Set<UniversalNode> visitedSet, UniversalNode universalNode) {
        Iterator<UniverseRoute> routeSetIterator = adjUniversalRouteSet.iterator();
        Set<UniversalNode> universalNodes = new HashSet<>();
        while (routeSetIterator.hasNext()) {

            UniverseRoute route = routeSetIterator.next();
            // To avoid the current node getting added to neighbour list
            if (route.getRouteEdge().getOrigin().equals(universalNode) && !(visitedSet.contains(route.getRouteEdge().getDest()))) {
                universalNodes.add(route.getRouteEdge().getDest());
            }
            if (route.getRouteEdge().getDest().equals(universalNode) && !(visitedSet.contains(route.getRouteEdge().getOrigin()))) {
                universalNodes.add(route.getRouteEdge().getOrigin());
            }
        }
        return universalNodes;
    }

    private Map<NodeDistance, Double> initializeDistanceNodes(Set<UniversalNode> unvisitedSet, UniversalNode source) {

        Map<NodeDistance, Double> nodeDistanceMap = new HashMap<>();
        Iterator<UniversalNode> nodeDistanceIterator = unvisitedSet.iterator();

        while (nodeDistanceIterator.hasNext()) {

            UniversalNode node = nodeDistanceIterator.next();
            NodeDistance nodeDistance;

            // Create a new Node Distance and add to nodeDistanceSet if not present.
            nodeDistance = new NodeDistance(source, node);
            if (node.equals(source)) {
                nodeDistance.setDistance(0d);
            }
            nodeDistanceMap.put(nodeDistance, nodeDistance.getDistance());
        }
        return nodeDistanceMap;
    }

    // TODO make more efficient through caching
    private Set<UniverseRoute> calculateNeighboursForGivenNode(UniversalNode node) {


        List<UniverseRoute> adjUniversalRoutesByDest = interStellarRouteRepo.findUniverseRouteByRouteEdgeDest(node);
        List<UniverseRoute> adjUniversalRoutesByOrigin = interStellarRouteRepo.findUniverseRouteByRouteEdgeOrigin(node);

        Set<UniverseRoute> adjUniversalRouteSet = new HashSet<>(adjUniversalRoutesByDest);
        adjUniversalRouteSet.addAll(adjUniversalRoutesByOrigin);
        return adjUniversalRouteSet;
    }
}
