package br.com.example.bookservice.service;

import br.com.example.bookservice.controller.BookMessageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookService {
	private Logger logger = LoggerFactory.getLogger(BookMessageController.class);

	public String getBookMessage(String bookMessage) {
		String bookSaudacao = ("The Book Message is: " + bookMessage);
		return bookSaudacao;
	}

	public String getBookMessagePlus(String bookMessage) throws Exception {
		int retry = 3;
		while (retry > 0) {
			try {
				logger.info("Request by service recevied");
				new RestTemplate().getForEntity("localhost:8080/bookMessage", String.class);
				return bookMessage;
			} catch (Exception e) {
				logger.error("Request by service recevied - mensagem='Erro ao processa tentativa número {}'",retry);
				retry--;
			}
		}
		return bookMessage.toUpperCase();
	}
}
