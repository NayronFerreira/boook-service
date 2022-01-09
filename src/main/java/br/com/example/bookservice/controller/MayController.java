package br.com.example.bookservice.controller;

import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("book-service")
public class MayController {
	private Logger logger = LoggerFactory.getLogger(MayController.class);

	@GetMapping(value = "/{babyMessage}")
	@Retry(name = "babyMessage", fallbackMethod = "itsBad")
	public String mayBaby (@PathVariable ("babyMessage") String babyMessage){
		logger.info("Request May Linda recevied");
		String babySaudacao = ("A May é minha princesa e ela é muito "+babyMessage);
		new RestTemplate().getForEntity("localhost:8080/babyMessage",String.class);
		return babySaudacao;
	}

	public String itsBad (Exception e){
		return "Deu ruim no babyMessage";
	}
}
