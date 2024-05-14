package com.selecao.leilao.specification;

import com.selecao.leilao.specification.filter.IFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public abstract class SpecificationBuilder<TEntity, Filter extends IFilter> {

    protected Root<TEntity> root;
    protected CriteriaQuery<?> query;
    protected CriteriaBuilder cb;

    protected Pagination pagination;

    List<Predicate> predicates;
    List<Predicate> simplificadoPredicates;

    public Specification<TEntity> build(Filter filter) {

        return (root, query, cb) -> {

            this.root = root;
            this.query = query;
            this.cb = cb;
            this.predicates = new ArrayList<>();
            this.simplificadoPredicates = new ArrayList<>();

            loadPredicates(filter);
            loadPredicatesSimplificado(filter);

            return filter.getSimplificado() == null || filter.getSimplificado()
                    ? gerarSimplificadoPredicates()
                    : gerarPredicates();
        };

    }

    public Pageable buildPageable(Filter filter) {

        if(filter.getOrdemList() != null && !filter.getOrdemList().isEmpty()){

            return new Pagination(filter.getPagina(), filter.getTamanhoPagina(), filter.getOrdemList(), filter.getOrdemDirecao()).build();

        } else if(filter.getOrdem() != null) {

            return new Pagination(filter.getPagina(), filter.getTamanhoPagina(), filter.getOrdem(), filter.getOrdemDirecao()).build();
        }

        return new Pagination(filter.getPagina(), filter.getTamanhoPagina()).build();

    }

    protected void loadPredicates(Filter filter){


    }

    protected void loadPredicatesSimplificado(Filter filter) {


    }

    protected void addPredicates(Predicate predicate) {

        if(predicate == null){

            return;
        }

        predicates = predicates != null ? predicates : new ArrayList<>();

        predicates.add(predicate);
    }

    protected void addSimplificadoPredicates(Predicate predicate) {

        if(predicate == null){

            return;

        }

        simplificadoPredicates = simplificadoPredicates != null ? simplificadoPredicates : new ArrayList<>();

        simplificadoPredicates.add(predicate);
    }


    private Predicate gerarPredicates() {

        Predicate[] predicatesArray = this.predicates.toArray(new Predicate[this.predicates.size()]);

        return this.cb.and(predicatesArray);

    }

    private Predicate gerarSimplificadoPredicates() {

        Predicate[] predicatesArray = this.simplificadoPredicates.toArray(new Predicate[this.simplificadoPredicates.size()]);

        return this.cb.and(predicatesArray);

    }

    protected String getNormalizedParam(String param) {

        return param == null ? null : '%' + (Normalizer.normalize(param.toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "").replaceAll("[.&,\\-)(?']", "")) + '%';
    }


}
