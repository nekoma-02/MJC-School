package com.epam.esm.audit.entity;

import com.epam.esm.audit.AuditAction;
import com.epam.esm.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_aud")
public class UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private long entityId;
    @Column
    private String name;
    @Enumerated(EnumType.STRING)
    private AuditAction auditAction;
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role;
}
