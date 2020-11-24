package cz.osu.project.service;

import cz.osu.project.database.entity.User;
import cz.osu.project.database.repository.RoleRepository;
import cz.osu.project.database.repository.UserRepository;
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

    public User create(String email, String lname, String fname, String password, String passwordAgain) throws Exception {
        if(email == null || email.isEmpty())
            throw new Exception("Email musí být vyplněn");
        if(lname == null || lname.isEmpty())
            throw new Exception("Jméno musí být vyplněno");
        if(fname == null || fname.isEmpty())
            throw new Exception("Příjmení musí být vyplněno");
        if(!password.equals(passwordAgain))
            throw new Exception("Hesla se neshodují");
        if(userRepository.findByEmail(email) != null)
            throw new Exception("Email je již zaregistrován");

        User user = new User(email, lname, fname, bCryptPasswordEncoder.encode(password));
        user.setRoles(new HashSet<>(roleRepository.findAll()));

        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
