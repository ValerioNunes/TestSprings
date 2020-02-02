package integracao.bancodedados.clientes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import integracao.bancodedados.cliente.Cliente;
import integracao.bancodedados.cliente.ClienteRepository;
import integracao.bancodedados.cliente.ClienteService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteControllerIntegrationTest {
    
        // serve para consumir os métodos HTTP
        @Autowired
        private TestRestTemplate testRestTemplate;
        @Autowired
        private ClienteRepository clienteRepository;
        @Autowired
        private ClienteService clienteService;

        ObjectMapper objectMapper = new ObjectMapper();
        Cliente cliente1, cliente2;
        
        @Before
        public void start(){
            cliente1 = new Cliente("Valerio", "Monte Castelo", "123456789");
            cliente2 = new Cliente("Breno", "Monte Castelo", "123456789");
        }

        @After
        public void end() {
            clienteRepository.deleteAll();
        }


        @Test
        public void deveMostrarTodosClientes() throws IOException {

            clienteService.inserir(cliente1);
            clienteService.inserir(cliente2);
            
            List<Cliente>  clientesCadastrados = new ArrayList<>();

            clientesCadastrados.add(cliente1);
            clientesCadastrados.add(cliente2);
            
            ResponseEntity<String> resposta = testRestTemplate.exchange("/clientecontroler/", HttpMethod.GET, null, String.class);
            Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());

            String json = resposta.getBody();
            List<Cliente> clientes = objectMapper.readValue(json, new TypeReference<List<Cliente>>(){});

            Assert.assertEquals(clientesCadastrados.size(), clientes.size());
            clientesCadastrados.forEach( f -> {
                Assert.assertTrue(clientes.contains(f));
            });

        }


    @Test
    public void inserirDeveSalvarCliente() {

        HttpEntity<Cliente> httpEntity = new HttpEntity<>(cliente1);

        ResponseEntity<Cliente> resposta =
                testRestTemplate.exchange("/clientecontroler/inserir",HttpMethod.POST,httpEntity, Cliente.class);

        Assert.assertEquals(HttpStatus.CREATED,resposta.getStatusCode());

        Cliente resultado = resposta.getBody();

        Assert.assertNotNull(resultado.getId());
        Assert.assertEquals(cliente1.getNome() , resultado.getNome());
        Assert.assertEquals(cliente1.getEndereco(), resultado.getEndereco());

    }

}
