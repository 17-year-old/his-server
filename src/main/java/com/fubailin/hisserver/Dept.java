package com.fubailin.hisserver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class Dept {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;
    private String name;
    private String address;
    private String tel;
    private boolean enabled;
    @JsonIgnore
    @CreatedDate
    private Date createdAt;
    @JsonIgnore
    @CreatedBy
    private Long createBy;
    @JsonIgnore
    @LastModifiedDate
    private Date modifiedAt;
    @JsonIgnore
    @LastModifiedBy
    private Long lastmodifiedBy;
}
