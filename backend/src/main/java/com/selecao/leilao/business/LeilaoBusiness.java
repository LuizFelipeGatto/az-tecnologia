package com.selecao.leilao.business;

import com.selecao.leilao.dto.LeilaoDTO;
import com.selecao.leilao.entity.Empresa;
import com.selecao.leilao.entity.Leilao;
import com.selecao.leilao.repository.EmpresaRepository;
import com.selecao.leilao.repository.LeilaoDTORepository;
import com.selecao.leilao.repository.LeilaoRepository;
import com.selecao.leilao.specification.LeilaoSpecification;
import com.selecao.leilao.specification.filter.Filtro;
import com.selecao.leilao.util.DataUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LeilaoBusiness {

    private final LeilaoRepository leilaoRepository;

    private final LeilaoDTORepository leilaoDTORepository;

    private final EmpresaRepository empresaRepository;

    public List<Leilao> getLeiloes(){
        return leilaoRepository.findAll();
    }

    public Page<LeilaoDTO> findByFilter(Filtro filtro) {
        LeilaoSpecification leilaoSpecification = new LeilaoSpecification();
        Specification<LeilaoDTO> tranSpec = leilaoSpecification.build(filtro);
        Pageable pageable = leilaoSpecification.buildPageable(filtro);
        Page<LeilaoDTO> page = leilaoDTORepository.findAll(tranSpec, pageable);

//        for (LeilaoDTO leilaoDTO : page.getContent()) {
//            Optional<Empresa> empresa = empresaRepository.findById(leilaoDTO.getComprador());
//            leilaoDTO.setRazaoSocial(empresa.get().getRazaoSocial());
//            leilaoDTO.setInicioPrevistoFormatado(DataUtils.formataData(leilaoDTO.getInicioPrevisto()));
//        }

        page.getContent().stream().forEach(leilaoDTO -> {
            empresaRepository.findById(leilaoDTO.getComprador())
                    .ifPresent(empresa -> {
                        leilaoDTO.setRazaoSocial(empresa.getRazaoSocial());
                        leilaoDTO.setInicioPrevistoFormatado(DataUtils.formataData(leilaoDTO.getInicioPrevisto()));
                    });
        });


        return page;
    }

    public boolean filtroIsEmpty(Filtro filtro){
        if(Objects.nonNull(filtro.getComprador())
                || Objects.nonNull(filtro.getTotalLeilao())) {
            return false;
        }

        return true;
    }

    public List<LeilaoDTO> leiloes(){
        List<Leilao> leilaos = leilaoRepository.findAll();
        return leilaos.stream().map(this::populaLeilao).collect(Collectors.toList());
    }

    public LeilaoDTO populaLeilao(Leilao leilao){
        LeilaoDTO leilaoDTO = leilaoDTORepository.findByComprador(leilao.getId());
        leilaoDTO.setInicioPrevistoFormatado(DataUtils.formataData(leilaoDTO.getInicioPrevisto()));
        Optional<Empresa> empresa = empresaRepository.findById(leilaoDTO.getComprador());
        if(empresa.isEmpty()){
            return null;
        }

        leilaoDTO.setRazaoSocial(empresa.get().getRazaoSocial());
        return leilaoDTO;
    }


}
