package com.andrew.models;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer code;

    private String name;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private Measure measure;

    private Double price;

    @Transient
    @OneToMany(mappedBy = "billBodyId")
    private List<BillBody> billBody;
}
