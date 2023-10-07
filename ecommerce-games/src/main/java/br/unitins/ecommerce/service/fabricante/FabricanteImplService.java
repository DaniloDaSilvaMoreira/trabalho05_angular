package br.unitins.ecommerce.service.fabricante;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import br.unitins.ecommerce.dto.fabricante.FabricanteDTO;
import br.unitins.ecommerce.dto.fabricante.FabricanteResponseDTO;
import br.unitins.ecommerce.model.produto.Fabricante;
import br.unitins.ecommerce.repository.FabricanteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class FabricanteImplService implements FabricanteService {

    @Inject
    Validator validator;

    @Inject
    FabricanteRepository fabricanteRepository;

    private DateTimeFormatter formatterGetAll = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DateTimeFormatter formatterGetById = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<FabricanteResponseDTO> getAll(int page, int pageSize) {

        List<Fabricante> list = fabricanteRepository.findAll().page(page, pageSize).list();
        return list.stream().map(fabricante -> new FabricanteResponseDTO(fabricante, formatterGetAll)).collect(Collectors.toList());
    }

    @Override
    public FabricanteResponseDTO getById(Long id) {
        
        Fabricante fabricante = fabricanteRepository.findById(id);

        if (fabricante == null)
            throw new NotFoundException("Não encontrado");

        return new FabricanteResponseDTO(fabricante, formatterGetById);
    }

    @Override
    @Transactional
    public FabricanteResponseDTO insert(FabricanteDTO fabricanteDTO) {
        
        validar(fabricanteDTO);

        Fabricante fabricante = new Fabricante();

        fabricante.setNome(fabricanteDTO.nome());

        fabricante.setAnoFundacao(fabricanteDTO.anoFundacao());

        fabricanteRepository.persist(fabricante);

        return new FabricanteResponseDTO(fabricante);
    }

    @Override
    @Transactional
    public FabricanteResponseDTO update(Long id, FabricanteDTO fabricanteDTO) {
        
        validar(fabricanteDTO);

        Fabricante fabricante = fabricanteRepository.findById(id);

        if (fabricante == null)
            throw new NotFoundException("Número fora das opções disponíveis");

        fabricante.setNome(fabricanteDTO.nome());

        fabricante.setAnoFundacao(fabricanteDTO.anoFundacao());

        return new FabricanteResponseDTO(fabricante);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Fabricante fabricante = fabricanteRepository.findById(id);

        if (fabricanteRepository.isPersistent(fabricante))
            fabricanteRepository.delete(fabricante);

        else
            throw new NotFoundException("Nenhum fabricante encontrado");
    }

    public List<FabricanteResponseDTO> findByNome(String nome, int page, int pageSize) {
        List<Fabricante> list = fabricanteRepository.findByNome(nome).page(page, pageSize).list();
        return list.stream().map(FabricanteResponseDTO::new).collect(Collectors.toList());
    }
    
    private void validar(FabricanteDTO fabricanteDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<FabricanteDTO>> violations = validator.validate(fabricanteDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    public long count() {
        return fabricanteRepository.count();
    }

    @Override
    public long countByNome(String nome) {
        return fabricanteRepository.findByNome(nome).count();
    }


}
