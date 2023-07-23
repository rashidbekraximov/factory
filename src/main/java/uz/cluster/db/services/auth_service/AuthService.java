package uz.cluster.db.services.auth_service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.cluster.annotation.CheckPermission;
import uz.cluster.db.entity.auth.User;
import uz.cluster.db.entity.references.model.*;
import uz.cluster.db.model.JwtResponse;
import uz.cluster.db.repository.references.DegreeRepository;
import uz.cluster.db.repository.references.GenderRepository;
import uz.cluster.db.repository.references.IdentityDocumentTypeRepository;
import uz.cluster.db.repository.user_info.RoleRepository;
import uz.cluster.db.repository.user_info.UserRepository;
import uz.cluster.db.services.file_transfer_service.AttachmentService;
import uz.cluster.enums.auth.Action;
import uz.cluster.enums.auth.SystemRoleName;
import uz.cluster.enums.forms.FormEnum;
import uz.cluster.payload.auth.LoginDTO;
import uz.cluster.payload.auth.UserDTO;
import uz.cluster.payload.response.ApiResponse;
import uz.cluster.security.jwt.JwtProvider;
import uz.cluster.util.LanguageManager;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;
    private final AttachmentService attachmentService;
    private final IdentityDocumentTypeRepository identityDocumentTypeRepository;
    private final DegreeRepository degreeRepository;
    private final GenderRepository genderRepository;
    private final RoleRepository roleRepository;

    @Transactional
    @CheckPermission(form = FormEnum.ADMIN_PANEL, permission = Action.CAN_ADD)
    public ApiResponse add(UserDTO userDTO) { // This method is to add
        if (userRepository.existsByLoginAndIdNot(userDTO.getLogin(), 0)) //This will check login is unique or not, if not it terminates from adding
            return new ApiResponse(false, LanguageManager.getLangMessage("phone_exists"));

        if (userRepository.existsByEmailAndIdNot(userDTO.getEmail(), 0)) //This will check that email is already exists or not, if not it terminates from adding
            return new ApiResponse(false, LanguageManager.getLangMessage("email_exists"));

        if (userRepository.existsByDocumentSerialNumberAndIdNot(userDTO.getDocumentSerialNumber(), 0)) //This check that passport number is already exists or not, if not it terminates from adding
            return new ApiResponse(false, LanguageManager.getLangMessage("passport_number_exists"));

        if (userDTO.getPassword().isEmpty()) //This check that user has passport or not, if not it terminates from adding
            return new ApiResponse(false, LanguageManager.getLangMessage("no_password"));

        Optional<IdentityDocumentType> optionalIdentityDocumentType = identityDocumentTypeRepository.findById(userDTO.getDocumentTypeId() != null ? userDTO.getDocumentTypeId() : 0);
        Optional<Degree> optionalDegree = degreeRepository.findById(userDTO.getDegreeId() != null ? userDTO.getDegreeId() : 0);
        Optional<Gender> optionalGender = genderRepository.findById(userDTO.getGenderId() != null ? userDTO.getGenderId() : 0);
        Optional<Role> optionalRole = roleRepository.findById(userDTO.getRoleId() != null ? userDTO.getRoleId() : 0);

        if (optionalIdentityDocumentType.isEmpty()) //This check that user has this info or not, if not this statement terminates from adding
            return new ApiResponse(false, LanguageManager.getLangMessage("no_identity_type"));

        if (optionalDegree.isEmpty()) //This check that user has this info or not, if not this statement terminates from adding
            return new ApiResponse(false, LanguageManager.getLangMessage("no_degree"));

        if (optionalGender.isEmpty()) //This check that user has this info or not, if not this statement terminates from adding
            return new ApiResponse(false, LanguageManager.getLangMessage("no_gender"));

        if (
                optionalRole.isEmpty()
                        && (userDTO.getSystemRoleName() == SystemRoleName.SYSTEM_ROLE_FORM_MEMBER || userDTO.getSystemRoleName() == SystemRoleName.SYSTEM_ROLE_MEMBER)
        ) //This check that user has this info or not, if not this statement terminates from adding
            return new ApiResponse(false, LanguageManager.getLangMessage("no_position"));

        if (Period.between(userDTO.getBirthday(), LocalDate.now()).getYears() < 18)  //This check that user's age is not less than AGE_RESTRICTION, if not this statement terminates from adding
            return new ApiResponse(false, LanguageManager.getLangMessage("age_restriction"));

        User user = new User(
                userDTO.getFirstName().substring(0, 1).toUpperCase(Locale.ROOT) + userDTO.getFirstName().toLowerCase().substring(1),
                userDTO.getLastName().substring(0, 1).toUpperCase(Locale.ROOT) + userDTO.getLastName().toLowerCase().substring(1),
                userDTO.getMiddleName().substring(0, 1).toUpperCase(Locale.ROOT) + userDTO.getMiddleName().toLowerCase().substring(1),
                optionalIdentityDocumentType.get(),
                userDTO.getDocumentSerialNumber().toUpperCase(),
                userDTO.getBirthday(),
                optionalDegree.get(),
                userDTO.getLogin(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                UUID.randomUUID().toString(),
                optionalGender.get(),
                userDTO.getNotes(),
                userDTO.getSystemRoleName(),
                userDTO.isAccountNonLocked(),
                userDTO.getClusterId()
        );
//        optionalRole.ifPresent(user::setRole);

        if (
                user.getSystemRoleName() != SystemRoleName.SYSTEM_ROLE_FORM_MEMBER
                        && user.getSystemRoleName() != SystemRoleName.SYSTEM_ROLE_MEMBER
        )
//            user.setRole(null);


        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); //This sets user's password that is encrypted
//        ApiResponse apiResponse = attachmentService.uploadFileToFileSystem(request); //This is after uploading users file to system, results is ApiResponse obj
//        Object object = apiResponse.getObject(); // we are getting attachments from ApiResponse

//        if (apiResponse.isSuccess()) { //this assigns the user attachment to user
//            user.setUserImage((Attachment) ((HashMap<?, ?>) object).get(1));
//            user.setDocumentImage((Attachment) ((HashMap<?, ?>) object).get(2));
//            user.setCertificateImage((Attachment) ((HashMap<?, ?>) object).get(3));
//        }
//        user.setEnabled(true);
        userRepository.save(user); //this saves user to database
        return new ApiResponse(true, user, LanguageManager.getLangMessage("saved"));
    }

    @Transactional
    @CheckPermission(form = FormEnum.ADMIN_PANEL, permission = Action.CAN_EDIT)
    public ApiResponse edit(UserDTO userDTO, MultipartHttpServletRequest request, int id) {
        ApiResponse apiResponse = new ApiResponse();
        if (userRepository.existsByLoginAndIdNot(userDTO.getLogin(), userDTO.getId())) //This will check login is unique or not, if not it terminates from adding
            return new ApiResponse(false, LanguageManager.getLangMessage("phone_exists"));

        if (userRepository.existsByEmailAndIdNot(userDTO.getEmail(), userDTO.getId())) //This will check that email is already exists or not, if not it terminates from adding
            return new ApiResponse(false, LanguageManager.getLangMessage("email_exists"));

        if (userRepository.existsByDocumentSerialNumberAndIdNot(userDTO.getDocumentSerialNumber(), userDTO.getId())) //This check that passport number is already exists or not, if not it terminates from adding
            return new ApiResponse(false, LanguageManager.getLangMessage("passport_number_exists"));

        Optional<User> optionalUser = userRepository.findById(id);
        Optional<IdentityDocumentType> optionalIdentityDocumentType = identityDocumentTypeRepository.findById(userDTO.getDocumentTypeId());
        Optional<Degree> optionalDegree = degreeRepository.findById(userDTO.getDegreeId());
        Optional<Gender> optionalGender = genderRepository.findById(userDTO.getGenderId());

        Optional<Role> optionalRole =  (userDTO.getRoleId() ==null )?null: roleRepository.findById(userDTO.getRoleId());

        if (optionalUser.isEmpty()) //This check that there is such user, if not this statement terminates from adding
            return new ApiResponse(false, id, LanguageManager.getLangMessage("no_user"));

        if (optionalIdentityDocumentType.isEmpty()) //This check that user has this info or not, if not this statement terminates from adding
            return new ApiResponse(false, id, LanguageManager.getLangMessage("no_identity_type"));

        if (optionalDegree.isEmpty()) //This check that user has this info or not, if not this statement terminates from adding
            return new ApiResponse(false, id, LanguageManager.getLangMessage("no_degree"));

        if (optionalGender.isEmpty()) //This check that user has this info or not, if not this statement terminates from adding
            return new ApiResponse(false, id, LanguageManager.getLangMessage("no_gender"));

        if ( (userDTO.getSystemRoleName() == SystemRoleName.SYSTEM_ROLE_FORM_MEMBER || userDTO.getSystemRoleName() == SystemRoleName.SYSTEM_ROLE_MEMBER) &&( optionalRole == null ||optionalRole.isEmpty())) //This check that user has this info or not, if not this statement terminates from adding
            return new ApiResponse(false, id, LanguageManager.getLangMessage("no_position"));

        if (Period.between(userDTO.getBirthday(), LocalDate.now()).getYears() < 18) //This check that user's age is not less than 18, if not this statement terminates from adding
            return new ApiResponse(false, optionalUser.get(), LanguageManager.getLangMessage("age_restriction"));

        User editingUser = optionalUser.get();
        editingUser.setFirstName(userDTO.getFirstName());
        editingUser.setLastName(userDTO.getLastName());
        editingUser.setMiddleName(userDTO.getMiddleName());
        editingUser.setLogin(userDTO.getLogin());
//        editingUser.setDocumentType(optionalIdentityDocumentType.get());
        editingUser.setDocumentSerialNumber(userDTO.getDocumentSerialNumber());
//        editingUser.setBirthday(userDTO.getBirthday());
//        editingUser.setDegree(optionalDegree.get());
//        editingUser.setGender(optionalGender.get());
//        editingUser.setNotes(userDTO.getNotes());
//        editingUser.setClusterId(userDTO.getClusterId());
//        editingUser.setAccountNonLocked(userDTO.isAccountNonLocked());
        editingUser.setSystemRoleName(userDTO.getSystemRoleName());

        if ( editingUser.getSystemRoleName() != SystemRoleName.SYSTEM_ROLE_FORM_MEMBER && editingUser.getSystemRoleName() != SystemRoleName.SYSTEM_ROLE_MEMBER )
//            editingUser.setRole(null);
//        else
//            editingUser.setRole(optionalRole.get());


        if (!userDTO.getPassword().isEmpty()) //If user's password is changed, it again encrypts it and assigns to user
            editingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

//        ApiResponse apiResponseFromAttachment = attachmentService.uploadFileToFileSystem(request); //This uploads all files to system
//        if (apiResponseFromAttachment.isSuccess()) { // if user's files are changed, it changes from DB of Attachment and assigns to user
//            Object object = apiResponseFromAttachment.getObject();
//            Attachment userImage = (Attachment) ((HashMap<?, ?>) object).get(1); //This gets userImage info from attachmentService upload
//            Attachment documentImage = (Attachment) ((HashMap<?, ?>) object).get(2); //This gets documentImage info from attachmentService upload
//            Attachment certificateImage = (Attachment) ((HashMap<?, ?>) object).get(3); //This gets certificateImage info from attachmentService upload
//            if (userImage != null) //if any changes in Attachments, it assigns new Attachment to User
//                editingUser.setUserImage(userImage);
//            if (documentImage != null) //if any changes in Attachments, it assigns new Attachment to User
//                editingUser.setUserImage(userImage);
//            if (certificateImage != null) //if any changes in Attachments, it assigns new Attachment to User
//                editingUser.setUserImage(userImage);
//        }

//        editingUser.setEnabled(true);
        User editedUser = userRepository.save(editingUser); //This will save User to DB
        apiResponse.setSuccess(true); //apiResponse status -> True or False
        apiResponse.setObject(editedUser); //this is User obj to give to Front-end
        apiResponse.setMessage(LanguageManager.getLangMessage("edited")); //ApiResponses message
        return apiResponse;
    }

    @CheckPermission(form = FormEnum.ADMIN_PANEL, permission = Action.CAN_VIEW)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @CheckPermission(form = FormEnum.ADMIN_PANEL, permission = Action.CAN_VIEW)
    public User getById(int id) {
        return userRepository.findById(id).orElse(new User());
    }

    public ApiResponse delete(int id) {
        if (!userRepository.existsById(id))
            return new ApiResponse(false, LanguageManager.getLangMessage("cant_find"));
        userRepository.deleteById(id);
        return new ApiResponse(true, LanguageManager.getLangMessage("deleted"));
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(login));
    }

    public JwtResponse login(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getLogin(),
                    loginDTO.getPassword()
            )); //System checks user is exists or not
            User user = (User) authentication.getPrincipal(); //Casts Principal to User

            String token = jwtProvider.generateToken(user.getLogin());
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(authentication);

            return new JwtResponse(
                    token,
                    true,
                    user.getFio(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getMiddleName(),
                    "",
                    user.getEmail(),
                    user.getLogin(),
                    user.getSystemRoleName().name(),
                    user.getClusterId(),
                    ""
            );
        } catch (Exception exception) {
            return new JwtResponse(
                    null,
                    false,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    0,
                    null
            );
        }
    }
}
