package cz.osu.project.service;

import cz.osu.project.database.entity.Contact;
import cz.osu.project.database.entity.User;
import cz.osu.project.database.repository.RoleRepository;
import cz.osu.project.database.repository.UserRepository;
import cz.osu.project.exception.UserErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User create(String email, String lname, String fname, String password, String passwordAgain) throws UserErrorException {
        checkMandatoryFields(email, lname, fname, password);
        if(!password.equals(passwordAgain))
            throw new UserErrorException("Hesla se neshodují");

        User user = new User(email, lname, fname, bCryptPasswordEncoder.encode(password));
        user.setRoles(new HashSet<>(roleRepository.findAll()));

        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private void checkMandatoryFields(String email, String lname, String fname, String password) throws UserErrorException {
        if(email == null || email.isEmpty())
            throw new UserErrorException("Email musí být vyplněn");
        if(lname == null || lname.isEmpty())
            throw new UserErrorException("Jméno musí být vyplněno");
        if(fname == null || fname.isEmpty())
            throw new UserErrorException("Příjmení musí být vyplněno");
        if(password == null || password.isEmpty())
            throw new UserErrorException("Heslo musí být vyplněno");
        if(userRepository.findByEmail(email) != null)
            throw new UserErrorException("Email je již zaregistrován");
    }

    private void checkMandatoryFields(User user) throws UserErrorException {
        this.checkMandatoryFields(user.getEmail(), user.getLname(), user.getFname(), user.getPassword());
    }
}
