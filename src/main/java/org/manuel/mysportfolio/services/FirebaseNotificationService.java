package org.manuel.mysportfolio.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.manuel.mysportfolio.exceptions.NotificationException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class FirebaseNotificationService implements NotificationService {

    private final FirebaseMessaging firebaseMessaging;

    public FirebaseNotificationService() {
        firebaseMessaging = FirebaseMessaging.getInstance();
    }

    public String send(final Message message) throws NotificationException {
        try {
            return firebaseMessaging.send(message);
        } catch (final FirebaseMessagingException e) {
            throw new NotificationException(e);
        }
    }
}
