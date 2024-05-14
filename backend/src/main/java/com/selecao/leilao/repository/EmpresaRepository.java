package com.selecao.leilao.repository;

import com.selecao.leilao.entity.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    Page<Empresa> findAll(Specification<Empresa> tranSpec, Pageable pageable);
}
