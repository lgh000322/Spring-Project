package UniP_server_chat.Unip_party_chat.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("UniP")
                        .version("v1.0")
                        .description("API Description"))
                // 'access'라는 헤더를 추가
                .addSecurityItem(new SecurityRequirement().addList("access"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("access", new SecurityScheme()
                                .in(SecurityScheme.In.HEADER)  // 헤더로 설정
                                .name("Authorization:Bearer ")  // 헤더 이름 설정
                                .type(SecurityScheme.Type.APIKEY)));  // scheme 없이 설정
    }
}
