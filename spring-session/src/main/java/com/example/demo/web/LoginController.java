package com.example.demo.web;

import com.example.demo.core.MemberDTO;
import com.example.demo.exception.CustomAuthenticationException;
import com.example.demo.exception.LoginFailedException;
import com.example.demo.provider.LoginService;
import com.example.demo.web.dto.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/api/login/v1")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public String login(HttpSession httpSession, @RequestBody LoginRequestDTO loginRequestDTO) {


        Optional<MemberDTO> optionalMember = loginService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

        if(optionalMember.isPresent()) {
            httpSession.setAttribute("role", optionalMember.get().getRole().name());
            httpSession.setMaxInactiveInterval(1800);
        } else {
            throw new LoginFailedException();
        }

        return "ok";
    }
}