package com.andrew.models;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "cash_Box")
public class CashBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private CashBoxNumber cashBoxNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private User user;

    @Transient
    @OneToMany(mappedBy = "cashBox")
    private List<Bill> bill;

    @Transient
    @OneToMany(mappedBy = "cashBox")
    private List<Report> reports;



    private LocalDateTime startDateTime;
    private Double StartMoney;
    private LocalDateTime currentDateTime;
    private Double CurrentMoney;
    private LocalDateTime finishDateTime;
    private Double FinishMoney;
}
