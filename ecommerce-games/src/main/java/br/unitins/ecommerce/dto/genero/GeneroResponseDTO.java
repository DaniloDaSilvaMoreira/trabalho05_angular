package br.unitins.ecommerce.dto.genero;

import br.unitins.ecommerce.model.produto.Genero;

public record GeneroResponseDTO(Long id, String nome) {
    
    public GeneroResponseDTO (Genero genero) {

        this(genero.getId(), genero.getNome());
    }
}
