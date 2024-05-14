package com.selecao.leilao.repository;

import com.selecao.leilao.entity.Comprador;
import com.selecao.leilao.entity.RelEmpresaLeilao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompradorRepository extends JpaRepository<Comprador, RelEmpresaLeilao> {

    @Query(value = "SELECT EXISTS (SELECT * FROM projeto.comprador WHERE empresa = ?1 )", nativeQuery = true)
    boolean vinculado(Integer id);

    @Query(value = "SELECT EXISTS (SELECT * FROM projeto.comprador WHERE leilao = ?1 )", nativeQuery = true)
    boolean vinculadoLeilao(Integer id);

    @Query(value = "SELECT * FROM projeto.comprador WHERE empresa = ?1 AND leilao = ?2", nativeQuery = true)
    Optional<Comprador> existsCompra(Integer id, Integer leilao);

}
