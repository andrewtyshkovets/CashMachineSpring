package com.andrew.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String fullName;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private State state;

    @Transient
    @OneToMany(mappedBy = "user")
    private List<CashBox> cashBox;

    @Transient
    @OneToMany(mappedBy = "cashier")
    private List<Bill> bills;

    @Transient
    @OneToMany(mappedBy = "user")
    List<Token> tokens;
}



