package com.example.backend.mongouser;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/barista/user")
@RequiredArgsConstructor
public class MongoUserController {

    private  final MongoUserDetailsService service;

    @GetMapping("/me")
    public String getMe1(Principal principal) {
        if (principal != null){
            return principal.getName();
        }
        return "AnonymousUser";
    }

    @GetMapping("/me2")
    public String getMe2(){
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    @PostMapping ("/login")
    public String login(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        SecurityContextHolder.clearContext();
        return "logged out";
    }

    @PostMapping("/register")
    public  String saveNewUser(@RequestBody MongoUser user) {
        return service.saveUser(user).getUsername();
    }

}