package com.loababa.api.common.service.impl;

public interface MessageSender {

    void sendLossamSignupURL(String message);

    void sendErrorNotification(String message);

    void sendConsultingNotification(String contactNumber);

}
