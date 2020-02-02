package integracao.bancodedados.frete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
@RestController
@RequestMapping("/fretecontroler")
public class FreteControler {

        @Autowired
        private FreteService freteService;

        @GetMapping("/")
        public ResponseEntity<List<Frete>> Fretes(){
            List<Frete> Fretes = freteService.buscarFretes();
            return ResponseEntity.ok(Fretes);
        }

        @GetMapping("/frete/{id}")
        public ResponseEntity<Frete> Frete(@PathVariable Long id){
            return freteService.buscarFreteId(id)
                    .map (ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping("/inserir")
        public ResponseEntity<Frete> inserir(@RequestBody @Valid Frete frete) throws URISyntaxException, FreteException {
            frete = freteService.inserirOuAlterar(frete);
            return new ResponseEntity<>(frete, HttpStatus.CREATED);
        }

        @PutMapping("/alterar/{id}")
        public ResponseEntity<Frete> alterar(@PathVariable Long id, @RequestBody @Valid Frete Frete) throws URISyntaxException, FreteException {
            Frete = freteService.inserir(Frete);
            return new ResponseEntity<>(Frete, HttpStatus.CREATED);
        }

        @DeleteMapping("/remover/{id}")
        public ResponseEntity<Frete> remover(@PathVariable Long id) {
            freteService.remover(id);
            return ResponseEntity.noContent().build();
        }

    }

