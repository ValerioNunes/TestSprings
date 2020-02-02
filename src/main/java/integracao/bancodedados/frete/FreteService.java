package integracao.bancodedados.frete;

import integracao.bancodedados.cidade.Cidade;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class FreteService {

    @Autowired
    private FreteRepository freteRepository;

    public Frete inserir(Frete frete) throws FreteException {
        try {
            frete = freteRepository.save(frete);
        } catch (ConstraintViolationException e) {
            throw new FreteException(e);
        }
        return frete;
    }
    public Frete inserirOuAlterar(Frete frete) {
            return freteRepository.save(frete);
    }

    public List<Frete> buscarFretes(){
        return  freteRepository.todos(new Sort(Sort.Direction.ASC, "id"));
    }
    public Optional<Frete> buscarFreteId(long id){
        return  freteRepository.findById(id);
    }

    public void remover(Long id) {
        freteRepository.deleteById(id);
    }

    public Frete getMaiorValorFrete(){
        return freteRepository.maxValorFrete();
    }

    public Cidade getCidadeComMaiorNumeroFretes(){
        if( freteRepository.cidadeComMaisFrete().size() > 0 )
            return freteRepository.cidadeComMaisFrete().get(0).getCidade();
        return null;
    }
}
