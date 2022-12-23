package com.mardi2020.groupservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mardi2020.groupservice.repository.GroupEntity;
import com.mardi2020.groupservice.repository.GroupRepository;
import com.mardi2020.groupservice.repository.GroupUsers;
import com.mardi2020.groupservice.repository.GroupUsersRepository;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final GroupRepository groupRepository;

    private final GroupUsersRepository groupUsersRepository;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "deleted-user")
    @Transactional
    public void deleteUserInGroup(String kafkaMessage) throws JsonProcessingException {
        log.info("[Kafka message] : " + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        JsonNode jsonNode = objectMapper.readTree(kafkaMessage);
        Long userId = jsonNode.get("payload").get("userId").longValue();
        GroupUsers groupUser = groupUsersRepository.findByUserId(userId);
        log.info("[User's Group ID] : " + groupUser.getGroupEntity().getId());
        if (groupUser.getGroupEntity() != null) {
            groupUsersRepository.delete(groupUser);
            GroupEntity group = groupUser.getGroupEntity();
            if (groupUsersRepository.countByGroupEntity(group) ==  0) {
                groupRepository.delete(group);
            }
        }
    }
}
