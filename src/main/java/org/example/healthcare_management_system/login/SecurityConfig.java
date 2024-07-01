package org.example.healthcare_management_system.login;
import org.example.healthcare_management_system.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private CorsConfig corsConfig;
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf((csrf) -> csrf.disable())
//                .cors(Customizer.withDefaults())
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))

                .authorizeRequests((authorizeRequests)->
                        authorizeRequests
                                .requestMatchers("/register", "/login" , "/signup" , "/logout" , "/**").permitAll()
//                                .anyRequest().authenticated()
                )

                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/", true)
                                .permitAll()
                )
                .logout(logout-> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint))
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                .addFilterBefore(new AuthenticationConfig(customUserDetailsService , customAuthenticationProvider , jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        http.userDetailsService(customUserDetailsService);

        return http.build();
    }


//
//    @Bean
//    public AuthenticationManager customAuthenticationManager(HttpSecurity http) throws Exception {
//        try {
//            System.out.println();
//            System.out.println("in customAuthenticationManager");
//            AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//            authManagerBuilder.authenticationProvider(customAuthenticationProvider);
//            System.out.println(customAuthenticationProvider);
//            System.out.println("in customAuthenticationManager");
//            return authManagerBuilder.build();
//        }catch ( Exception e ) {
//            System.out.println();
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(customAuthenticationProvider);
//    }

//    @Bean
//    public AuthenticationConfig customAuthenticationFilter() throws Exception {
//        AuthenticationConfig filter = new AuthenticationConfig();
//        filter.setAuthenticationManager(authenticationManager());
//        filter.setFilterProcessesUrl("/login");
//        return filter;
//    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        System.out.println();
//        System.out.println("in configure global");
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
//            auth.authenticationProvider(customAuthenticationProvider);
//            System.out.println();
//            System.out.println(authentication.getName());
//            System.out.println(auth);
//        }
//        catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
}

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }


//                .csrf((csrf) -> csrf
//                        .ignoringRequestMatchers("/signup")
//                )

//    private final CustomUserDetailsService customUserDetailsService;
//
//    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
//        this.customUserDetailsService = customUserDetailsService;
//    }
//

//                .csrf()
//                .disable()
//                .cors()
//                .and()

//    @Autowired

//@Bean
//public AuthenticationManager authenticationManager() throws Exception {
//    System.out.println();
//    System.out.println("in AuthenticationManager");
//    return new ProviderManager(customAuthenticationProvider);
//}
//
//@Bean
//public AuthenticationManager authManager(
//        HttpSecurity http,
//        CustomAuthenticationProvider authProvider) throws Exception {
//    System.out.println();
//    System.out.println("in AuthManager");
//    AuthenticationManagerBuilder authenticationManagerBuilder = http
//            .getSharedObject(AuthenticationManagerBuilder.class);
//    authenticationManagerBuilder.authenticationProvider(authProvider);
//    return authenticationManagerBuilder.build();
//}