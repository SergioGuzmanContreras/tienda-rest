package mx.com.openwebinars.tienda.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.com.openwebinars.tienda.component.CategoriaConverter;
import mx.com.openwebinars.tienda.component.PaginationLinksUtils;
import mx.com.openwebinars.tienda.dao.entity.CategoriaEntity;
import mx.com.openwebinars.tienda.models.Categoria;
import mx.com.openwebinars.tienda.service.CategoriaService;
import mx.com.openwebinars.tienda.utils.exceptions.CategoriaNotFoundException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categorias")
public class CategoriaRestController {

	private final CategoriaService service;
	private final CategoriaConverter converter;	
	private final PaginationLinksUtils pagination;

	@GetMapping
	@ResponseBody
	public ResponseEntity<Page<Categoria>>  obtenerTodos(@PageableDefault(size = 10, page= 0) Pageable pageable, HttpServletRequest request){
		var data = this.service.findAll(pageable);
		if(data.isEmpty())
			throw new CategoriaNotFoundException();
		else {
			var response = data.map(this.converter::converterToObject);		
			var uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
			return ResponseEntity.ok().header("link", this.pagination.createLinkHeader(response, uriBuilder)).body(response);
		}
	
	}
	
	@ResponseBody
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> obtenerUno(@PathVariable Long id) {
		var data = this.service.findById(id);
		var response = this.converter.converterToObject(data);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	@ResponseBody
	public ResponseEntity<Categoria> nueva(@RequestBody Categoria request) {
		log.info("request {}", request);
		var transform = this.converter.converterToEntity(request);
		var response = this.converter.converterTo(this.service.save(transform));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}


	@PutMapping
	@ResponseBody
	public ResponseEntity<Categoria> editarCategoria(@RequestBody Categoria request) {
		log.info("request {}", request);
		var transform = this.converter.converterTo(request);
		var response = this.converter.converterTo(this.service.update(transform));
		return ResponseEntity.ok(response);
	}

	@ResponseBody
	@DeleteMapping("/{id}")
	public ResponseEntity<CategoriaEntity> borrarCategoria(@PathVariable Long id) {
		this.service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
