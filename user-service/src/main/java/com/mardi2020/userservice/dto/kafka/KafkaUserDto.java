package com.mardi2020.userservice.dto.kafka;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KafkaUserDto implements Serializable {

    private Schema schema;

    private Payload payload;
}
