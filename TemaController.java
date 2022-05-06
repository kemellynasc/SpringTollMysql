package com.BlogPessoal.BlogPessoal.Controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.BlogPessoal.BlogPessoal.Repository.TemaRepository;
import com.BlogPessoal.BlogPessoal.model.Tema;

import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;

@RestController
@RequestMapping("/temas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController<Tema> {
	
	@Autowired
	private TemaRepository temaRepository;
	
	 @GetMapping
	 public ResponseEntity<List<com.BlogPessoal.BlogPessoal.model.Tema>> getAll(){
		 return ResponseEntity.ok(temaRepository.findAll());
	 }
	
	@SuppressWarnings("unchecked")
	@GetMapping("/{id}")
	public ResponseEntity<Tema>getById(@PathVariable Long id){
		return (ResponseEntity<Tema>) temaRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); 

	}

	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<com.BlogPessoal.BlogPessoal.model.Tema>> getByTitle(@PathVariable String
			descricao){
		return ResponseEntity.ok(temaRepository
				.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	@PostMapping
	public ResponseEntity<Tema> post(@Valid @RequestBody Tema tema){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(TemaRepository.save(tema));
	}
@PutMapping
public ResponseEntity<Tema> put(@Valid @RequestBody Tema tema){
	return temaRepository.findById(tema.getId())
			.map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
					.body(temaRepository.save(tema)))
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
}

@ResponseStatus(HttpStatus.NO_CONTENT)
@DeleteMapping("/{id}")
public void delete(@PathVariable Long id) {
Optional<Tema>tema = temaRepository.findById(id);

if(tema.isEmpty())
	throw new ResponseStatusException(HttpStatus.NOT_FOUND);

temaRepository.deleteById(id);

}
}
