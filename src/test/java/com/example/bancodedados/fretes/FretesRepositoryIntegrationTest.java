package com.example.bancodedados.fretes;

import com.example.bancodedados.cidade.Cidade;
import com.example.bancodedados.cidade.CidadeRepository;
import com.example.bancodedados.cliente.Cliente;
import com.example.bancodedados.cliente.ClienteRepository;
import com.example.bancodedados.frete.Frete;
import com.example.bancodedados.frete.FreteRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/*
É usado em combinação com @RunWith (SpringRunner.class).
A anotação desativa a configuração automática completa
e aplica apenas faz a configuração relevante aos testes JPA.
Por padrão, os testes anotados com @DataJpaTest
usam um banco de dados em memória. */

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class FretesRepositoryIntegrationTest {

	@Autowired
	private FreteRepository freteRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private CidadeRepository cidadeRepository;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private Frete frete;

	@Before
	public void start() {
		Cliente cliente1 = new Cliente("Valerio", "Monte Castelo", "123456789");
		clienteRepository.save(cliente1);

		Cidade cidade1 = new Cidade("Sao Luis", 12.3, "MA");
		cidadeRepository.save(cidade1);

		frete = new Frete(234.0,"Maquina 1",1000.0,cliente1,cidade1);
		freteRepository.save(frete);
	}
//
//	@Test
//	public void saveComDescricaoNuloDeveLancarException() throws Exception {
//		expectedException.expect(ConstraintViolationException.class);
//		expectedException.expectMessage("A descricao deve ser preenchida");
//
//		frete.setDescricao("");
//		freteRepository.save(frete);
//		System.out.println("########## " + frete);
//	}
//
//	@Test
//	public void saveComPesoNuloDeveLancarException() throws Exception {
//		expectedException.expect(ConstraintViolationException.class);
//		expectedException.expectMessage("O peso deve ser preenchido");
//
//		frete.setPeso(-1);
//		freteRepository.save(frete);
//	}
//
//	@Test
//	public void saveComValorNuloDeveLancarException() throws Exception {
//		expectedException.expect(ConstraintViolationException.class);
//		expectedException.expectMessage("O valor deve ser preenchido");
//
//		frete.setValor(-1);
//		freteRepository.save(frete);
//	}
//
//
//	@Test
//	public void saveComClienteNuloDeveLancarException() throws Exception {
//		expectedException.expect(ConstraintViolationException.class);
//		expectedException.expectMessage("O cliente deve ser preenchido");
//
//		frete.setCliente(null);
//		freteRepository.save(frete);
//	}
//
//	@Test
//	public void saveComCidadeNuloDeveLancarException() throws Exception {
//		expectedException.expect(ConstraintViolationException.class);
//		expectedException.expectMessage("A cidade deve ser preenchida");
//
//		frete.setCidade(null);
//		freteRepository.save(frete);
//	}

	@Test
	public void saveDeveSalvarfrete() {
		freteRepository.save(frete);
		List<Frete> fretes = freteRepository.findAll();
		Assert.assertEquals(1, fretes.size());
		freteRepository.deleteAll();
	}

	@Test
	public void deleteByIdDeveRemoverfrete() {
		freteRepository.save(frete);
		List<Frete> fretes = freteRepository.findAll();
		Assert.assertEquals(1, fretes.size());

		freteRepository.deleteById(frete.getId());
		List<Frete> resultado = freteRepository.findAll();
		Assert.assertEquals(0, resultado.size());
	}

}