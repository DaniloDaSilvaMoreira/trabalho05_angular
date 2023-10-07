package br.unitins.ecommerce.service.plataforma;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.ecommerce.dto.plataforma.PlataformaDTO;
import br.unitins.ecommerce.dto.plataforma.PlataformaResponseDTO;
import br.unitins.ecommerce.model.produto.Plataforma;
import br.unitins.ecommerce.repository.FabricanteRepository;
import br.unitins.ecommerce.repository.PlataformaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PlataformaImplService implements PlataformaService {

    @Inject
    Validator validator;

    @Inject
    PlataformaRepository plataformaRepository;

    @Inject
    FabricanteRepository fabricanteRepository;

    private DateTimeFormatter formatterGetAll = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DateTimeFormatter formatterGetById = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<PlataformaResponseDTO> getAll(int page, int pageSize) {

         List<Plataforma> list = plataformaRepository.findAll().page(page, pageSize).list();
        return list.stream().map(plataforma -> new PlataformaResponseDTO(plataforma, formatterGetAll)).collect(Collectors.toList());
    }

    @Override
    public PlataformaResponseDTO getById(Long id) {
        
        Plataforma plataforma = plataformaRepository.findById(id);

        if (plataforma == null)
            throw new NotFoundException("Não encontrado");

        return new PlataformaResponseDTO(plataforma, formatterGetById);
    }

    @Override
    @Transactional
    public PlataformaResponseDTO insert(PlataformaDTO plataformaDTO) {
        
        validar(plataformaDTO);

        Plataforma plataforma = new Plataforma();

        plataforma.setNome(plataformaDTO.nome());

        plataforma.setDescricao(plataformaDTO.descricao());

        plataforma.setAnoLancamento(plataformaDTO.anoLancamento());

        plataforma.setFabricante(fabricanteRepository.findById(plataformaDTO.fabricante()));

        plataformaRepository.persist(plataforma);

        return new PlataformaResponseDTO(plataforma);
    }

    @Override
    @Transactional
    public PlataformaResponseDTO update(Long id, PlataformaDTO plataformaDTO) {
        
        validar(plataformaDTO);

        Plataforma plataforma = plataformaRepository.findById(id);

        if (plataforma == null)
            throw new NotFoundException("Número fora das opções disponíveis");

        plataforma.setNome(plataformaDTO.nome());

        plataforma.setDescricao(plataformaDTO.descricao());

        plataforma.setAnoLancamento(plataformaDTO.anoLancamento());

        plataforma.setFabricante(fabricanteRepository.findById(plataformaDTO.fabricante()));

        return new PlataformaResponseDTO(plataforma);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Plataforma plataforma = plataformaRepository.findById(id);

        if (plataformaRepository.isPersistent(plataforma))
            plataformaRepository.delete(plataforma);

        else
            throw new NotFoundException("Nenhuma plataforma encontrado");
    }
    
    private void validar(PlataformaDTO plataformaDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<PlataformaDTO>> violations = validator.validate(plataformaDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    public List<PlataformaResponseDTO> findByNome(String nome, int page, int pageSize) {
        List<Plataforma> list = plataformaRepository.findByNome(nome).page(page, pageSize).list();
        return list.stream().map(PlataformaResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return plataformaRepository.count();
    }

    @Override
    public long countByNome(String nome) {
        return plataformaRepository.findByNome(nome).count();
    }


}
