package com.mardi2020.userservice.dto.kafka;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payload {

    private Long userId;
}
