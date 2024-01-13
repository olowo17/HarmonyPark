package com.harmonypark.harmonypark.controller;

import com.harmonypark.harmonypark.PushMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@RequiredArgsConstructor
@Controller
@Data
public class PushMessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    //Mapped as /app/application
    @MessageMapping("/application")
    @SendTo("/all/messages")
    public PushMessage send(final PushMessage message) throws Exception{
        return message;
    }

    // mapped as /app/private
    @MessageMapping("/private")
//
    public void sendToSpecificUser(@Payload PushMessage message){
        System.out.println("Sending message to /user/specific: " + message);
        simpMessagingTemplate.convertAndSendToUser(message.getTo(),"/app/private", message);
    }
}
