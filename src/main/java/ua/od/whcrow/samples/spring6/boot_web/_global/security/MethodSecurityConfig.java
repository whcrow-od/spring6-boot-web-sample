package ua.od.whcrow.samples.spring6.boot_web._global.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
// Enables handling of @Secured annotation
//@EnableMethodSecurity(securedEnabled = true, prePostEnabled = false)
class MethodSecurityConfig {
}
