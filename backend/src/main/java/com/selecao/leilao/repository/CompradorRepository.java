package com.selecao.leilao.repository;

import com.selecao.leilao.entity.Comprador;
import com.selecao.leilao.entity.RelEmpresaLeilao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompradorRepository extends JpaRepository<Comprador, RelEmpresaLeilao> {

    @Query(value = "SELECT EXISTS (SELECT * FROM projeto.comprador WHERE empresa = ?1 )", nativeQuery = true)
    boolean vinculado(Integer id);

}
