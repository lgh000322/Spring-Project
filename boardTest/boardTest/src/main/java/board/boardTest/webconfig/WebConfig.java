package board.boardTest.webconfig;

import board.boardTest.converter.StringToMemberConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final StringToMemberConverter stringToMemberConverter;


    public WebConfig(StringToMemberConverter stringToMemberConverter) {
        this.stringToMemberConverter = stringToMemberConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToMemberConverter);
    }
}
