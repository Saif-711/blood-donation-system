package blood.blooddonation.Service;


import blood.blooddonation.Dto.LoginRequest;
import blood.blooddonation.Dto.LoginResponse;
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
import blood.blooddonation.exception.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtService jwtService;
    private UserRepository userRepository;
    private PatientRepository patientRepository;
    private DonorRepository donorRepository;
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    public AuthService(UserRepository userRepository,
                       PatientRepository patientRepository,
                       DonorRepository donorRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService
                        ) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.donorRepository = donorRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("email already exists");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
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
    public LoginResponse signin(LoginRequest request){
        //manually verify from here
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new InvalidCredentialsException("Invalid Email or Password"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid Email or Password");
        }
        // to here (or easier using authenticationManager to verify direct)
        String token = jwtService.generateToken(user.getEmail());
        return new LoginResponse(
                token,
                user.getEmail(),
                user.getRole().name()
        );
    }
    public LoginResponse authenticate(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new InvalidCredentialsException("Invalid Email or Password"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    request.getPassword()
                )
        );
        String token = jwtService.generateToken(user.getEmail());
        return new LoginResponse(
            token,
            user.getEmail(),
            user.getRole().name()
        );
    }

}
