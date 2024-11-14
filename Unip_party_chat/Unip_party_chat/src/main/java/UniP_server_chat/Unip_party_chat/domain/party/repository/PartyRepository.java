package UniP_server_chat.Unip_party_chat.domain.party.repository;

import UniP_server_chat.Unip_party_chat.domain.party.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party,Long> {
}
