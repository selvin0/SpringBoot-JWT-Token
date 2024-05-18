package CodeWithSecurity.Spring.jwt.Service;
import CodeWithSecurity.Spring.jwt.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class UserDetailsImpl implements UserDetailsService
{
     private final UserRepository userRepository;
    public UserDetailsImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
           return userRepository.findByUsername(username)
                   .orElseThrow(()->new UsernameNotFoundException("user not found"));
    }
}
