package mx.com.openwebinars.tienda.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import mx.com.openwebinars.tienda.component.CategoriaConverter;
import mx.com.openwebinars.tienda.dao.entity.CategoriaEntity;
import mx.com.openwebinars.tienda.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaRestController {

	@Autowired
	private CategoriaService CategoriaService;
	
	@Autowired
	private CategoriaConverter CategoriaConverter;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<?>  obtenerTodos() {
		var data = this.CategoriaService.findAll();
		if(data.isEmpty())
			return ResponseEntity.notFound().build();
		else {
			var response = data.stream().map(this.CategoriaConverter::converterTo).collect(Collectors.toList());		
			return ResponseEntity.ok(response);
		}
	}
	
	@ResponseBody
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaEntity> obtenerUno(@PathVariable Long id) {
		var response = this.CategoriaService.findById(id);
		if(response == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(response);
	}

	@PostMapping
	@ResponseBody
	public ResponseEntity<CategoriaEntity> nuevoCategoria(@RequestBody CategoriaEntity request) {
		var response = this.CategoriaService.save(request);
		if(response == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@ResponseBody
	@PutMapping("/{id}")
	public ResponseEntity<CategoriaEntity> editarCategoria(@RequestBody CategoriaEntity request, @PathVariable Long id) {
		request.setId(id);
		var response = this.CategoriaService.update(request);
		if(response == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(response);
	}

	@ResponseBody
	@DeleteMapping("/{id}")
	public ResponseEntity<CategoriaEntity> borrarCategoria(@PathVariable Long id) {
		this.CategoriaService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
