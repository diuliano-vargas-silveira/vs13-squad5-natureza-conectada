//package br.com.vemser.naturezaconectada.naturezaconectada.repository;
//
//import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
//import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
//import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface IMudaRepository extends JpaRepository<Muda, Integer> {
//
//    boolean mudarAtivoMuda(Integer id, Ativo ativo);
//
//    List<Muda> listarMudasAtivas();
//
//    Muda procurarPorEco(Ecossistema ecossistema);
//
//    List<Muda> procurarMudasEntrega(int idEntrega);
//}