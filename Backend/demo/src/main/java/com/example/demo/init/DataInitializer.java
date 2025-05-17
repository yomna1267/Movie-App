package com.example.demo.init;

import com.example.demo.model.Role;
import com.example.demo.model.Users;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Role adminRoleEntity = roleRepository.findByName("ADMIN")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ADMIN");
                    return roleRepository.save(role);
                });

        Role userRoleEntity = roleRepository.findByName("USER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("USER");
                    return roleRepository.save(role);
                });

        userRepository.findByUsername("admin").orElseGet(() -> {
            Users admin = new Users();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("adminpass"));
            admin.setRole(adminRoleEntity);
            return userRepository.save(admin);
        });

        userRepository.findByUsername("yomna").orElseGet(() -> {
            Users yomna = new Users();
            yomna.setUsername("yomna");
            yomna.setEmail("yomna@example.com");
            yomna.setPassword(passwordEncoder.encode("yomna"));
            yomna.setRole(adminRoleEntity);
            return userRepository.save(yomna);
        });

        userRepository.findByUsername("user").orElseGet(() -> {
            Users user = new Users();
            user.setUsername("user");
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("userpass"));
            user.setRole(userRoleEntity);
            return userRepository.save(user);
        });

    }
}
