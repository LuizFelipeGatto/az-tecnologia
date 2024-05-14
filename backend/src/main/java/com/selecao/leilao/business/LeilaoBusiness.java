package com.selecao.leilao.business;

import com.selecao.leilao.dto.LeilaoDTO;
import com.selecao.leilao.dto.ResultadoOperacaoDTO;
import com.selecao.leilao.entity.Empresa;
import com.selecao.leilao.entity.Leilao;
import com.selecao.leilao.repository.CompradorRepository;
import com.selecao.leilao.repository.EmpresaRepository;
import com.selecao.leilao.repository.LeilaoDTORepository;
import com.selecao.leilao.repository.LeilaoRepository;
import com.selecao.leilao.specification.LeilaoSpecification;
import com.selecao.leilao.specification.filter.Filtro;
import com.selecao.leilao.util.Constants;
import com.selecao.leilao.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    private final CompradorRepository compradorRepository;

    public List<Leilao> getLeiloes(){
        return leilaoRepository.findAll();
    }

    public List<LeilaoDTO> leiloes(){
        List<Leilao> leilaos = leilaoRepository.findAll();
        return leilaos.stream().map(this::populaLeilao).collect(Collectors.toList());
    }

    public ResultadoOperacaoDTO<Leilao> getById(Integer id){
        Optional<Leilao> leilao = leilaoRepository.findById(id);
        if(leilao.isEmpty()) {
            return new ResultadoOperacaoDTO<>(false, null, Constants.LEILAO_NAO_ENCONTRADO);
        }
        return new ResultadoOperacaoDTO<>(true, leilao.get(), null);
    }

    public Page<LeilaoDTO> findByFilter(Filtro filtro) {
        LeilaoSpecification leilaoSpecification = new LeilaoSpecification();
        Specification<LeilaoDTO> tranSpec = leilaoSpecification.build(filtro);
        Pageable pageable = leilaoSpecification.buildPageable(filtro);
        Page<LeilaoDTO> page = leilaoDTORepository.findAll(tranSpec, pageable);

        page.getContent().stream().forEach(leilaoDTO -> {
            empresaRepository.findById(leilaoDTO.getComprador())
                    .ifPresent(empresa -> {
                        leilaoDTO.setRazaoSocial(empresa.getRazaoSocial());
                        leilaoDTO.setInicioPrevistoFormatado(Utils.formataData(leilaoDTO.getInicioPrevisto()));
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

    public LeilaoDTO populaLeilao(Leilao leilao){
        LeilaoDTO leilaoDTO = leilaoDTORepository.findByComprador(leilao.getId());
        leilaoDTO.setInicioPrevistoFormatado(Utils.formataData(leilaoDTO.getInicioPrevisto()));
        Optional<Empresa> empresa = empresaRepository.findById(leilaoDTO.getComprador());
        if(empresa.isEmpty()){
            return null;
        }

        leilaoDTO.setRazaoSocial(empresa.get().getRazaoSocial());
        return leilaoDTO;
    }

    @Transactional
    public ResultadoOperacaoDTO<String> salvarLeilao(Leilao leilao){
        leilao.setCreatedAt(LocalDateTime.now());
        leilao.setUpdatedAt(LocalDateTime.now());
        leilaoRepository.save(leilao);
        return new ResultadoOperacaoDTO<>(true, Constants.LEILAO_CADASTRADO, null);
    }

    @Transactional
    public ResultadoOperacaoDTO<String> editarLeilao(Leilao leilao, Integer id){
        Optional<Leilao> leilaoBanco = leilaoRepository.findById(id);
        if(leilaoBanco.isEmpty()){
            return null;
        }

        leilao.setId(leilaoBanco.get().getId());
        Leilao leilaoSalvar = atualizaLeilao(leilao);
        leilaoSalvar.setCreatedAt(leilaoBanco.get().getCreatedAt());
        leilaoRepository.save(leilaoSalvar);
        return new ResultadoOperacaoDTO<>(true, Constants.LEILAO_ATUALIZADO, null);
    }

    public Leilao atualizaLeilao(Leilao leilao){
        return Leilao
                .builder()
                .id(leilao.getId())
                .codigo(leilao.getCodigo())
                .descricao(leilao.getDescricao())
                .inicioPrevisto(leilao.getInicioPrevisto())
                .vendedor(leilao.getVendedor())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public ResultadoOperacaoDTO<String> deletarLeilao(Integer id){
        if(compradorRepository.vinculadoLeilao(id)){
            return new ResultadoOperacaoDTO<>(false, null, Constants.LEILAO_VINCULADO);
        }

        leilaoRepository.findById(id).ifPresent(leilaoRepository::delete);
        return new ResultadoOperacaoDTO<>(true, Constants.EXCLUIDA, null);
    }

}
