package br.unitins.ecommerce.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.ecommerce.application.Result;
import io.quarkus.arc.DefaultBean;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import br.unitins.ecommerce.dto.plataforma.PlataformaDTO;
import br.unitins.ecommerce.dto.plataforma.PlataformaResponseDTO;
import br.unitins.ecommerce.service.plataforma.PlataformaService;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/plataformas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlataformaResource {
    
    @Inject
    PlataformaService plataformaService;

    private static final Logger LOG = Logger.getLogger(PlataformaResource.class);

    @GET
    public List<PlataformaResponseDTO> getAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {

        LOG.info("Buscando todos as Plataformas.");
        LOG.debug("ERRO DE DEBUG.");
        return plataformaService.getAll(page, pageSize);
    }

    @GET
    @Path("/{id}")
    public PlataformaResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {
        LOG.info("Buscando plataforma por ID: " + id);
        LOG.debug("ERRO DE DEBUG.");
        return plataformaService.getById(id);
    }

    @POST
    public Response insert(PlataformaDTO plataformaDTO) {
        LOG.infof("Inserindo uma Plataforma: %s", plataformaDTO.nome());

        Result result = null;

        try {
            PlataformaResponseDTO plataforma = plataformaService.insert(plataformaDTO);

            LOG.infof("Plataforma (%d) criado com sucesso.", plataforma.id());

            return Response.status(Status.CREATED).entity(plataforma).build();

        } catch (ConstraintViolationException e) {

            LOG.error("Erro ao incluir uma Plataforma.");

            LOG.debug(e.getMessage());

            result = new Result(e.getConstraintViolations());

        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());

            result = new Result(e.getMessage(), false);
        }
        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, PlataformaDTO plataformaDTO) {
        Result result = null;
        
        try {
            plataformaService.update(id, plataformaDTO);
            LOG.infof("Plataforma (%d) atualizado com sucesso.", id);
            return Response
                    .status(Status.NO_CONTENT) // 204
                    .build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro de validação ao atualizar a Plataforma.", e);
            LOG.debug(e.getMessage());

            result = new Result(e.getConstraintViolations());

        } catch (Exception e) {
            LOG.fatal("Erro ao atualizar a Plataforma " + id + ".", e);
            result = new Result(e.getMessage(), false);
    
        }
        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException, NotFoundException {

        try {
            plataformaService.delete(id);
            LOG.infof("Plataforma (%d) excluído com sucesso.", id);
            return Response
                    .status(Status.NO_CONTENT)
                    .build();
        } catch (IllegalArgumentException e) {
            LOG.error("Erro ao deletar Plataforma: parâmetros inválidos.", e);
            throw e;
        } catch (NotFoundException e) {
            LOG.errorf("Plataforma (%d) não encontrado.", id);
            throw e;
        }
    }

    @GET
    @Path("/count")
    public long count(){
        return plataformaService.count();
    }

    @GET
    @Path("/search/{nome}/count")
    public long count(@PathParam("nome") String nome){
        return plataformaService.countByNome(nome);
    }

    @GET
    @Path("/search/{nome}")
    public List<PlataformaResponseDTO> search(
        @PathParam("nome") String nome,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
    return plataformaService.findByNome(nome, page, pageSize);

    }
}
