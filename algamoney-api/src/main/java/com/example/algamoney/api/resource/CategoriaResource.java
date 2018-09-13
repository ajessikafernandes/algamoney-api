package com.example.algamoney.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categoria")
public class CategoriaResource {

	// @Autowired fornece controle sobre onde e como a ligação deve ser
	// realizada
	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}

	// Salva a criação da categoria no BD
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);//Consigo pegar o código
		
		//      Vou pegar da classe ServletUriComponentsBuilder e requisição atual (que é categoria) depois adicionar o código     
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{codigo}")//na URI
				.buildAndExpand(categoriaSalva.getCodigo())
				.toUri();
		//"seta" o header Location com o código da URI
		response.setHeader("Location", uri.toASCIIString());
		
		//Já declaro o status aqui
		return ResponseEntity.created(uri).body(categoriaSalva); 
	}
	
	@GetMapping("/{codigo}")
	public Categoria buscarPeloCodigo(@PathVariable long codigo){
		return categoriaRepository.findOne(codigo);
	}

	// @Repository Anotação que serve para definir uma classe como pertencente à
	// camada de persistência.

}
