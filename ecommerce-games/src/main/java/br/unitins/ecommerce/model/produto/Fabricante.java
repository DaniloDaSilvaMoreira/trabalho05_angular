package br.unitins.ecommerce.model.produto;

import java.time.LocalDate;

import br.unitins.ecommerce.model.DefaultEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Fabricante extends DefaultEntity {
    
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDate anoFundacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getAnoFundacao() {
        return anoFundacao;
    }

    public void setAnoFundacao(LocalDate anoFundacao) {
        this.anoFundacao = anoFundacao;
    }
}
