package UniP_server_chat.Unip_party_chat.global.config;

import UniP_server_chat.Unip_party_chat.global.jwt.info.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = JwtProperties.class)
public class ConfigurationPropsConfig {
}
