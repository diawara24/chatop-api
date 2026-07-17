package com.openclassroom.chatopapi.services.impl;

import com.openclassroom.chatopapi.dto.MessageRequestDto;
import com.openclassroom.chatopapi.model.Message;
import com.openclassroom.chatopapi.model.Rental;
import com.openclassroom.chatopapi.model.User;
import com.openclassroom.chatopapi.record.MessageResponse;
import com.openclassroom.chatopapi.repository.MessageRepository;
import com.openclassroom.chatopapi.repository.RentalRepository;
import com.openclassroom.chatopapi.repository.UserRepository;
import com.openclassroom.chatopapi.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final RentalRepository rentalRepository;

    private final UserRepository userRepository;

    @Override
    public MessageResponse send(MessageRequestDto message) {

        Rental rental = rentalRepository.findById(message.getRentalId())
                .orElseThrow(() -> new RuntimeException("Location non trouvé avec l'id: " + message.getRentalId()));

        User user = userRepository.findById(message.getUserId())
                .orElseThrow(() -> new RuntimeException("User non trouvé avec l'id: " + message.getUserId()));

        Message msg = Message.builder()
                .message(message.getMessage())
                .user(user)
                .rental(rental)
                .build();

        messageRepository.save(msg);

        return new MessageResponse("Message send with success");
    }
}
