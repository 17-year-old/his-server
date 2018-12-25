package com.fubailin.hisserver.Security;

import com.fubailin.hisserver.config.Config;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

//验证token的合法性
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    private Config config;

    public JWTAuthenticationFilter(Config config, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.config = config;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(config.getJwtHeader());

        if (header == null || !header.startsWith(config.getJwtPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getUsernamePasswordAuthenticationToken(header);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String token) {
        String loginname = Jwts.parser()
                .setSigningKey(config.getJwtSecretKey())
                .parseClaimsJws(token.replace(config.getJwtPrefix(), ""))
                .getBody()
                .getSubject();

        if (null != loginname) {
            return new UsernamePasswordAuthenticationToken(loginname, null, new ArrayList<>());
        }

        return null;
    }
}