package UniP_server_chat.Unip_party_chat.domain.member.entity.Enum;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum Role {
    //스프링 시큐리티의 권한 코드는 항상 "ROLE_" 로 시작해야 함 -> hasRole로 뒤의 ADMIN, MEMBER로 접근 가능
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    GUEST("ROLE_GUEST"),
    ;

    private static final Map<String, Role> BY_KEY = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(Role::getKey, Function.identity())));

    private final String key;

    Role(String key) {
        this.key = key;
    }
}