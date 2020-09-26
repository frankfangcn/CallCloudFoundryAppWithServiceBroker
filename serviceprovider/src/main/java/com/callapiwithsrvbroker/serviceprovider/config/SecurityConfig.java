package com.callapiwithsrvbroker.serviceprovider.config;

import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.token.TokenAuthenticationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final XsuaaServiceConfiguration xsuaaServiceConfiguration;

    @Autowired
    public SecurityConfig(XsuaaServiceConfiguration xsuaaServiceConfiguration) {
        this.xsuaaServiceConfiguration = xsuaaServiceConfiguration;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(PUT, "/callback/v1.0/tenants*//**").hasAuthority("Callback")
            .antMatchers(GET, "/callback/v1.0/dependencies*//**").hasAuthority(("Callback"))
            .antMatchers(DELETE, "/callback/v1.0/tenants*//**").hasAuthority("Callback")
//            .antMatchers(GET, "/acctgdoc/printrequest/**").anonymous() // accepts unauthenticated user (w/o JWT)​
            .antMatchers(GET, "/getconsumertoken/**").hasAuthority("GetConsumerToken") // checks scope $XSAPPNAME.GetConsumerToken
//            .antMatchers(GET, "/getconsumertoken/**").permitAll() // checks scope $XSAPPNAME.GetConsumerToken
//            .antMatchers(GET, "/acctgdoc/readdoc/**").authenticated() // TODO: apply scope check at method level using @PreAuthorize​
            .anyRequest().denyAll() // denies anything not configured above
            .and()
            .oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(getJwtAuthoritiesConverter());
    }

    Converter<Jwt, AbstractAuthenticationToken> getJwtAuthoritiesConverter() {
        TokenAuthenticationConverter converter = new TokenAuthenticationConverter(xsuaaServiceConfiguration);
        converter.setLocalScopeAsAuthorities(true);
        return converter;
    }

}
