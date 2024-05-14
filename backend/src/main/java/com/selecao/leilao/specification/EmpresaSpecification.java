package com.selecao.leilao.specification;

import com.selecao.leilao.entity.Empresa;
import com.selecao.leilao.specification.filter.Filtro;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class EmpresaSpecification extends SpecificationBuilder<Empresa, Filtro>{

    @Override
    protected void loadPredicates(Filtro filtro) {

        withFiltro(filtro);

    }

    private void withFiltro(Filtro filtro) {

        if(Objects.nonNull(filtro.getComprador())) {
            addPredicates(cb.equal(root.get("id"), filtro.getComprador()));
        }

        if(StringUtils.hasText(filtro.getCnpj())) {
            addPredicates(cb.equal(root.get("cnpj"), filtro.getCnpj()));
        }

        if(StringUtils.hasText(filtro.getTelefone())) {
            addPredicates(cb.equal(root.get("telefone"), filtro.getTelefone()));
        }

        if(StringUtils.hasText(filtro.getEmail())) {
            addPredicates(cb.equal(root.get("email"), filtro.getEmail()));
        }

    }

}
