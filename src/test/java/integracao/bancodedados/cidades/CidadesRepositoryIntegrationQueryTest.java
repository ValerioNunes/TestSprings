package integracao.bancodedados.cidades;

import integracao.bancodedados.cidade.CidadeRepository;
import integracao.bancodedados.cidade.Cidade;
import integracao.bancodedados.cidade.CidadeRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CidadesRepositoryIntegrationQueryTest {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Before
	public void before() {
		Cidade cidade = new Cidade("Sao Luis", 12.3, "MA");
		cidadeRepository.save(cidade);
		cidade = new Cidade("Rio de Janeiro", 5.6, "RJ");
		cidadeRepository.save(cidade);
		cidade = new Cidade("Sao Paulo", 2.3, "SP");
		cidadeRepository.save(cidade);
	}

	@After
	public void after() {
		cidadeRepository.deleteAll();
	}


	@Test
	public void deveBuscarcidadePeloNome() {
		Cidade cidade= cidadeRepository.findFirstByNome("Sao Luis");
		System.out.println(cidade.getNome());
		Assert.assertTrue(cidade.getNome().equals("Sao Luis"));
	}

	@Test
	public void deveRetornarTodosOscidadesOrdenadosEmOrdemCrescente()  {

		List<Cidade> cidades = cidadeRepository.todos(new Sort(Sort.Direction.ASC, "nome"));

		Assert.assertTrue(cidades.get(0).getNome().equals("Rio de Janeiro"));
		Assert.assertTrue(cidades.get(1).getNome().equals("Sao Luis"));
		Assert.assertTrue(cidades.get(2).getNome().equals("Sao Paulo"));

	}


}