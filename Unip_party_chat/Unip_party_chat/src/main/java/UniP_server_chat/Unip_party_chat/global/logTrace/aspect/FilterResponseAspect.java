/*
package UniP_server_chat.Unip_party_chat.global.logTrace.aspect;

import UniP_server_chat.Unip_party_chat.global.baseResponse.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Aspect
@RequiredArgsConstructor
public class FilterResponseAspect {

    private final ObjectMapper objectMapper;

    @Around("@annotation(UniP_server_chat.Unip_party_chat.global.filter.annotation.FilterResponse)")
    public Object responseAction(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletResponse response = null;

        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof HttpServletResponse) {
                response = (HttpServletResponse) arg;
                break;
            }
        }

        try {
            return joinPoint.proceed();
        } catch (JwtException exception) {
            if (response != null) {
                sendErrorResponse(HttpStatus.UNAUTHORIZED, response, exception);
            }
            return null;
        }
    }

    private void sendErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");
        String jsonStr = objectMapper.writeValueAsString(ResponseDto.fail(status.value(), ex.getMessage()));
        response.getWriter().write(jsonStr);
    }
}
*/
