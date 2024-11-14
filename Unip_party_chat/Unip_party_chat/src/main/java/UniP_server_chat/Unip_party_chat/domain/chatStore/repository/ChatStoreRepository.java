package UniP_server_chat.Unip_party_chat.domain.chatStore.repository;

import UniP_server_chat.Unip_party_chat.domain.chatStore.entity.ChatStore;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatStoreRepository extends JpaRepository<ChatStore, Long> {
    Optional<ChatStore> findByMember(Member member);
}
