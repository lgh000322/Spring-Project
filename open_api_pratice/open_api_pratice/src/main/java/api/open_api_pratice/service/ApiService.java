package api.open_api_pratice.service;

import api.open_api_pratice.apiget.Exam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiService {
    private final WebClient webClient;

    @Value("${apiKey}")
    private String apiKey;

    @Value("${api.path}")
    private String path;

    public Exam getApi() {
        Exam block = webClient.get()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .queryParam("serviceKey", apiKey)
                        .queryParam("pageNo", "1")
                        .queryParam("numOfRows", "1000")
                        .queryParam("dataType", "Json")
                        .queryParam("base_date", "20240430")
                        .queryParam("base_time", "0600")
                        .queryParam("nx", "55")
                        .queryParam("ny", "127")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Exam.class)
                .block();

        return block;
    }
}
