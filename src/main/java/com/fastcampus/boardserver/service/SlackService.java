package com.fastcampus.boardserver.service;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiErrorResponse;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import lombok.extern.log4j.Log4j2;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Log4j2
public class SlackService {
    @Value(value = "${slack.token}")
    String slackToken;

    public void sendSlackMessage(String message, String channel) {
        String channelAddress = "";

        if(channel.equals("error")) {
            channelAddress = "#공부용";
        }

        try {
            MethodsClient methodsClient = Slack.getInstance().methods(slackToken);

            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(channelAddress)
                    .text(message).build();
            ChatPostMessageResponse chatPostMessageResponse = methodsClient.chatPostMessage(request);

            if(!chatPostMessageResponse.isOk()) {
                log.error(chatPostMessageResponse.getMessage());
            }else {
                log.info("sendSlackMessage SUCCESS" + channel);
            }
        }catch (SlackApiException | IOException e){
            log.error(e.getMessage());
        }
    }
}
