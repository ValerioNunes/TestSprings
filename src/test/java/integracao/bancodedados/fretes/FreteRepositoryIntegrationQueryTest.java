package integracao.bancodedados.fretes;

import integracao.bancodedados.cidade.Cidade;
import integracao.bancodedados.cidade.CidadeRepository;
import integracao.bancodedados.cliente.Cliente;
import integracao.bancodedados.cliente.ClienteRepository;
import integracao.bancodedados.frete.Frete;
import integracao.bancodedados.frete.FreteRepository;
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
public class FreteRepositoryIntegrationQueryTest {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private FreteRepository freteRepository;
	@Before
	public void before() {
		Cliente cliente1 = new Cliente("Valerio", "Monte Castelo", "123456789");
		clienteRepository.save(cliente1);
		Cliente cliente2 = new Cliente("Breno", "Maioba", "222123456");
		clienteRepository.save(cliente2);
		Cliente cliente3 = new Cliente("Pablo", "Bequimao", "987654321");
		clienteRepository.save(cliente3);

		Cidade cidade1 = new Cidade("Sao Luis", 12.3, "MA");
		cidadeRepository.save(cidade1);
		Cidade cidade2 = new Cidade("Rio de Janeiro", 5.6, "RJ");
		cidadeRepository.save(cidade2);
		Cidade cidade3 = new Cidade("Sao Paulo", 2.3, "SP");
		cidadeRepository.save(cidade3);


		Frete frete1 = new Frete(234.0,"Maquina 1",1000.0,cliente2,cidade1);
		freteRepository.save(frete1);
		Frete frete2 = new Frete(134.0,"Maquina 2",1000.0,cliente3,cidade1);
		freteRepository.save(frete2);

		Frete frete3 = new Frete(334.0,"Maquina 3",1000.0,cliente1,cidade1);
		freteRepository.save(frete3);

		Frete frete4 = new Frete(124.0,"Maquina 4",1000.0,cliente1,cidade1);
		freteRepository.save(frete4);
	}

	@After
	public void after() {
		cidadeRepository.deleteAll();
		clienteRepository.deleteAll();
		freteRepository.deleteAll();
	}

	@Test
	public void deveBuscarListaFretesPorClientePeloNome() {
		List<Frete> frete = freteRepository.buscaPor("Valerio" );

		Assert.assertEquals("Maquina 4", frete.get(0).getDescricao());
		Assert.assertEquals("Maquina 3", frete.get(1).getDescricao());

	}

	@Test
	public void deveRetornarTodosOsFretesOrdenadosEmOrdemCrescente()  {

		List<Frete> fretes = freteRepository.todos(new Sort(Sort.Direction.ASC, "id"));

		Assert.assertTrue(fretes.get(2).getCliente().getNome().equals("Breno"));
		Assert.assertTrue(fretes.get(1).getCliente().getNome().equals("Pablo"));
		Assert.assertTrue(fretes.get(0).getCliente().getNome().equals("Valerio"));
	}


}