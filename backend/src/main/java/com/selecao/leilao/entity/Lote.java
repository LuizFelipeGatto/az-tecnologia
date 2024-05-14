package com.selecao.leilao.entity;

import com.selecao.leilao.util.ConfigUrl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(schema = ConfigUrl.SCHEMA_PROJETO, name = "lote")
public class Lote {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "lote_id_seq")
    @SequenceGenerator(name = "lote_id_seq", sequenceName = ConfigUrl.SCHEMA_PROJETO + ".lote_id_seq", allocationSize=1)
    private Integer id;

    @Column
    private Integer numeroLote;

    @Column
    private String descricao;

    @Column
    private BigDecimal quantidade;

    @Column
    private BigDecimal valorInicial;

    @Column
    private String unidade;

    @ManyToOne
    @JoinColumn(name = "leilao", referencedColumnName = "id")
    private Leilao leilao;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

}
