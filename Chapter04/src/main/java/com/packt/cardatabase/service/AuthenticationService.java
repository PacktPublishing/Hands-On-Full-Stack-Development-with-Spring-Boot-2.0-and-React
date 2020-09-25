package com.packt.cardatabase.service;

import static java.util.Collections.emptyList;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthenticationService {

  static final long EXPIRATION_TIME = 864_000_00; // 1 day in milliseconds
  static final String SIGNING_KEY = "SecretKey";
  static final String PREFIX = "Bearer";
  static final String AUTHORIZATION = "Authorization";
  static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";

  private AuthenticationService() {
    throw new IllegalStateException("AuthenticationService is a utility class");
  }

  public static void addToken(HttpServletResponse res, String username) {
    String jwtToken =
        Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SIGNING_KEY)
            .compact();
    res.addHeader(AUTHORIZATION, PREFIX + " " + jwtToken);
    res.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS, AUTHORIZATION);
  }

  public static Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(AUTHORIZATION);
    if (token != null) {
      String user =
          Jwts.parser()
              .setSigningKey(SIGNING_KEY)
              .parseClaimsJws(token.replace(PREFIX, ""))
              .getBody()
              .getSubject();

      if (user != null) {
        return new UsernamePasswordAuthenticationToken(user, null, emptyList());
      }
    }
    return null;
  }
}
