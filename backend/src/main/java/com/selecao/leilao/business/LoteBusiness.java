package com.selecao.leilao.business;

import com.selecao.leilao.dto.ResultadoOperacaoDTO;
import com.selecao.leilao.entity.Lote;
import com.selecao.leilao.repository.LoteRepository;
import com.selecao.leilao.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoteBusiness {

    private final LoteRepository loteRepository;

    public List<Lote> getLotes(){
        return loteRepository.findAll();
    }

    public ResultadoOperacaoDTO<Lote> getById(Integer id){
        Optional<Lote> lote = loteRepository.findById(id);
        if(lote.isEmpty()) {
            return new ResultadoOperacaoDTO<>(false, null, Constants.LOTE_NAO_ENCONTRADO);
        }
        return new ResultadoOperacaoDTO<>(true, lote.get(), null);
    }

    @Transactional
    public ResultadoOperacaoDTO<String> salvarLote(Lote lote){
        lote.setCreatedAt(LocalDateTime.now());
        lote.setUpdatedAt(LocalDateTime.now());
        loteRepository.save(lote);
        return new ResultadoOperacaoDTO<>(true, Constants.LOTE_CADASTRADO, null);
    }

    @Transactional
    public ResultadoOperacaoDTO<String> editarLote(Lote lote, Integer id){
        Optional<Lote> loteBanco = loteRepository.findById(id);
        if(loteBanco.isEmpty()){
            return new ResultadoOperacaoDTO<>(false, null, Constants.LOTE_NAO_ENCONTRADO);
        }

        lote.setId(loteBanco.get().getId());
        lote.setUpdatedAt(LocalDateTime.now());
        loteRepository.save(lote);
        return new ResultadoOperacaoDTO<>(true, Constants.LOTE_ATUALIZADO, null);
    }

    public ResultadoOperacaoDTO<String> deletarLote(Integer id){
        loteRepository.findById(id).ifPresent(loteRepository::delete);
        return new ResultadoOperacaoDTO<>(true, Constants.EXCLUIDA, null);
    }

}
