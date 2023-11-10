package com.cpkld.service.auth;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cpkld.dto.UserDTO;
import com.cpkld.model.CustomUserDetails;
import com.cpkld.model.entity.Customer;
import com.cpkld.model.entity.User;
import com.cpkld.repository.CustomerRepository;
import com.cpkld.repository.RoleRepository;
import com.cpkld.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveAccount(UserDTO userDTO) {
        User user = new User();
        Customer customer = new Customer();
        //user.setId(null);
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setStatus(1);
        user.setRole(roleRepository.findById(3).orElseThrow());
        userRepository.saveUser(user.getEmail(), user.getPassword(), user.getStatus(), user.getRole().getRoleId());

        if (userRepository.findByEmail(user.getEmail()) != null) {
            //customer.setId(null);
            customer.setFullName(userDTO.getFullname());
            customer.setEmail(userDTO.getEmail());
            customer.setAddress(userDTO.getAddress());
            customer.setPhoneNumber(userDTO.getPhone());
            customer.setUser(userRepository.findByEmail(userDTO.getEmail()));
            customerRepository.saveCustomer(userDTO.getFullname(), userDTO.getAddress(), customer.getEmail(), customer.getPhoneNumber(), customer.getUser().getId());
        }
        // System.out.println(user.toString());
        // System.out.println(customer.toString());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not existed!");
        }
        return new CustomUserDetails(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDTO> getAllAccounts() {
        return userRepository.findAll()
            .stream()
            .map(t -> {
                try {
                    return convertCustomerEntityToDTO(t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            })
            .collect(Collectors.toList());
    }

    private UserDTO convertCustomerEntityToDTO(User user) throws Exception {
        UserDTO userDTO = new UserDTO();
        Customer customer = customerRepository.findByEmail(user.getCustomer().getEmail()).orElseThrow();
        // Role role = user.getRole();
        if (user.getId() != customer.getUser().getId()) {
            throw new Exception();
        }
        userDTO.setId(user.getId());
        userDTO.setFullname(customer.getFullName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(customer.getAddress());
        userDTO.setPhone(customer.getPhoneNumber());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }
    
    // private Role saveRoleIfNotExisted(String roleName) {
    //     Role role = new Role();
    //     role.setRoleName(roleName);
    //     return roleRepository.save(role);
    // }

}
