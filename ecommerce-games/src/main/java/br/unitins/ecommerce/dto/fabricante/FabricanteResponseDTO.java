package br.unitins.ecommerce.dto.fabricante;

import java.time.format.DateTimeFormatter;

import br.unitins.ecommerce.model.produto.Fabricante;

public record FabricanteResponseDTO(Long id, String nome, String anoFundacao) {
    
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public FabricanteResponseDTO (Fabricante fabricante, DateTimeFormatter formatter) {

        this(fabricante.getId(), fabricante.getNome(), fabricante.getAnoFundacao().format(formatter));
    }

    public FabricanteResponseDTO (Fabricante fabricante) {

        this(fabricante.getId(), fabricante.getNome(), fabricante.getAnoFundacao().format(formatter));
    }
}
