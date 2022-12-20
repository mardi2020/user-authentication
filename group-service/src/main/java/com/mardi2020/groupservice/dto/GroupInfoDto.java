package com.mardi2020.groupservice.dto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupInfoDto {

    private Long groupId;

    private String groupName;

    private List<Long> users;
}
