package com.fubailin.hisserver.Security;

import com.fubailin.hisserver.User;
import com.fubailin.hisserver.config.Config;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

//核心功能是在验证用户名密码正确后，生成一个token，并将token返回给客户端
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {
    private Config config;
    private UserDetailsService userDetailsService;

    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(Config config, UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.config = config;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    //接收并解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());

        Authentication authResult = authenticationManager.authenticate(authenticationToken);

        return authResult;
    }

    //用户成功登录后，这个方法会被调用，我们在这个方法里生成token
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        String username = authResult.getName();
        User user = (User) userDetailsService.loadUserByUsername(username);
        long id = user.getId();
        byte[] secretKeyBytes = Base64.getDecoder().decode(config.getJwtSecretKey());

        SecretKey secretKeykey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("id", String.valueOf(id))
                .claim("name", user.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + config.getJwtExpiration()))
                .signWith(secretKeykey)
                .compact();
        response.addHeader(config.getJwtHeader(), config.getJwtPrefix() + " " + token);
    }

}
