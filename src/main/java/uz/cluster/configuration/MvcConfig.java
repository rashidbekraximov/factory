package uz.cluster.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.annotation.PostConstruct;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class MvcConfig implements WebMvcConfigurer {

    private static final String dateFormat = "yyyy-MM-dd";
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public AuditorAware<Integer> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(dateTimeFormat);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
//            builder.serializerByType(Nls.class,new ContactAppConfig.JsonNls());
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
            builder.failOnUnknownProperties(false);
//            builder.serializerByType(Status.class, new ContactAppConfig.JsonStatus());
        };
    }

    @Autowired
    private DispatcherServlet dispatcherServlet;

    @PostConstruct
    private void configureDispatcherServlet() {
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }


    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        SessionLocaleResolver lr = new SessionLocaleResolver();
        lr.setDefaultLocale(Locale.forLanguageTag("ru"));
        return lr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        //instantiate the object with an empty constructor
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();

        //the request param that we'll use to determine the locale
        interceptor.setParamName("lang");

        //get out
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
