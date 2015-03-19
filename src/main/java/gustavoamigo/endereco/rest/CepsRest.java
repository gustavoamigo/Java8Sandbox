package gustavoamigo.endereco.rest;

import gustavoamigo.endereco.entitdades.Cep;
import gustavoamigo.endereco.persistencia.Persistence;
import gustavoamigo.endereco.servicos.CepServicos;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Gustavo
 */
@Path("ceps")
public class CepsRest {
    
    final private CepServicos cepServicos;
    
    public CepsRest() {
        cepServicos = new CepServicos(Persistence.getInstance());
    }
    
    
    @GET
    @Path("{id}")
    public Response find(@PathParam("id") String id) {
        Cep cep = cepServicos.find(id);
        if(cep == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(cep, MediaType.APPLICATION_JSON).build();
    }
    
}
