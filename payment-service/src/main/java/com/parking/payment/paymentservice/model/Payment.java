package com.parking.payment.paymentservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "parking_spot_id", nullable = false)
    private Long parkingSpotId;

    @Column(name = "charge_id", nullable = false)
    private String chargeId;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "status", nullable = false)
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "payment_date", nullable = false)
    private Date paymentDate;

    // Optional: Constructor without ID for creating new records
    public Payment(Long userId, Long parkingSpotId, String chargeId, Long amount, String currency, String status, Date paymentDate) {
        this.userId = userId;
        this.parkingSpotId = parkingSpotId;
        this.chargeId = chargeId;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.paymentDate = paymentDate;
    }
}
