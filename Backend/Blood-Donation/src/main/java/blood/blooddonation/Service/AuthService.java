package blood.blooddonation.Service;


import blood.blooddonation.Dto.RegisterRequest;
import blood.blooddonation.Enums.RequestStatus;
import blood.blooddonation.Enums.Role;
import blood.blooddonation.Model.Donor;
import blood.blooddonation.Model.Patient;
import blood.blooddonation.Model.User;
import blood.blooddonation.Repository.DonorRepository;
import blood.blooddonation.Repository.PatientRepository;
import blood.blooddonation.Repository.UserRepository;
import blood.blooddonation.exception.EmailAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserRepository userRepository;
    private PatientRepository patientRepository;
    private DonorRepository donorRepository;
    public AuthService(UserRepository userRepository,
                       PatientRepository patientRepository,
                       DonorRepository donorRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.donorRepository = donorRepository;
    }

    public void register(RegisterRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("email already exists");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        userRepository.save(user);

        if(request.getRole()== Role.DONOR){
            Donor donor = new Donor();
            donor.setUser(user);
            donor.setBloodType(request.getBloodType());
            donor.setAvailable(true);
            donor.setCity(request.getCity());

            donorRepository.save(donor);
        }
        if(request.getRole()== Role.PATIENT){
            Patient patient = new Patient();
            patient.setUser(user);
            patient.setBloodTypeNeeded(request.getBloodType());
            patient.setCity(request.getCity());
            patient.setStatus(RequestStatus.PENDING);
            patientRepository.save(patient);
        }

    }

}
