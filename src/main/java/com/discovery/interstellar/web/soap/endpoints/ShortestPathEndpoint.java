package com.discovery.interstellar.web.soap.endpoints;

import com.discovery.interstellar.service.UniversalPathwayService;
import com.discovery.interstellar.web.soap.domain.GetShortestPathRequest;
import com.discovery.interstellar.web.soap.domain.GetShortestPathResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ShortestPathEndpoint {

    private static final String NAMESPACE_URI = "http://interstellar.discovery.com/web/soap/domain";
    @Autowired
    UniversalPathwayService universalPathwayService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getShortestPathRequest")
    @ResponsePayload
    public GetShortestPathResponse getShortestPathRequest(@RequestPayload GetShortestPathRequest request) {
        GetShortestPathResponse response = new GetShortestPathResponse();
        response.setName(universalPathwayService.getShortestPath(request.getSource(),request.getDest()));
        return response;
    }


}
