package com.mardi2020.groupservice.dto;

import lombok.Data;

@Data
public class CreateGroupDto {

    private Long userId;

    private String groupName;
}
