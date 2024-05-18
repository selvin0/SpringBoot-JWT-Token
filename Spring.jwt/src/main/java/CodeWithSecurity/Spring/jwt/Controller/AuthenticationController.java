package CodeWithSecurity.Spring.jwt.Controller;

import CodeWithSecurity.Spring.jwt.Service.AuthenticationService;
import CodeWithSecurity.Spring.jwt.model.AuthenticationResponse;
import CodeWithSecurity.Spring.jwt.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController
{
   private final AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User request)
    {
       return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User request)
    {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
