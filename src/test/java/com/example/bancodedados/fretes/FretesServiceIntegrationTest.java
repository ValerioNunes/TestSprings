package com.example.bancodedados.fretes;

import com.example.bancodedados.cidade.Cidade;
import com.example.bancodedados.cidade.CidadeRepository;
import com.example.bancodedados.cliente.Cliente;
import com.example.bancodedados.cliente.ClienteRepository;
import com.example.bancodedados.frete.Frete;
import com.example.bancodedados.frete.FreteException;
import com.example.bancodedados.frete.FreteService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FretesServiceIntegrationTest {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private FreteService freteService;


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Frete frete;
    private Frete freteNaoSalvo;
    @Before
    public void start() throws FreteException {
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


        Frete frete1 = new Frete(234.0,"Maquina 1",1000.0,cliente2,cidade2);
        freteService.inserir(frete1);
        Frete frete2 = new Frete(134.0,"Maquina 2",1000.0,cliente3,cidade3);
        freteService.inserir(frete2);

        frete = new Frete(334.0,"Maquina 3",1000.0,cliente1,cidade1);
        freteService.inserir(frete);

        Frete frete4 = new Frete(124.0,"Maquina 4",1000.0,cliente1,cidade1);
        freteService.inserir(frete4);

        freteNaoSalvo = new Frete(124.0,"Maquina 4",1000.0,cliente1,cidade1);

    }

    @Test
    public void inserirRegistroComClinteNuloDeveLancarException() throws FreteException {
        expectedException.expect(FreteException.class);
        expectedException.expectMessage("O cliente deve ser preenchido");
        freteNaoSalvo.setCliente(null);
        Mockito.when(freteService.inserir(freteNaoSalvo))
                .thenThrow(new ConstraintViolationException("O cliente deve ser preenchido",null));
        //agendaController.inserirRegistro(null, ddd, telefone);
    }

    @Test
    public void inserirRegistroComCidadeNuloDeveLancarException() throws FreteException {
        expectedException.expect(FreteException.class);
        expectedException.expectMessage("A cidade deve ser preenchida");
        freteNaoSalvo.setCidade(null);
        Mockito.when(freteService.inserir(freteNaoSalvo))
                .thenThrow(new ConstraintViolationException("A cidade deve ser preenchida",null));
        //agendaController.inserirRegistro(null, ddd, telefone);
    }

    @Test
    public void deveRetornaFreteComMaiorValor(){
        Assert.assertTrue(frete.getValor() == freteService.getMaiorValorFrete().getValor());
    }


    @Test
    public void  deveSerAcidadeComMaisFretes(){
       Cidade cidadeMaiorNumFretes = freteService.getCidadeComMaiorNumeroFretes();
    }



}
