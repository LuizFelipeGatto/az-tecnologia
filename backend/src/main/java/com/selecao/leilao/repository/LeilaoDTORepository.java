package com.selecao.leilao.repository;

import com.selecao.leilao.dto.LeilaoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeilaoDTORepository extends JpaRepository<LeilaoDTO, Integer> {

    LeilaoDTO findByComprador(Integer id);

    Page<LeilaoDTO> findAll(Specification<LeilaoDTO> tranSpec, Pageable pageable);
}
