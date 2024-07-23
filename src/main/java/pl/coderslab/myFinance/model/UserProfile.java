package pl.coderslab.myFinance.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profileId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "first_name", length = 50)
    @Size(max = 50, message = "First name should not exceed 50 characters.")
    private String firstName;

    @Column(name = "last_name", length = 50)
    @Size(max = 50, message = "Last name should not exceed 50 characters.")
    private String lastName;

    @Column(name = "address", length = 255)
    @Size(max = 255, message = "Address should not exceed 255 characters.")
    private String address;

    @Column(name = "phone_number", length = 20)
    @Size(max = 20, message = "Phone number should not exceed 20 characters.")
    private String phoneNumber;
}