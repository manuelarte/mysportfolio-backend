package org.manuel.mysportfolio.services;

import com.google.firebase.messaging.Message;
import org.manuel.mysportfolio.exceptions.NotificationException;

public interface NotificationService {

  String send(Message message) throws NotificationException;

}
