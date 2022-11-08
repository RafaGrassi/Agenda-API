package com.exercicio.agenda.controllers;

import com.exercicio.agenda.entidades.Contato;
import com.exercicio.agenda.repositories.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contatos") //http://localhost:8080/contatos
public class ContatoController {

    @Autowired
    ContatoRepository contatoRepository;

    @PostMapping
    public ResponseEntity<Contato> salvar(@Valid @RequestBody Contato contato){
        return ResponseEntity.status(HttpStatus.CREATED).body(contatoRepository.save(contato));
    }

    @GetMapping
    public ResponseEntity<List<Contato>> consultarContatos(){
        return ResponseEntity.status(HttpStatus.OK).body(contatoRepository.findAll());
    }

    @GetMapping("/{idcontato}")
    public ResponseEntity<Optional<Contato>> consultarById(@PathVariable("idcontato") Long idcontato){
        return ResponseEntity.ok().body(contatoRepository.findById(idcontato));
    }

    @PutMapping("/{idcontato}")
    public ResponseEntity<Object> alterar(
            @PathVariable("idcontato") Long idcontato,
            @Valid @RequestBody Contato contato) {
        Contato cont = contatoRepository.findById(idcontato).get();
        cont.setNome(contato.getNome());
        cont.setEmail(contato.getEmail());
        cont.setFone(contato.getFone());
        return ResponseEntity.status(HttpStatus.OK).body(contatoRepository.save(cont));
    }

    @DeleteMapping("/{idcontato}")
    public ResponseEntity<String> excluir(@PathVariable("idcontato") Long idcontato){
        try{
            Contato cont = contatoRepository.findById(idcontato).get();
            if(cont != null){
                contatoRepository.delete(cont);
            }
            return ResponseEntity.ok().body("Produto excluído com sucesso.");
        }
        catch (Exception e) {
            return ResponseEntity.ok().body("Produto não existe.");
        }
    }

}
