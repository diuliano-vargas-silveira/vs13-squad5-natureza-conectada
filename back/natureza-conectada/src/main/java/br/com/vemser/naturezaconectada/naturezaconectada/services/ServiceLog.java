package br.com.vemser.naturezaconectada.naturezaconectada.services;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.LogMudasCriadasDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.models.LogUsuarios;
import br.com.vemser.naturezaconectada.naturezaconectada.models.LogMudasCriadas;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.LogMudasCriadasRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.LogUsuarioRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.LogUsuarioDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceLog {
    private final LogUsuarioRepository logUsuario;
    private final LogMudasCriadasRepository logMudas;
    private final ObjectMapper objectMapper;

    public void criarLogUsuario(LogUsuarios logUsuarios){
        logUsuarios.setDataDeCriacao(LocalDateTime.now());
        this.logUsuario.save(logUsuarios);
    }
    public void criarLogMudas(LogMudasCriadas logMuda){
        logMuda.setCriadoEm(LocalDateTime.now());
        this.logMudas.save(logMuda);
    }
    public List<LogUsuarioDTO> totalDeUsuarioPorTipo(){
        return this.logUsuario.groupByTipoUsuarioAndCount().stream().map(logContaUsuario -> this.objectMapper.convertValue(logContaUsuario,LogUsuarioDTO.class)).collect(Collectors.toList());
    }
    public List<LogMudasCriadasDTO>listarMudasCriadasPorNome(String nome){
        return this.logMudas.findAllBynomeDoAdminLikeIgnoreCase(nome).stream().map(logMudasCriadas -> this.objectMapper.convertValue(logMudasCriadas, LogMudasCriadasDTO.class)).collect(Collectors.toList());
    }


}
