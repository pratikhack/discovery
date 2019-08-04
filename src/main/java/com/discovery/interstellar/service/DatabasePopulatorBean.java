package com.discovery.interstellar.service;

import com.discovery.interstellar.data.entities.RouteEdge;
import com.discovery.interstellar.data.entities.UniversalNode;
import com.discovery.interstellar.data.entities.UniverseRoute;
import com.discovery.interstellar.data.repositories.InterStellarNodeRepo;
import com.discovery.interstellar.data.repositories.InterStellarRouteRepo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
@Transactional
public class DatabasePopulatorBean {

    @Autowired
    InterStellarNodeRepo interStellarNodeRepo;
    @Autowired
    InterStellarRouteRepo interStellarRouteRepo;

    DataFormatter formatter = new DataFormatter();

    @PostConstruct
    public void init() throws IOException, InvalidFormatException {

        Workbook workbook = WorkbookFactory.create(new ClassPathResource("WorkSheet.xlsx").getInputStream());
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        //Persist the planet ids and name data from excel

        Sheet planetnames = workbook.getSheet("Planet Names");
        planetnames.forEach(row -> {
            if (row.getRowNum() == 0) {
                //header row do  nothing
            } else {
                UniversalNode node = new UniversalNode();
                node.setNode(formatter.formatCellValue(row.getCell(0)));
                node.setNodeName(formatter.formatCellValue(row.getCell(1)));
                interStellarNodeRepo.save(node);
            }
        });

        // persist the route between planet and their weight from excel

       persistUniverseRoutes(workbook.getSheet("Routes"));

      /*  interStellarRouteRepo.findAll().forEach(s -> System.out.println(s.getRouteEdge().getOrigin().getNode()
                + s.getRouteEdge().getDest().getNode() +
                " " + s.getDistance()));*/
    }

    private void persistUniverseRoutes(Sheet sheet) {

        sheet.forEach(row -> {
            if (row.getRowNum() == 0) {
                //header row do  nothing
            } else {


                String nodeOrigin = formatter.formatCellValue(row.getCell(1));
                UniversalNode origin = interStellarNodeRepo.findUniversalNodeByNode(nodeOrigin);
                String nodeDest = formatter.formatCellValue(row.getCell(2));
                UniversalNode dest = interStellarNodeRepo.findUniversalNodeByNode(nodeDest);
                // check if the route already exists
                UniverseRoute route = interStellarRouteRepo.findUniverseRouteByRouteEdgeDestAndRouteEdgeOrigin(origin,dest);
                if(route != null || origin.equals(dest)) {
                          // DO NOTHING ENTRY EXISTS
                }

                 else {

                    RouteEdge routeEdge = new RouteEdge();
                    routeEdge.setOrigin(origin);
                    routeEdge.setDest(dest);
                    route = new UniverseRoute();
                    route.setRouteEdge(routeEdge);
                    route.setDistance(Double.parseDouble(formatter.formatCellValue(row.getCell(3))));
                    interStellarRouteRepo.save(route);
                }
            }
        });
    }
}

