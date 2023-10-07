package br.unitins.ecommerce.dto.plataforma;

import java.time.format.DateTimeFormatter;

import br.unitins.ecommerce.dto.fabricante.FabricanteResponseDTO;
import br.unitins.ecommerce.model.produto.Plataforma;

public record PlataformaResponseDTO(Long id, String nome, String descricao ,String anoLancamento,FabricanteResponseDTO fabricante) {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PlataformaResponseDTO (Plataforma plataforma, DateTimeFormatter formatter) {

        this(plataforma.getId(), plataforma.getNome(),plataforma.getDescricao(), plataforma.getAnoLancamento().format(formatter), new FabricanteResponseDTO(plataforma.getFabricante()));
    }

    public PlataformaResponseDTO (Plataforma plataforma) {

        this(plataforma.getId(), plataforma.getNome(),plataforma.getDescricao() ,plataforma.getAnoLancamento().format(formatter), new FabricanteResponseDTO(plataforma.getFabricante()));
    }

}
