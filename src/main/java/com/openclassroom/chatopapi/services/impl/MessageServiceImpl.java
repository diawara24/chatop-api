package com.openclassroom.chatopapi.services.impl;

import com.openclassroom.chatopapi.dto.MessageRequestDto;
import com.openclassroom.chatopapi.exception.domaines.NotFoundException;
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

import static com.openclassroom.chatopapi.constantes.ErrorConstant.*;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final RentalRepository rentalRepository;

    private final UserRepository userRepository;

    @Override
    public MessageResponse send(MessageRequestDto message) throws NotFoundException {

        Rental rental = rentalRepository.findById(message.getRentalId())
                .orElseThrow(() -> new NotFoundException(
                        String.format(RENTAL_NOT_FOUND,
                                message.getRentalId()
                        )
                ));

        User user = userRepository.findById(message.getUserId())
                .orElseThrow(() -> new NotFoundException(
                        String.format(USER_NOT_FOUND,
                                message.getUserId()
                        )
                ));

        Message msg = Message.builder()
                .message(message.getMessage())
                .user(user)
                .rental(rental)
                .build();

        messageRepository.save(msg);

        return new MessageResponse("Message send with success");
    }
}
