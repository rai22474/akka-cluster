package io.wasupu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

@Service
@Path("/api/connections")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConnectionsRest {

    @GET
    public Response getAll() throws ExecutionException, InterruptedException {
        ClusterStatus clusterStatus = connectionsRepository.get();
        return Response.ok("{\"connections\":" + clusterStatus.getNumberOfConnections() + ",\"id\":\"" + clusterStatus.getId() + "\"}").build();
    }

    @POST
    public Response create() {
        connectionsRepository.add();
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    public Response delete(){
        connectionsRepository.delete();
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Autowired
    private ConnectionsRepository connectionsRepository;

}
