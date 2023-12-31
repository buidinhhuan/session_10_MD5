package security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import security.model.dto.request.FormSignInDto;
import security.model.dto.request.FormSignUpDto;
import security.model.dto.response.JwtResponse;
import security.security.jwt.JwtProvider;
import security.security.user_principle.UserPrinciple;
import security.service.IUserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v4/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("vào được rồi nè");
    }
    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> signin(@RequestBody FormSignInDto formSignInDto){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(formSignInDto.getUsername(),formSignInDto.getPassword())
        ); // tạo đối tương authentiction để xác thực thông qua username va password
        // tạo token và trả về cho người dùng
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        String token = jwtProvider.generateToken(userPrinciple);
        // lấy ra user principle
        List<String> roles = userPrinciple.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseEntity.ok(JwtResponse.builder().token(token)
                .name(userPrinciple.getName())
                .username(userPrinciple.getUsername())
                .roles(roles)
                        .type("Bearer")
                .status(userPrinciple.isStatus()).build());
    }
    @PostMapping("/sign-up")
    private ResponseEntity<String> signup(@RequestBody FormSignUpDto formSignUpDto){
        userService.save(formSignUpDto);
        return new ResponseEntity("success",HttpStatus.CREATED);

    }
}
