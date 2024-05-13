package com.selecao.leilao.repository;

import com.selecao.leilao.entity.Comprador;
import com.selecao.leilao.entity.RelEmpresaLeilao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompradorRepository extends JpaRepository<Comprador, RelEmpresaLeilao> {

}
