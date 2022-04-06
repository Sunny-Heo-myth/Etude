package com.chatboard.etude.service.message;

import com.chatboard.etude.dto.message.MessageCreateRequest;
import com.chatboard.etude.dto.message.MessageDto;
import com.chatboard.etude.dto.message.MessageListDto;
import com.chatboard.etude.dto.message.MessageReadCondition;
import com.chatboard.etude.entity.message.Message;
import com.chatboard.etude.exception.MessageNotFoundException;
import com.chatboard.etude.repository.member.MemberRepository;
import com.chatboard.etude.repository.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    public MessageListDto readAllBySender(MessageReadCondition condition) {
        return MessageListDto.toDto(messageRepository.findAllBySenderIdOrderByMessageIdDesc(
                condition.getMemberId(), condition.getLastMessageId(), Pageable.ofSize(condition.getSize())
        ));
    }

    public MessageListDto readAllByReceiver(MessageReadCondition condition) {
        return MessageListDto.toDto(messageRepository.findAllByReceiverIdOrderByMessageIdDesc(
                condition.getMemberId(), condition.getLastMessageId(), Pageable.ofSize(condition.getSize())
        ));
    }

    public MessageDto read(Long id) {
        return MessageDto.toDto(
                messageRepository.findWithSenderAndReceiverById(id)
                        .orElseThrow(MessageNotFoundException::new)
        );
    }

    @Transactional
    public void create(MessageCreateRequest request) {
        messageRepository.save(MessageCreateRequest.toEntity(request, memberRepository));
    }

    @Transactional
    public void deleteBySender(Long id) {
        delete(id, Message::deleteBySender);
    }

    @Transactional
    public void deleteByReceiver(Long id) {
        delete(id, Message::deleteByReceiver);
    }

    private void delete(Long id, Consumer<Message> delete) {
        Message message = messageRepository.findById(id)
                .orElseThrow(MessageNotFoundException::new);
        delete.accept(message);

        if (message.isDeletable()) {
            messageRepository.delete(message);
        }
    }
}