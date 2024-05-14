package com.selecao.leilao.dto;

import com.selecao.leilao.entity.Empresa;
import com.selecao.leilao.util.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaDTO {

    private Integer id;

    private String razaoSocial;

    private String cnpj;

    private String lograduro;

    private String municipio;

    private String numero;

    private String complemento;

    private String bairro;

    private String cep;

    private String telefone;

    private String email;

    private String site;

    private String usuario;

    private String senha;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdAtFormatada;

    private String updatedAtFormatada;

    public EmpresaDTO(Empresa empresa){
        this.id = empresa.getId();
        this.razaoSocial = empresa.getRazaoSocial();
        this.cnpj = empresa.getCnpj();
        this.lograduro = empresa.getLograduro();
        this.municipio = empresa.getMunicipio();
        this.numero = empresa.getNumero();
        this.complemento = empresa.getComplemento();
        this.bairro = empresa.getBairro();
        this.cep = empresa.getCep();
        this.telefone = empresa.getTelefone();
        this.email = empresa.getEmail();
        this.site = empresa.getSite();
        this.usuario = empresa.getUsuario();
        this.createdAtFormatada = Objects.nonNull(empresa.getCreatedAt()) ? Utils.formataData(empresa.getCreatedAt()) : "";
        this.updatedAtFormatada = Objects.nonNull(empresa.getUpdatedAt()) ? Utils.formataData(empresa.getUpdatedAt()) : "";
    }

}
