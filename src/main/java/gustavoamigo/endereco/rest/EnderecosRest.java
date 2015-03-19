package gustavoamigo.endereco.rest;

import gustavoamigo.endereco.entitdades.Endereco;
import gustavoamigo.endereco.persistencia.Persistence;
import gustavoamigo.endereco.servicos.CepNotFoundException;
import gustavoamigo.endereco.servicos.CepServicos;
import gustavoamigo.endereco.servicos.EnderecoServicos;
import java.util.List;
import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.validation.ValidationError;

/**
 * @author Gustavo
 */
@Stateless
@Path("enderecos")
@Consumes({MediaType.APPLICATION_JSON}) 
public class EnderecosRest{

    final private EnderecoServicos enderecoServicos;

    public EnderecosRest() {
        enderecoServicos = new EnderecoServicos(Persistence.getInstance()
                , new CepServicos(Persistence.getInstance()));
    }

    @POST
    public Response create(@Valid Endereco endereco) {
        try {
            enderecoServicos.create(endereco);
        } catch (CepNotFoundException cnfe) {
            ValidationError[] errors = {new ValidationError(cnfe.getMessage(), null, null, null)};
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }        
        return Response.ok(endereco, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("{id}")
    public Response edit( @PathParam("id") Long id,@Valid Endereco entity) {
        try {
            enderecoServicos.edit(id, entity);
        } catch (CepNotFoundException cnfe) {
            ValidationError[] errors = {new ValidationError(cnfe.getMessage(), null, null, null)};
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        enderecoServicos.remove(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("{id}")
    public Response find(@PathParam("id") Long id) {
        Endereco endereco = enderecoServicos.find(id);
        if(endereco == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(endereco, MediaType.APPLICATION_JSON).build();
    }

    @GET
    public Response findAll() {
        List<Endereco> enderecos = enderecoServicos.findAll();
        return Response.ok(enderecos, MediaType.APPLICATION_JSON).build();
    }
    
}
