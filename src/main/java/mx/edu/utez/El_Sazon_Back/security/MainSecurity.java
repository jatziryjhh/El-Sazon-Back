    package mx.edu.utez.El_Sazon_Back.security;

    import mx.edu.utez.El_Sazon_Back.security.jwt.JwtAuthenticationFilter;
    import mx.edu.utez.El_Sazon_Back.security.service.UsuarioDetailsServiceImpl;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.AuthenticationProvider;
    import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
    import org.springframework.security.config.Customizer;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity

    public class MainSecurity {

        private final String[] WHITE_LIST ={
                "/api/auth/**",
                "/api/producto/**",
                "/api/usuario/registro/**",
                "/api/pedido/**",
                "/api/producto/visualizar/**",
                "/api/categoria/**",
                "/api/rol/**"
        };

        private final UsuarioDetailsServiceImpl service;

        public MainSecurity(UsuarioDetailsServiceImpl service) {
            this.service = service;
        }

        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationProvider authenticationProvider(){
            DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
            dao.setUserDetailsService(service);
            dao.setPasswordEncoder(passwordEncoder());
            return dao;
        }
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
            return configuration.getAuthenticationManager();
        }

        @Bean
        public JwtAuthenticationFilter filter(){
            return new JwtAuthenticationFilter();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
            http.cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(req ->
                            req.requestMatchers(WHITE_LIST).permitAll()  // Asegúrate que estas rutas estén correctamente configuradas
                                    .requestMatchers("/api/usuario/**").hasAnyAuthority("Gerente", "Cliente")
                                    .requestMatchers("/api/pedido/**").hasAnyAuthority("Cliente", "Gerente", "Empleado")
                                    .requestMatchers("/api/venta/**").hasAnyAuthority("Gerente", "Empleado")
                                    .anyRequest().authenticated()
                    )
                    .httpBasic(Customizer.withDefaults())
                    .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authenticationProvider(authenticationProvider())
                    .addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class)
                    .logout(out -> out.logoutUrl("/api/auth/logout").clearAuthentication(true));

            return http.build();
        }
    }
