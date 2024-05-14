package com.selecao.leilao.entity;

import com.selecao.leilao.util.ConfigUrl;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(schema = ConfigUrl.SCHEMA_PROJETO, name = "empresa")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empresa {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "empresa_id_seq")
    @SequenceGenerator(name = "empresa_id_seq", sequenceName = ConfigUrl.SCHEMA_PROJETO + ".empresa_id_seq", allocationSize=1)
    private Integer id;

    @Column
    private String razaoSocial;

    @Column
    private String cnpj;

    @Column
    private String lograduro;

    @Column
    private String municipio;

    @Column
    private String numero;

    @Column
    private String complemento;

    @Column
    private String bairro;

    @Column
    private String cep;

    @Column
    private String telefone;

    @Column
    private String email;

    @Column
    private String site;

    @Column
    private String usuario;

    @Column
    private String senha;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;
}
