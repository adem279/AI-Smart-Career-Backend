package com.smartcareer.aismartcareerbackend.service;

import com.smartcareer.aismartcareerbackend.dto.ConversationResponse;
import com.smartcareer.aismartcareerbackend.dto.InterviewResponse;
import com.smartcareer.aismartcareerbackend.dto.MessageRequest;
import com.smartcareer.aismartcareerbackend.dto.MessageResponse;
import com.smartcareer.aismartcareerbackend.entities.*;
import com.smartcareer.aismartcareerbackend.mapper.InterviewMapper;
import com.smartcareer.aismartcareerbackend.mapper.MessageMapper;
import com.smartcareer.aismartcareerbackend.repository.ApplicationRepository;
import com.smartcareer.aismartcareerbackend.repository.InterviewRepository;
import com.smartcareer.aismartcareerbackend.repository.MessageRepository;
import com.smartcareer.aismartcareerbackend.repository.UserRepository;
import com.smartcareer.aismartcareerbackend.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public MessageResponse send(MessageRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        String userType = SecurityUtils.getCurrentUserType();

        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Candidature introuvable"));

        checkAccess(application, userId, userType);

        if (application.getStatus() != ApplicationStatus.ACCEPTED) {
            throw new RuntimeException("La messagerie n'est disponible qu'une fois la candidature acceptée");
        }

        User sender = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Message message = Message.builder()
                .application(application)
                .sender(sender)
                .content(request.getContent())
                .build();

        messageRepository.save(message);

        User recipient = userType.equals("COMPANY") ? application.getCandidate() : application.getJobOffer().getCompany();
        String senderLabel = userType.equals("COMPANY")
                ? application.getJobOffer().getCompany().getCompanyName()
                : application.getCandidate().getFirstName() + " " + application.getCandidate().getLastName();

        notificationService.createNotification(
                recipient,
                "Nouveau message",
                senderLabel + " vous a envoyé un message concernant \"" + application.getJobOffer().getTitle() + "\""
        );

        return MessageMapper.toResponse(message);
    }

    public List<MessageResponse> getConversation(Long applicationId) {
        Long userId = SecurityUtils.getCurrentUserId();
        String userType = SecurityUtils.getCurrentUserType();

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Candidature introuvable"));

        checkAccess(application, userId, userType);

        return messageRepository.findByApplicationIdOrderBySentAtAsc(applicationId)
                .stream()
                .map(MessageMapper::toResponse)
                .toList();
    }

    public void markAsRead(Long applicationId) {
        Long userId = SecurityUtils.getCurrentUserId();
        String userType = SecurityUtils.getCurrentUserType();

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Candidature introuvable"));

        checkAccess(application, userId, userType);

        List<Message> messages = messageRepository.findByApplicationIdOrderBySentAtAsc(applicationId);
        for (Message message : messages) {
            if (!message.getSender().getId().equals(userId) && !Boolean.TRUE.equals(message.getIsRead())) {
                message.setIsRead(true);
            }
        }
        messageRepository.saveAll(messages);
    }

    /**
     * Liste des conversations de l'utilisateur courant, une par candidature acceptée,
     * enrichie avec le dernier message, le nombre de messages non lus et — de façon
     * intelligente — les informations d'entretien planifié pour cette candidature.
     */
    public List<ConversationResponse> getMyConversations() {
        Long userId = SecurityUtils.getCurrentUserId();
        String userType = SecurityUtils.getCurrentUserType();

        List<Application> applications = userType.equals("COMPANY")
                ? applicationRepository.findByJobOffer_Company_Id(userId)
                : applicationRepository.findByCandidateId(userId);

        Map<Long, ConversationResponse> conversations = new LinkedHashMap<>();

        for (Application application : applications) {
            if (application.getStatus() != ApplicationStatus.ACCEPTED) {
                continue;
            }

            Message lastMessage = messageRepository.findLastMessage(application.getId());
            long unread = messageRepository.countByApplicationIdAndSenderIdNotAndIsReadFalse(application.getId(), userId);

            InterviewResponse interviewResponse = interviewRepository.findByApplicationId(application.getId())
                    .map(InterviewMapper::toResponse)
                    .orElse(null);

            Candidate candidate = application.getCandidate();
            Company company = application.getJobOffer().getCompany();

            ConversationResponse conversation = ConversationResponse.builder()
                    .applicationId(application.getId())
                    .applicationStatus(application.getStatus().name())
                    .jobOfferId(application.getJobOffer().getId())
                    .jobOfferTitle(application.getJobOffer().getTitle())
                    .candidateId(candidate.getId())
                    .candidateName(candidate.getFirstName() + " " + candidate.getLastName())
                    .candidatePhoto(candidate.getPhoto())
                    .companyId(company.getId())
                    .companyName(company.getCompanyName())
                    .companyLogo(company.getLogo())
                    .lastMessage(lastMessage != null ? lastMessage.getContent() : null)
                    .lastMessageDate(lastMessage != null ? lastMessage.getSentAt() : null)
                    .unreadCount(unread)
                    .interview(interviewResponse)
                    .build();

            conversations.put(application.getId(), conversation);
        }

        return conversations.values().stream()
                .sorted(Comparator.comparing(
                        ConversationResponse::getLastMessageDate,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
    }

    public long getUnreadCount() {
        return getMyConversations().stream()
                .mapToLong(ConversationResponse::getUnreadCount)
                .sum();
    }

    private void checkAccess(Application application, Long userId, String userType) {
        boolean isCandidate = userType.equals("CANDIDATE") && application.getCandidate().getId().equals(userId);
        boolean isCompany = userType.equals("COMPANY") && application.getJobOffer().getCompany().getId().equals(userId);

        if (!isCandidate && !isCompany) {
            throw new RuntimeException("Vous n'êtes pas autorisé à accéder à cette conversation");
        }
    }
}