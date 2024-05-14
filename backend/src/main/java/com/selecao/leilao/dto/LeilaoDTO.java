package com.selecao.leilao.dto;

import com.selecao.leilao.util.ConfigUrl;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = ConfigUrl.SCHEMA_PROJETO, name = "vw_leilao_dto")
public class LeilaoDTO {

    @Id
    private Long id;

    private Integer comprador;

    @Transient
    private String razaoSocial;

    private LocalDateTime inicioPrevisto;

    @Transient
    private String inicioPrevistoFormatado;

    private BigDecimal totalLeilao;

}
