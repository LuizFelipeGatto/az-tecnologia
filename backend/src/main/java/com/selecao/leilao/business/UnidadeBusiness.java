package com.selecao.leilao.business;

import com.selecao.leilao.dto.ResultadoOperacaoDTO;
import com.selecao.leilao.dto.UnidadeDTO;
import com.selecao.leilao.entity.Unidade;
import com.selecao.leilao.repository.UnidadeRepository;
import com.selecao.leilao.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UnidadeBusiness {

    private final UnidadeRepository unidadeRepository;

    public List<UnidadeDTO> getUnidades(){
        List<Unidade> unidades = unidadeRepository.findAll();
        return unidades.stream().map(unidade -> new UnidadeDTO(unidade)).collect(Collectors.toList());
    }

    public UnidadeDTO getById(Integer id){
        Optional<Unidade> unidade = unidadeRepository.findById(id);
        if(unidade.isEmpty()) {
            return null;
        }
        return new UnidadeDTO(unidade.get());
    }

    @Transactional
    public ResultadoOperacaoDTO<String> salvarUnidade(UnidadeDTO unidadeDTO){
        unidadeRepository.save(
                Unidade
                .builder()
                .nome(unidadeDTO.getNome())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()
        );
        return new ResultadoOperacaoDTO<>(true, Constants.UNIDADE_CADASTRADA, null);
    }

    @Transactional
    public ResultadoOperacaoDTO<String> editarUnidade(UnidadeDTO unidadeDTO, Integer id){
        Optional<Unidade> unidade = unidadeRepository.findById(id);
        if(unidade.isEmpty()){
            return null;
        }
        unidadeDTO.setId(unidade.get().getId());
        Unidade unidadeSalvar = atualizaUnidade(unidadeDTO);
        unidadeSalvar.setCreatedAt(unidade.get().getCreatedAt());
        unidadeRepository.save(unidadeSalvar);
        return new ResultadoOperacaoDTO<>(true, Constants.UNIDADE_ATUALIZADA, null);
    }

    public Unidade atualizaUnidade(UnidadeDTO unidadeDTO){
        return Unidade
                .builder()
                .id(unidadeDTO.getId())
                .nome(unidadeDTO.getNome())
                .createdAt(unidadeDTO.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public String deletarUnidade(Integer id){
        unidadeRepository.findById(id).ifPresent(unidadeRepository::delete);
        return "ok";
    }

}
