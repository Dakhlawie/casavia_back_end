package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Conversation;
import com.meriem.casavia.entities.Person;
import com.meriem.casavia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation,Long> {
    Conversation findByUserAndPartner(User user, Person partner);
    List<Conversation> findByPartner(Person partner);
    List<Conversation> findByUser(User user);
    @Modifying
    @Query("UPDATE Message m SET m.seen = true WHERE m.conversation = :conversation AND m.role = 'PARTNER'")
    void markMessagesAsSeen(@Param("conversation") Conversation conversation);
    @Query("SELECT COUNT(m) FROM Message m WHERE m.conversation.user.user_id = :userId AND m.role = 'PARTNER' AND m.seen = false")
    long countUnseenMessagesByUser(@Param("userId") Long userId);
    @Modifying
    @Query("UPDATE Message m SET m.seen = true WHERE m.conversation = :conversation AND m.role = 'USER'")
    void markMessagesUserAsSeen(@Param("conversation") Conversation conversation);
    @Query("SELECT COUNT(m) FROM Message m WHERE m.conversation.partner.person_id = :partnerId AND m.role = 'User' AND m.seen = false")
    long countUnseenMessagesByPartner(@Param("partnerId") Long partnerId);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.conversation.id = :conversationId AND m.conversation.partner.person_id = :partnerId AND m.role = 'User' AND m.seen = false")
    long countUnseenMessagesByPartnerAndConversation(@Param("partnerId") Long partnerId, @Param("conversationId") Long conversationId);
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Conversation c WHERE c.user = :user AND c.partner = :partner")
    boolean existsByUserAndPartner(@Param("user") User user, @Param("partner") Person partner);
}
