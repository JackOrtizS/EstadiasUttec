package com.jack.estadiasuttec.Auth;

import com.jack.estadiasuttec.Entitys.User.Role;
import com.jack.estadiasuttec.Entitys.User.User;
import com.jack.estadiasuttec.Entitys.User.UserRepository;
import com.jack.estadiasuttec.Jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getMatricula(), request.getPassword()));

        UserDetails user = userRepository.findByMatricula(request.getMatricula()).orElseThrow();

        String token = jwtService.getToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request){
        User user = User.builder()
                .nombre(request.nombre)
                .apellido(request.apellido)
                .matricula(request.matricula)
                .correo(request.correo)
                .password(passwordEncoder.encode(request.getPassword()))
                .carrera(request.carrera)
                .role(Role.User)
                .build();

        System.out.println(user.getPassword());

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken((UserDetails) user))
                .build();
    }

}
