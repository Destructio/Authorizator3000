package com.github.destructio.authorizator3000.model;

import com.github.destructio.authorizator3000.provider.Provider;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User implements Serializable {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(unique = true)
    @NotBlank @Size(min = 1, max = 20)
    private String username;

    private String password;

    @Email
    @Column(unique = true)
    private String email;

    @Size(min = 1, max = 50)
    private String name;

    @URL
    private String pictureUrl;

    @NotNull
    private Provider provider;

    private boolean isEnabled = true;
    private boolean isExpired = false;
    private boolean isLocked = false;
    private boolean isPasswordExpired = false;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
}
