package com.selecao.leilao.specification;

import com.selecao.leilao.dto.LeilaoDTO;
import com.selecao.leilao.specification.filter.Filtro;

import java.util.Objects;

public class LeilaoSpecification extends SpecificationBuilder<LeilaoDTO, Filtro>{

    @Override
    protected void loadPredicates(Filtro filtro) {

        withFiltro(filtro);

    }

    private void withFiltro(Filtro filtro) {

        if(Objects.nonNull(filtro.getComprador())) {
            addPredicates(cb.equal(root.get("comprador"), filtro.getComprador()));
        }

        if(Objects.nonNull(filtro.getTotalLeilao())) {
            addPredicates(cb.equal(root.get("totalLeilao"), filtro.getTotalLeilao()));
        }

    }

}
