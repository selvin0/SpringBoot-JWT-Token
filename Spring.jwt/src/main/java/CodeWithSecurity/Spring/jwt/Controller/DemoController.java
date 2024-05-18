package CodeWithSecurity.Spring.jwt.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController
{
    @GetMapping("/demo")
    public ResponseEntity<String > demo()
    {
        return ResponseEntity.ok("hello From secured url");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> adminOnly()
    {
      return ResponseEntity.ok("Hello from admin only");
    }
}
