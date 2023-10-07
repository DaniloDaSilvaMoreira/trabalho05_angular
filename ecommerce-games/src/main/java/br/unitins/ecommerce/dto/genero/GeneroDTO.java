package br.unitins.ecommerce.dto.genero;

import jakarta.validation.constraints.NotBlank;

public record GeneroDTO (
    
    @NotBlank(message = "Campo nome não pode estar vazio")
    String nome
) {
    
}
