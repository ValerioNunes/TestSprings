package integracao.bancodedados.clientes;

import integracao.bancodedados.cliente.Cliente;
import integracao.bancodedados.cliente.ClienteRepository;
import integracao.bancodedados.frete.FreteService;
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
public class ClienteRepositoryIntegrationQueryTest {

	@Autowired
	private ClienteRepository clienteRepository;

	@Before
	public void before() {
		Cliente cliente = new Cliente("Valerio", "Monte Castelo", "123456789");
		clienteRepository.save(cliente);
		cliente = new Cliente("Breno", "Maioba", "222123456");
		clienteRepository.save(cliente);
		cliente = new Cliente("Pablo", "Bequimao", "987654321");
		clienteRepository.save(cliente);
	}

	@After
	public void after() {
		clienteRepository.deleteAll();
	}


	@Test
	public void deveBuscarClientePeloNome() {
		Cliente cliente= clienteRepository.findFirstByNome("Valerio");
		Assert.assertTrue(cliente.getNome().equals("Valerio"));
	}

	@Test
	public void deveRetornarTodosOsClientesOrdenadosEmOrdemCrescente()  {
		List<Cliente> clientes = clienteRepository.todos(new Sort(Sort.Direction.ASC, "nome"));

		Assert.assertTrue(clientes.get(0).getNome().equals("Breno"));
		Assert.assertTrue(clientes.get(1).getNome().equals("Pablo"));
		Assert.assertTrue(clientes.get(2).getNome().equals("Valerio"));
	}




}