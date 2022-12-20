package com.mardi2020.groupservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class LeaveGroupDto {

    private Long userId;

    private Long groupId;

    public LeaveGroupDto(Long groupId, Long userId) {
        this.groupId = groupId;
        this.userId = userId;
    }
}
