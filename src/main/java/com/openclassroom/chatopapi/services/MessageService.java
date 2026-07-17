package com.openclassroom.chatopapi.services;

import com.openclassroom.chatopapi.dto.MessageRequestDto;
import com.openclassroom.chatopapi.record.MessageResponse;

public interface MessageService {
    MessageResponse send(MessageRequestDto message);
}
