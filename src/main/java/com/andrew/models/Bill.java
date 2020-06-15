package com.andrew.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private CashBox cashBox;

    @Transient
    @OneToMany(mappedBy = "bill")
    private List<BillBody> billBodies;


    private LocalDateTime currentDate;
    private Double totalPrice;
    private boolean isCancelled;
    private boolean isClosed;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private User cashier;

    @Transient
    private Map<Long, Double> productAmount;
    @Transient
    private Map<Long, Double> productPrice;

}
