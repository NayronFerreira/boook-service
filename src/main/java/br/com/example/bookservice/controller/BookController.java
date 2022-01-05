package br.com.example.bookservice.controller;

import br.com.example.bookservice.model.Book;
import br.com.example.bookservice.repository.BookRepository;
import br.com.example.bookservice.response.Cambio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("book-service")
public class BookController {

	@Autowired
	private Environment environment;
	@Autowired
	private BookRepository repository;

	@GetMapping(value = "/{id}/{currency}")
	public Book getBook (@PathVariable ("id") Long id, @PathVariable ("currency") String currency){

		var book = repository.getById(id);
		if (book == null) throw new RuntimeException("Entre com um ID e currency corretamente");

		HashMap<String, String> params = new HashMap<>();
		params.put("amount", book.getPrice().toString());
		params.put("from", "USD");
		params.put("to", currency);

		var response = new RestTemplate().getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}", Cambio.class, params);
		var cambio = response.getBody();
		book.setPrice(cambio.getConvertedValue().doubleValue());
		var port = environment.getProperty("local.server.port");
		book.setEnvironment(port);
		return book;
	}
}
