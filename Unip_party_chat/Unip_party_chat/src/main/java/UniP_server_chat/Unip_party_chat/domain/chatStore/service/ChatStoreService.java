package UniP_server_chat.Unip_party_chat.domain.chatStore.service;

import UniP_server_chat.Unip_party_chat.domain.chatStore.entity.ChatStore;
import UniP_server_chat.Unip_party_chat.domain.chatStore.repository.ChatStoreRepository;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import UniP_server_chat.Unip_party_chat.domain.member.service.CustomMemberService;
import UniP_server_chat.Unip_party_chat.global.memberinfo.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatStoreService {

    private final MemberInfo memberInfo;
    private final ChatStoreRepository chatStoreRepository;

    @Transactional
    public ChatStore createOrUseChatStore() {
        Member member = memberInfo.getThreadLocalMember();
        Optional<ChatStore> findChatStoreOptional = chatStoreRepository.findByMember(member);

        if (findChatStoreOptional.isEmpty()) {
            ChatStore chatStore = ChatStore.builder()
                    .member(member)
                    .build();

            chatStoreRepository.save(chatStore);
            return chatStore;
        }

        return findChatStoreOptional.get();
    }

    @Transactional
    public ChatStore createOrUseChatStore(Member member) {
        Optional<ChatStore> findChatStoreOptional = chatStoreRepository.findByMember(member);

        if (findChatStoreOptional.isEmpty()) {
            ChatStore chatStore = ChatStore.builder()
                    .member(member)
                    .build();

            chatStoreRepository.save(chatStore);
            return chatStore;
        }

        return findChatStoreOptional.get();
    }

}
