package ir.aspacrm.my.app.mahan.events;

import ir.aspacrm.my.app.mahan.model.Payment;

import java.util.List;

/**
 * Created by Microsoft on 3/10/2016.
 */
public class EventOnGetPaymentResponse {
    List<Payment> payments;
    public EventOnGetPaymentResponse(List<Payment> payments) {
        this.payments = payments;
    }
    public List<Payment> getPayments() {
        return payments;
    }
}
