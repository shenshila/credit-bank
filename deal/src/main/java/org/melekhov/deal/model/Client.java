package org.melekhov.deal.model;

import jakarta.persistence.*;
import lombok.*;
import org.melekhov.deal.model.enums.Gender;
import org.melekhov.deal.model.enums.MaritalStatus;
import org.melekhov.deal.model.jsonb.Employment;
import org.melekhov.deal.model.jsonb.Passport;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Entity
@Table(name = "client")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    private Gender gender;

    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Column(name = "dependent_amount")
    private Integer dependentAmount;

    @Column(name = "passport_id")
    private Passport passport;

    @Column(name = "employment_id")
    private Employment employment;

    @Column(name = "account_number")
    private String accountNumber;
}
