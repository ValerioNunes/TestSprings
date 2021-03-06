package integracao.bancodedados.frete;

import integracao.bancodedados.cidade.CidadeQtdFrete;
import integracao.bancodedados.frete.Frete;
import org.junit.runner.RunWith;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Long> {


    @Query("FROM Frete u WHERE u.cliente.nome = ?1 order by u.valor")
    List<Frete> buscaPor(String nome);

    @Query(value = "From Frete u order by u.valor")
    List<Frete> todos(Sort sort);

    @Query(value = "select  * From Frete u order by u.valor desc limit 1", nativeQuery = true)
    Frete maxValorFrete();

    @Query("select new integracao.bancodedados.cidade.CidadeQtdFrete( u.cidade , count(u)) from Frete u group by u.cidade order by  count(u) desc")
    List<CidadeQtdFrete> cidadeComMaisFrete();
}
