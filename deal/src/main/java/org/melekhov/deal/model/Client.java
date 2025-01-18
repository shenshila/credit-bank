package org.melekhov.deal.model;

import jakarta.persistence.*;
import lombok.*;
import org.melekhov.deal.model.jsonb.Employment;
import org.melekhov.deal.model.jsonb.Passport;
import org.melekhov.shareddto.enums.Gender;
import org.melekhov.shareddto.enums.MaritalStatus;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "client_id")
    private UUID clientId;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "marital_status")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name = "dependent_amount")
    private Integer dependentAmount;

    @Column(columnDefinition = "jsonb", name = "passport_id")
    @Embedded
    private Passport passport;

    @Column(columnDefinition = "jsonb", name = "employment_id")
    @Embedded
    private Employment employment;

    @Column(name = "account_number")
    private String accountNumber;
}
