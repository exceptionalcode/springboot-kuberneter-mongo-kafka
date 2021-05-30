package com.hunza.event.caterer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author ishaan.solanki
 * <p>
 * Class {@link KafkaProducerService} is responsible to produce the message on the topic.
 * <p>It will generate or produce the message produces in kafka cluster</p>
 */
@Service
public class KafkaProducerService {

    /**
     * Logger for {@link KafkaConsumerService}
     */
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    /**
     * It is a kafka topic 'caterer_topic'
     */
    private static final String KAFKA_TOPIC = "caterer_topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Method is responsible to produce the message.
     *
     * <p>Method produces the message and publishes on a topic 'caterer_topic'.</p>
     *
     * @param caterer {@link String}
     */
    public void produce(String caterer) {
        logger.info("produce message to kafka topic!!");
        kafkaTemplate.send(KAFKA_TOPIC, caterer);
    }
}
