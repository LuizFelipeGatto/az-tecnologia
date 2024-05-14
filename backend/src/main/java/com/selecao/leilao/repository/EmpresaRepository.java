package com.selecao.leilao.repository;

import com.selecao.leilao.entity.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    Page<Empresa> findAll(Specification<Empresa> tranSpec, Pageable pageable);

    @Query(value = "SELECT EXISTS (SELECT * FROM projeto.empresa WHERE cnpj LIKE ?1)", nativeQuery = true)
    boolean existeEmpresaCnpj(String cnpj);

    @Query(value = "SELECT EXISTS (SELECT * FROM projeto.empresa WHERE usuario LIKE ?1)", nativeQuery = true)
    boolean existeEmpresaUsuario(String usuario);
}
