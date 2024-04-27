package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.config.AWSConfig;
import com.fastcampus.boardserver.service.SnsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

import java.util.Map;

@Log4j2
@RestController
public class SnsController {

    private final AWSConfig awsConfig;
    private final SnsService snsService;

    public SnsController(AWSConfig awsConfig, SnsService snsService) {
        this.awsConfig = awsConfig;
        this.snsService = snsService;
    }

    @PostMapping("/create-topic")
    public ResponseEntity<String> createTopic(@RequestParam final String topic) {
        final CreateTopicRequest createTopicRequest = CreateTopicRequest.builder()
                .name(topic)
                .build();

        SnsClient snsclient = snsService.getSnsClient();
        final CreateTopicResponse createTopicResponse = snsclient.createTopic(createTopicRequest);

        if(!createTopicResponse.sdkHttpResponse().isSuccessful()) {
            throw getResponseStatusException(createTopicResponse);
        }
        log.info("Topic created successfully" + createTopicResponse.sdkHttpResponse());
        snsclient.close();
        return new ResponseEntity<>("Topic created successfully", HttpStatus.OK);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestParam final String endpoint,
                                            @RequestParam final String topicArn) {
        final SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .protocol("https")
                .topicArn(topicArn)
                .endpoint(endpoint)
                .build();

        SnsClient snsclient = snsService.getSnsClient();
        final SubscribeResponse subscribeResponse = snsclient.subscribe(subscribeRequest);

        if(!subscribeResponse.sdkHttpResponse().isSuccessful()) {
            throw getResponseStatusException(subscribeResponse);
        }
        log.info("Subscribe successfully" + subscribeResponse.sdkHttpResponse());
        snsclient.close();
        return new ResponseEntity<>("Subscribe successfully", HttpStatus.OK);
    }

    @PostMapping("/publish")
    public String publish(@RequestParam final String topicArn
                                        , @RequestBody Map<String, Object> message) {
        SnsClient snsclient = snsService.getSnsClient();
        final PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(topicArn)
                .subject("HTTP ENDPOINT TEST MESSAGE")
                .message(message.toString())
                .build();

        PublishResponse publishResponse = snsclient.publish(publishRequest);
        log.info("Publish successfully" + publishResponse.sdkHttpResponse());
        snsclient.close();

        return publishResponse.messageId();

    }

    private ResponseStatusException getResponseStatusException(SnsResponse snsResponse) {
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, snsResponse.sdkHttpResponse().statusText().get());
    }

}
