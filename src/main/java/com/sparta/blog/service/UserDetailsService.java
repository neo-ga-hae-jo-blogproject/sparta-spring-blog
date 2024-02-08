package com.sparta.blog.service;




import com.sparta.blog.entity.User;
import com.sparta.blog.repository.UserRepository;
import com.sparta.blog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsImpl getUserDetails(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found" + email));
        return new UserDetailsImpl(user);
    }


}
