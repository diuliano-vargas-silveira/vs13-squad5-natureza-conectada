package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.repository.ClienteRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EnderecoRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EntregaMudaRepository;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EntregaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ServiceEntregaTest {

    @Mock
    private EntregaRepository entregaRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private ServiceCliente serviceCliente;
    @Mock
    private ServiceEndereco serviceEndereco;
    @Mock
    private ServiceMudas serviceMudas;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private EntregaMudaRepository entregaMudaRepository;
    @InjectMocks
    private ServiceEntrega serviceEntrega;

    @Test
    @DisplayName("Deveria ...")
    public void deveria...() {}

}