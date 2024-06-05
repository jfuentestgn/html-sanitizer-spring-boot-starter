package net.jfuentestgn.htmlsanitizer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for the HTML Sanitizer Spring Boot Starter
 */
@Configuration
@Slf4j
public class HtmlSanitizerConfiguration implements WebMvcConfigurer {

    /**
     * Default constructor
     */
    public HtmlSanitizerConfiguration() {
    }

    /**
     * Creates the PoliciesRegistry bean
     *
     * <p>The {@link PoliciesRegistry} bean is responsible of keeping policies than can be used to sanitize HTML user input</p>
     *
     * @return the policies registry
     */
    @Bean
    public PoliciesRegistry policiesRegistry() {
        log.info("[m:policiesRegistry] Instantiating bean 'PoliciesRegistry'");
        PoliciesRegistry policiesRegistry = new PoliciesRegistry();
        policiesRegistry.addPredefinedPolicies();
        return policiesRegistry;
    }

    /**
     * Creates the {@link Sanitizer} bean.
     *
     * <p>The Sanitizer bean lets you sanitize any HTML user input</p>
     * @return the sanitizer
     */
    @Bean
    public Sanitizer sanitizer() {
        log.info("[m:sanitizer] Instantiating bean 'Sanitizer'");
        return new Sanitizer(policiesRegistry());
    }

    /**
     * Creates the Formatter Factory bean for the '{@link Sanitize}' formatting annotation
     *
     * @return the sanitizer formatter factory
     */
    @Bean
    SanitizerFormatterFactory sanitizerFormatterFactory() {
        log.info("[m:sanitizerFormatterFactory] Instantiating bean 'SanitizerFormatterFactory'");
        return new SanitizerFormatterFactory(sanitizer());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        log.info("[m:addFormatters] Adding 'SanitizerFormatterFactory' formatter to FormatterRegistry");
        registry.addFormatterForFieldAnnotation(sanitizerFormatterFactory());
    }


}
