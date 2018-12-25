package com.fubailin.hisserver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private final Long id;
    private String name;
    private String username;
    private String password;
    private String IDCard;
    private String telephone;
    private String email;
    private String employeeID;
    private String province;
    private String city;
    private String address;
    @JsonIgnore
    private boolean accountExpired = false;
    @JsonIgnore
    private boolean credentialsExpired = false;
    @JsonIgnore
    private boolean locked = false;
    @JsonIgnore
    private boolean enabled = true;
    @JsonIgnore
    @CreatedDate
    private Date createdAt;
    @JsonIgnore
    @CreatedBy
    private String createBy;
    @JsonIgnore
    @LastModifiedDate
    private Date modifiedAt;
    @JsonIgnore
    @LastModifiedBy
    private String lastmodifiedBy;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("GUEST"));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return !credentialsExpired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }
}
