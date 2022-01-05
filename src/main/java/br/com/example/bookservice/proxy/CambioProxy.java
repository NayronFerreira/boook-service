package br.com.example.bookservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "cambio-service", url = "localhost:8000")
public interface CambioProxy {
}
