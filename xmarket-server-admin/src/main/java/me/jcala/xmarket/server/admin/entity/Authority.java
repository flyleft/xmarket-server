package me.jcala.xmarket.server.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Authority {
        private String username;
        private String password;
        private String password_salt;
        private Set<Role> roles;
}