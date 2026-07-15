package com.smartcareer.aismartcareerbackend.controllers;

import com.smartcareer.aismartcareerbackend.dto.ConversationResponse;
import com.smartcareer.aismartcareerbackend.dto.MessageRequest;
import com.smartcareer.aismartcareerbackend.dto.MessageResponse;
import com.smartcareer.aismartcareerbackend.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageResponse> send(@Valid @RequestBody MessageRequest request) {
        return ResponseEntity.ok(messageService.send(request));
    }

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<List<MessageResponse>> getConversation(@PathVariable Long applicationId) {
        return ResponseEntity.ok(messageService.getConversation(applicationId));
    }

    @PatchMapping("/application/{applicationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long applicationId) {
        messageService.markAsRead(applicationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationResponse>> getMyConversations() {
        return ResponseEntity.ok(messageService.getMyConversations());
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount() {
        return ResponseEntity.ok(messageService.getUnreadCount());
    }
}
