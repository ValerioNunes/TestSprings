package integracao.bancodedados.cidades;

import integracao.bancodedados.cidade.Cidade;
import integracao.bancodedados.cidade.CidadeRepository;
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
public class CidadeRepositoryIntegrationTest {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private Cidade cidade;

	@Before
	public void start() {
	   cidade = new Cidade("São Luís", 12.4, "UF");
	}

	@Test
	public void saveComDddNuloDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("O taxa deve ser preenchido");

		cidade.settaxa(-1);
		cidadeRepository.save(cidade);
	}

	@Test
	public void saveComTelefoneNuloDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("O UF deve ser preenchido");

		cidade.setUF(null);
		cidadeRepository.save(cidade);
	}

	@Test
	public void saveComNomeNuloDeveLancarException() throws Exception {
		expectedException.expect(ConstraintViolationException.class);
		expectedException.expectMessage("O Nome deve ser preenchido");

		cidade.setNome(null);
		cidadeRepository.save(cidade);
	}

	@Test
	public void saveDeveSalvarcidade() {
		cidadeRepository.save(cidade);
		List<Cidade> cidades = cidadeRepository.findAll();
		Assert.assertEquals(1, cidades.size());
		cidadeRepository.deleteAll();
	}

	@Test
	public void deleteByIdDeveRemovercidade() {
		cidadeRepository.save(cidade);
		List<Cidade> cidades = cidadeRepository.findAll();
		Assert.assertEquals(1, cidades.size());

		cidadeRepository.deleteById(cidade.getId());
		List<Cidade> resultado = cidadeRepository.findAll();
		Assert.assertEquals(0, resultado.size());
	}

}