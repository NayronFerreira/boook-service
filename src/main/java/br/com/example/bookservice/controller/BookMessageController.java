package br.com.example.bookservice.controller;

import br.com.example.bookservice.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book-service")
public class BookMessageController {
	private Logger logger = LoggerFactory.getLogger(BookMessageController.class);
	@Autowired
	private BookService bookService;

	@GetMapping(value = "/{bookMessage}")
//	@Retry(name = "bookMessage2", fallbackMethod = "itsBad")
	public String bookMessage (@PathVariable ("bookMessage") String bookMessage) throws Exception {
		logger.info("Request Book Message recevied");
		String bookSaudacao = bookService.getBookMessage(bookMessage);
		logger.info("Request Book Message PLUS recevied");
		String bookSaudacao2 = bookService.getBookMessagePlus(bookMessage);
		return bookSaudacao+", "+bookSaudacao2;
	}

	public String itsBad (Exception e){
		return "Deu ruim no bookMessage";
	}
}
