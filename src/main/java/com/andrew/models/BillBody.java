package com.andrew.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bill_body")
public class BillBody{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billBodyId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private Bill bill;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private Product product;

    private Double currentAmount;
    private Double currentPrice;


}