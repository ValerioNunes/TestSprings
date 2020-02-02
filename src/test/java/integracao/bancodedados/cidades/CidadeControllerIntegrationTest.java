package integracao.bancodedados.cidades;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import integracao.bancodedados.cidade.Cidade;
import integracao.bancodedados.cidade.CidadeRepository;
import integracao.bancodedados.cidade.CidadeService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CidadeControllerIntegrationTest {
    
        // serve para consumir os métodos HTTP
        @Autowired
        private TestRestTemplate testRestTemplate;
        @Autowired
        private CidadeRepository cidadeRepository;
        @Autowired
        private CidadeService cidadeService;

        ObjectMapper objectMapper = new ObjectMapper();
        Cidade Cidade1, Cidade2;
        
        @Before
        public void start(){
            Cidade1 = new Cidade("Valerio", 12, "MA");
            Cidade2 = new Cidade("Breno", 13, "MA");
        }

        @After
        public void end() {
            cidadeRepository.deleteAll();
        }


        @Test
        public void deveMostrarTodosCidades() throws IOException {

            cidadeService.inserir(Cidade1);
            cidadeService.inserir(Cidade2);
            
            List<Cidade>  CidadesCadastrados = new ArrayList<>();

            CidadesCadastrados.add(Cidade1);
            CidadesCadastrados.add(Cidade2);
            
            ResponseEntity<String> resposta = testRestTemplate.exchange("/cidadecontroler/", HttpMethod.GET, null, String.class);
            Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());

            String json = resposta.getBody();
            List<Cidade> Cidades = objectMapper.readValue(json, new TypeReference<List<Cidade>>(){});

            Assert.assertEquals(CidadesCadastrados.size(), Cidades.size());
            CidadesCadastrados.forEach( f -> {
                Assert.assertTrue(Cidades.contains(f));
            });

        }


    @Test
    public void inserirDeveSalvarCidade() {

        HttpEntity<Cidade> httpEntity = new HttpEntity<>(Cidade1);

        ResponseEntity<Cidade> resposta =
                testRestTemplate.exchange("/cidadecontroler/inserir",HttpMethod.POST,httpEntity, Cidade.class);

        Assert.assertEquals(HttpStatus.CREATED,resposta.getStatusCode());

        Cidade resultado = resposta.getBody();

        Assert.assertNotNull(resultado.getId());
        Assert.assertEquals(Cidade1.getNome() , resultado.getNome());
        Assert.assertEquals(Cidade1.getUF(), resultado.getUF());

    }

}
