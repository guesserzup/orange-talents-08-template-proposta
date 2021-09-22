package br.com.zupacademy.propostas.seguranca;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable().csrf().disable().authorizeRequests().antMatchers("/**").permitAll();

//        http.cors()
//                .disable()
//                .csrf()
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/actuator/health", "/proposta/**")
//                .hasAuthority("SCOPE_read")
//                .antMatchers(HttpMethod.POST, "/propostas", "/cartao/{numCartao}/biometria", "/cartao/{numCartao}/bloqueio")
//                .hasAuthority("SCOPE_write")
//                .anyRequest()
//                .authenticated()
//                .and()
//                .oauth2ResourceServer()
//                .jwt();
    }
}
