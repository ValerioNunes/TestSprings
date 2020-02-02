package com.example.bancodedados.clientes;

import com.example.bancodedados.cliente.Cliente;
import com.example.bancodedados.cliente.ClienteRepository;
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

import javax.validation.ConstraintViolationException;
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
public class ClientesRepositoryIntegrationTest {

	@Autowired
	private ClienteRepository clienteRepository;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private Cliente cliente;

	@Before
	public void start() {
	   cliente = new Cliente("Valerio", "Retiro Natal", "123456789");
	}

	@Test
	public void saveComDddNuloDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("O endereco deve ser preenchido");

		cliente.setEndereco(null);
		clienteRepository.save(cliente);
	}

	@Test
	public void saveComTelefoneNuloDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("O Telefone deve ser preenchido");

		cliente.setTelefone(null);
		clienteRepository.save(cliente);
	}

	@Test
	public void saveComNomeNuloDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("O Nome deve ser preenchido");

		cliente.setNome(null);
		clienteRepository.save(cliente);
	}

	@Test
	public void saveDeveSalvarcliente() {
		clienteRepository.save(cliente);
		List<Cliente> clientes = clienteRepository.findAll();
		Assert.assertEquals(1, clientes.size());
		clienteRepository.deleteAll();
	}

	@Test
	public void deleteByIdDeveRemovercliente() {
		clienteRepository.save(cliente);
		List<Cliente> clientes = clienteRepository.findAll();
		Assert.assertEquals(1, clientes.size());

		clienteRepository.deleteById(cliente.getId());
		List<Cliente> resultado = clienteRepository.findAll();
		Assert.assertEquals(0, resultado.size());
	}

}