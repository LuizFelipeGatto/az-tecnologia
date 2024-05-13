package com.selecao.leilao.entity;

import com.selecao.leilao.util.ConfigUrl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = ConfigUrl.SCHEMA_PROJETO, name = "comprador")
public class Comprador {

    @EmbeddedId
    private RelEmpresaLeilao relEmpresaLeilao;

    @ManyToOne
    @JoinColumn(name = "empresa", referencedColumnName = "id", insertable=false, updatable=false)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "leilao", referencedColumnName = "id", insertable=false, updatable=false)
    private Leilao leilao;

}
