package com.github.destructio.authorizator3000.model;

import com.github.destructio.authorizator3000.enums.JpaUserProvider;
import com.github.destructio.authorizator3000.model.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.net.URL;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User implements Serializable {
    @Id
    @UuidGenerator
    private UUID id;
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private String name;
    private URL pictureUrl;
    private JpaUserProvider provider;

    private boolean isEnabled = true;
    private boolean isExpired = false;
    private boolean isLocked = false;
    private boolean isPasswordExpired = false;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
}
