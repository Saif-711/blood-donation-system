package blood.blooddonation.Model;

import blood.blooddonation.Enums.BloodType;
import blood.blooddonation.Enums.RequestStatus;
import jakarta.persistence.*;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private BloodType bloodTypeNeeded;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private String city;

    // getters & setters
    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public BloodType getBloodTypeNeeded() { return bloodTypeNeeded; }
    public void setBloodTypeNeeded(BloodType bloodTypeNeeded) { this.bloodTypeNeeded = bloodTypeNeeded; }

    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}
