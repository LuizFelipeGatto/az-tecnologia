package com.selecao.leilao.entity;

import com.selecao.leilao.util.ConfigUrl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(schema = ConfigUrl.SCHEMA_PROJETO, name = "leilao")
public class Leilao {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "leilao_id_seq")
    @SequenceGenerator(name = "leilao_id_seq", sequenceName = ConfigUrl.SCHEMA_PROJETO + ".leilao_id_seq", allocationSize=1)
    private Integer id;

    @Column
    private Integer codigo;

    @Column
    private String descricao;

    @Column
    private Integer vendedor;

    @Column
    private LocalDateTime inicioPrevisto;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

}
