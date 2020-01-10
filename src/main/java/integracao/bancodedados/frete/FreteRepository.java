package integracao.bancodedados.frete;

import integracao.bancodedados.frete.Frete;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FreteRepository extends JpaRepository<Frete, Long> {

    @Query("FROM Frete u WHERE u.cliente.nome = ?1 order by u.valor")
    List<Frete> buscaPor(String nome);

    @Query(value = "From Frete")
    List<Frete> todos(Sort sort);
}
