package CodeWithSecurity.Spring.jwt.Service;
import CodeWithSecurity.Spring.jwt.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;
@Service
public class JwtService
{
    private final String SECRET_KEY="e9357d4199aedcc758450edd98510f09d0ff0e1f45b0d98b1fabf0770a544c86";


     public String extractUsername(String token)    //5
     {
         return extractClaim(token,Claims::getSubject);
     }

     public  boolean isValid(String token, UserDetails user)  //6
     {
         String username=extractUsername(token);
         return username.equals((user.getUsername())) && !isTokenExpired(token);
     }

    private boolean isTokenExpired(String token)        //7
    {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token)       //8
    {
        return extractClaim(token,Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims,T> resolver)  // 4
    {
             Claims claims=extractAllClaims(token);
             return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token)  //3
    {
        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(User user)  //1
    {
        String token=
                Jwts.builder()
                        .subject(user.getUsername())
                        .issuedAt(new Date(System.currentTimeMillis()))
                        .expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
                        .signWith(getSigninKey())
                        .compact();
        return token;
    }

    private SecretKey  getSigninKey()   //2
    {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
