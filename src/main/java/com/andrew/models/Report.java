package com.andrew.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ReportType type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private CashBox cashBox;


    private LocalDateTime startTime;
    private Double startMoney;
    private LocalDateTime finishTime;
    private Double finishMoney;
    private Double sumOfSales;
    private Integer totalClosed;
    private Integer totalCancelled;

}
