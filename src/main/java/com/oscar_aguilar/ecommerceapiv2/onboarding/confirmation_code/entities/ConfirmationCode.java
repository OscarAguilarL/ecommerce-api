package com.oscar_aguilar.ecommerceapiv2.onboarding.confirmation_code.entities;

import com.oscar_aguilar.ecommerceapiv2.user.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer code;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    public ConfirmationCode(
            Integer code,
            LocalDateTime expiresAt,
            LocalDateTime createdAt,
            User user
    ) {
        this.code = code;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.user = user;
    }
}
