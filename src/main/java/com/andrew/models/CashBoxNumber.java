package com.andrew.models;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cash_box_numbers")
public class CashBoxNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    @OneToMany(mappedBy = "cashBoxNumber")
    private List<CashBox> cashBoxes;

    private Integer number;
}
