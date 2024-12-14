package com.parking.payment.paymentservice.repository;

import com.parking.payment.paymentservice.model.PaymentRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentRequestModel, Long> {
    // Custom query methods can be added here if needed
}
