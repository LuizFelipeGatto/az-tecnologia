package com.selecao.leilao.entity;

import com.selecao.leilao.util.ConfigUrl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(schema = ConfigUrl.SCHEMA_PROJETO, name = "unidade")
public class Unidade {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "unidade_id_seq")
    @SequenceGenerator(name = "unidade_id_seq", sequenceName = ConfigUrl.SCHEMA_PROJETO + ".unidade_id_seq", allocationSize=1)
    private Integer id;

    @Column
    private String nome;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;
}
