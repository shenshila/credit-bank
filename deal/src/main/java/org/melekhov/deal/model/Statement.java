package org.melekhov.deal.model;

import jakarta.persistence.*;
import lombok.*;
import org.melekhov.deal.model.enums.ApplicationStatus;
import org.melekhov.deal.model.jsonb.StatusHistory;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@Table(name = "statement")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "statement_id")
    private UUID statementId;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client clientId;

    @OneToOne
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id")
    private Credit creditId;

    @Column(name = "status", insertable = false, updatable = false) //не забудь здесь
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "creation_date")
    private LocalDateTime createdOn;

    @Column(name = "applied_offer")
    private String appliedOffer; // Изменить

    @Column(name = "sign_date")
    private LocalDateTime signDate;

    @Column(name = "ses_code")
    private String sesCode; // ????

    @Column(name = "status_history")
    private StatusHistory statusHistory;
}
