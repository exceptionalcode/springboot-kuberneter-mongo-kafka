package com.hunza.event.caterer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author ishaan.solanki
 * <p>
 * Class {@link KafkaConsumerService} is responsible to consume the message stored in topic.
 * <p>It will access or consume the message produces in kafka cluster</p>
 */
@Service
public class KafkaConsumerService {

    /**
     * Logger for {@link KafkaConsumerService}
     */
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    /**
     * Method is responsible to consume the message.
     *
     * <p>Method consume the message published on a topic 'caterer_topic'
     * against the group 'hunza-group-id'.</p>
     *
     * @param caterer {@link String}
     */
    @KafkaListener(topics = "caterer_topic",
            groupId = "hunza-group-id")
    public void consume(String caterer) {
        logger.info(String.format("###Consumer: Caterer with name add -> %s", caterer));
    }
}
