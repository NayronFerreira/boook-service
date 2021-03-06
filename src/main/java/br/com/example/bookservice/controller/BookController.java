package br.com.example.bookservice.controller;

import br.com.example.bookservice.model.Book;
import br.com.example.bookservice.proxy.CambioProxy;
import br.com.example.bookservice.repository.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book endpoint")
@RestController
@RequestMapping("book-service")
public class BookController {

	@Autowired
	private Environment environment;
	@Autowired
	private BookRepository repository;

	@Autowired
	private CambioProxy cambioProxy;

	@Operation(summary = "Busca um livro por ID e converte o valor com base na moeda especificada.")
	@GetMapping(value = "/{id}/{currency}")
	public Book getBook (@PathVariable ("id") Long id, @PathVariable ("currency") String currency){

		var book = repository.getById(id);
		if (book == null) throw new RuntimeException("Entre com um ID e currency corretamente");

		var cambio = cambioProxy.getCambio(book.getPrice(),"USD",currency);

		book.setPrice(cambio.getConvertedValue().doubleValue());

		var port = environment.getProperty("local.server.port");
		book.setEnvironment("Book port: "+ port + " Cambio port: "+ cambio.getEnvironment());
		return book;
	}
}
