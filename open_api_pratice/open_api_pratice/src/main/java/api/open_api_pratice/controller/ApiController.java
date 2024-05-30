package api.open_api_pratice.controller;

import api.open_api_pratice.apiget.Example;
import api.open_api_pratice.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiController {

    private final ApiService apiService;

    @GetMapping("/api")
    public ResponseEntity<Mono<Example>> getApi() throws IOException {
        Mono<Example> api = apiService.getApi();
        return new ResponseEntity<>(api, HttpStatus.OK);
    }
}
