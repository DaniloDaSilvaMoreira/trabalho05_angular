package br.unitins.ecommerce.service.genero;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.ecommerce.dto.genero.GeneroDTO;
import br.unitins.ecommerce.dto.genero.GeneroResponseDTO;

import br.unitins.ecommerce.model.produto.Genero;
import br.unitins.ecommerce.repository.GeneroRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class GeneroImplService implements GeneroService {

    @Inject
    Validator validator;

    @Inject
    GeneroRepository generoRepository;

    @Override
    public List<GeneroResponseDTO> getAll(int page, int pageSize) {

        List<Genero> list = generoRepository.findAll().page(page, pageSize).list();
        return list.stream().map(genero -> new GeneroResponseDTO(genero)).collect(Collectors.toList());
    }

    @Override
    public GeneroResponseDTO getById(Long id) {
        
        Genero genero = generoRepository.findById(id);

        if (genero == null)
            throw new NotFoundException("Não encontrado");

            return new GeneroResponseDTO(genero);
    }

    @Override
    @Transactional
    public GeneroResponseDTO insert(GeneroDTO generoDTO) {
        
        validar(generoDTO);

        Genero genero = new Genero();

        genero.setNome(generoDTO.nome());

        generoRepository.persist(genero);

        return new GeneroResponseDTO(genero);
    }

    @Override
    @Transactional
    public GeneroResponseDTO update(Long id, GeneroDTO generoDTO) {
        
        validar(generoDTO);

        Genero genero = generoRepository.findById(id);

        if (genero == null)
            throw new NotFoundException("Número fora das opções disponíveis");

        genero.setNome(generoDTO.nome());

        return new GeneroResponseDTO(genero);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Genero genero = generoRepository.findById(id);

        if (generoRepository.isPersistent(genero))
            generoRepository.delete(genero);

        else
            throw new NotFoundException("Nenhum genero encontrado");
    }
    
    private void validar(GeneroDTO generoDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<GeneroDTO>> violations = validator.validate(generoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    public List<GeneroResponseDTO> findByNome(String nome, int page, int pageSize) {
        List<Genero> list = generoRepository.findByNome(nome).page(page, pageSize).list();
         return list.stream().map(GeneroResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return generoRepository.count();
    }

    @Override
    public long countByNome(String nome) {
        return generoRepository.findByNome(nome).count();
    }
}
