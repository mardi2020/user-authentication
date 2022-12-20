package com.mardi2020.groupservice.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GroupUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = GroupEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "group_id")
    private GroupEntity groupEntity;

    @Column(nullable = false)
    private Long userId;
}
