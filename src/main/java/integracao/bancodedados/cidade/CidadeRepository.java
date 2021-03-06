package integracao.bancodedados.cidade;

import integracao.bancodedados.cidade.Cidade;
import integracao.bancodedados.cliente.Cliente;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    Cidade findFirstByNome(String nome);

    @Query("SELECT u FROM Cidade u WHERE u.nome = ?1")
    Cidade buscaPor(String nome);

    @Query(value = "From Cidade")
    List<Cidade> todos(Sort sort);
}
