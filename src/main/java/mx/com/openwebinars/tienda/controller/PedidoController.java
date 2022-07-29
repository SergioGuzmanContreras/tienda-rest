package mx.com.openwebinars.tienda.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import mx.com.openwebinars.tienda.component.PaginationLinksUtils;
import mx.com.openwebinars.tienda.dao.entity.PedidoEntity;
import mx.com.openwebinars.tienda.service.PedidoService;
import mx.com.openwebinars.tienda.utils.exceptions.SearchProductoNotResultException;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PaginationLinksUtils paginationLinks;

	@GetMapping
	public ResponseEntity<Page<PedidoEntity>>  pedidos(@PageableDefault(size = 10, page= 0) Pageable pageable,  HttpServletRequest request){
		var data = this.pedidoService.findAll(pageable);
		if(data.isEmpty())
			throw new SearchProductoNotResultException();
		else {
			var uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
			return ResponseEntity.ok().header("link", this.paginationLinks.createLinkHeader(data, uriBuilder)).body(data);
		}			
	}


}
