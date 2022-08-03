package mx.com.openwebinars.tienda.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import mx.com.openwebinars.tienda.component.PaginationLinksUtils;
import mx.com.openwebinars.tienda.component.PedidoConverter;
import mx.com.openwebinars.tienda.models.Pedido;
import mx.com.openwebinars.tienda.service.PedidoService;
import mx.com.openwebinars.tienda.utils.exceptions.PedidoNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController {

	private final PedidoService pedidoService;	
	private final PaginationLinksUtils paginationLinks;
	private final PedidoConverter converter;

	@GetMapping
	public ResponseEntity<Page<Pedido>>  pedidos(@PageableDefault(size = 10, page= 0) Pageable pageable,  HttpServletRequest request){
		var data = this.pedidoService.findAll(pageable);
		if(data.isEmpty())
			throw new PedidoNotFoundException();
		else {
			Page<Pedido> response = data.map(this.converter::convertTo);
			var uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
			return ResponseEntity.ok().header("link", this.paginationLinks.createLinkHeader(response, uriBuilder)).body(response);
		}			
	}

	@PostMapping
	@ResponseBody
	public ResponseEntity<Pedido> save(@RequestBody Pedido pedido){
		var data = this.pedidoService.save(this.converter.convertTo(pedido));
		if(data == null)
			throw new PedidoNotFoundException(pedido);
		else {
			var response = this.converter.convertTo(data);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
		
	}

}
