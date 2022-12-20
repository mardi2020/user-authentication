package com.mardi2020.userservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mardi2020.userservice.dto.kafka.Field;
import com.mardi2020.userservice.dto.kafka.FieldType;
import com.mardi2020.userservice.dto.kafka.KafkaUserDto;
import com.mardi2020.userservice.dto.kafka.Payload;
import com.mardi2020.userservice.dto.kafka.Schema;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper mapper;

    private final List<Field> fields = Collections.singletonList(new Field(FieldType.STRING, true, "userId"));

    private final Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("users")
            .build();

    public Long send(String topic, Long userId) {
        Payload payload = Payload.builder()
                .userId(userId)
                .build();

        KafkaUserDto userDto = KafkaUserDto.builder()
                .schema(schema)
                .payload(payload)
                .build();

        String data = null;
        try {
            data = mapper.writeValueAsString(userDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(topic, data);
        log.info("[Kafka Producer SENT data - User service] : " + userDto);

        return userId;
    }
}
