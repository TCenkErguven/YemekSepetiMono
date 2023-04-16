package com.yemeksepeti.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    @Email(message = "Geçerli bir mail adresi giriniz.")
    @Column (unique = true)
    private String email;
    @NotBlank(message = "Lütfen adres bilgisi giriniz.")
    private String address;
    private String phoneNumber;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!])(?=\\S+$).{8,}$",
            message = "Şifre en az bir büyük, bir küçük, harf, rakam, ve özel karakterden oluşmalıdır.")
    @NotBlank(message = "Şifre bilgilerini boş bırakmayınız.")
    private String password;
    @NotBlank(message = "Kart bilgilerini doldurunuz.")
    private String cardDetails;
    @Min(0)
    @Builder.Default
    private Double balance = 0.0;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EStatus status  = EStatus.PENDING;
    private String activationCode;
}
