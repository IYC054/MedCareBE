package fpt.aptech.pjs4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;


    @Size(max = 50)
    @NotNull
    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;

    @Size(max = 60)
    @NotNull
    @Column(name = "status", nullable = false, length = 60)
    private String status;

    @ManyToOne(optional = false)
    @JsonIgnore
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @Size(max = 100)
    @NotNull
    @Column(name = "transaction_code", nullable = false, length = 100)
    private String transactionCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "vipappointment_id")
    private VipAppointment vipappointment;

    @JsonProperty("vipappointment_id")
    public Integer getVIPAppointmentId() {
        return vipappointment != null ? vipappointment.getId() : null;
    }
    @JsonProperty("appointment_id")
    public Integer getAppointmentId() {
        return appointment != null ? appointment.getId() : null;
    }
    @Column(name = "transaction_date")
    private Instant transactionDate;

    @Size(max = 250)
    @Nationalized
    @Column(name = "transaction_description", length = 250)
    private String transactionDescription;

}