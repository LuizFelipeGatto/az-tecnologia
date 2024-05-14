package com.selecao.leilao.dto;

import com.selecao.leilao.entity.Unidade;
import com.selecao.leilao.util.DataUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnidadeDTO {

    private Integer id;

    private String nome;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdAtFormatada;

    private String updatedAtFormatada;

    public UnidadeDTO(Unidade unidade){
        this.id = unidade.getId();
        this.nome = unidade.getNome();
        this.createdAtFormatada = DataUtils.formataData(unidade.getCreatedAt());
        this.updatedAtFormatada = DataUtils.formataData(unidade.getUpdatedAt());
    }

}
