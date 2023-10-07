package br.unitins.ecommerce.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.ecommerce.application.Result;
import br.unitins.ecommerce.dto.fabricante.FabricanteDTO;
import br.unitins.ecommerce.dto.fabricante.FabricanteResponseDTO;
import br.unitins.ecommerce.service.fabricante.FabricanteService;
import jakarta.inject.Inject;
import io.quarkus.arc.DefaultBean;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/fabricantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FabricanteResource {
    
    @Inject
    FabricanteService fabricanteService;

    private static final Logger LOG = Logger.getLogger(FabricanteResource.class);

    @GET
    public List<FabricanteResponseDTO> getAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {


        LOG.info("Buscando todos os estados.");
        LOG.debug("ERRO DE DEBUG.");
        return fabricanteService.getAll(page, pageSize);
    }

    @GET
    @Path("/{id}")
    public FabricanteResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {
        LOG.infof("Buscando fabricantes por ID. ", id);
        LOG.debug("ERRO DE DEBUG.");
        return fabricanteService.getById(id);
    }

    @POST
    public Response insert(FabricanteDTO fabricanteDTO) {
        LOG.infof("Inserindo um fabricante: %s", fabricanteDTO.nome());
        Result result = null;
        try {

            return Response
                    .status(Status.CREATED) // 201
                    .entity(fabricanteService.insert(fabricanteDTO))
                    .build();

        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um fabricante.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());

        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);

        }
        return Response
                .status(Status.NOT_FOUND)
                .entity(result)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, FabricanteDTO fabricanteDTO) {
        Result result = null;
        try {
            fabricanteService.update(id, fabricanteDTO);
            LOG.infof("Fabricante (%d) atualizado com sucesso.", id);
            return Response
                    .status(Status.NO_CONTENT) // 204
                    .build();

        } catch (ConstraintViolationException e) {
            LOG.errorf("Erro ao atualizar um Fabricante. ", id, e);
            LOG.debug(e.getMessage());

            result = new Result(e.getConstraintViolations());

        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);

        }
        return Response
                .status(Status.NOT_FOUND)
                .entity(result)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException, NotFoundException {
        try {
            fabricanteService.delete(id);
            LOG.infof("fabricante excluído com sucesso.", id);
            return Response
                    .status(Status.NO_CONTENT)
                    .build();
        } catch (IllegalArgumentException e) {
            LOG.error("Erro ao deletar um fabricante: parâmetros inválidos.", e);
            throw e;
        }
    }

    @GET
    @Path("/count")
    public long count(){
        return fabricanteService.count();
    }

    @GET
    @Path("/search/{nome}/count")
    public long count(@PathParam("nome") String nome){
        return fabricanteService.countByNome(nome);
    }

    @GET
    @Path("/search/{nome}")
    public List<FabricanteResponseDTO> search(
            @PathParam("nome") String nome,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return fabricanteService.findByNome(nome, page, pageSize);

    }
}
