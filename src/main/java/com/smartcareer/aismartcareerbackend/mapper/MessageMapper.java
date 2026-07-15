package com.smartcareer.aismartcareerbackend.mapper;

import com.smartcareer.aismartcareerbackend.dto.MessageResponse;
import com.smartcareer.aismartcareerbackend.entities.Company;
import com.smartcareer.aismartcareerbackend.entities.Message;
import com.smartcareer.aismartcareerbackend.entities.User;

public class MessageMapper {

    public static MessageResponse toResponse(Message message) {
        User sender = message.getSender();
        String senderType = (sender instanceof Company) ? "COMPANY" : "CANDIDATE";
        String senderName = (senderType.equals("COMPANY"))
                ? ((Company) sender).getCompanyName()
                : sender.getFirstName() + " " + sender.getLastName();

        return MessageResponse.builder()
                .id(message.getId())
                .applicationId(message.getApplication().getId())
                .senderId(sender.getId())
                .senderType(senderType)
                .senderName(senderName)
                .content(message.getContent())
                .sentAt(message.getSentAt())
                .isRead(message.getIsRead())
                .build();
    }
}