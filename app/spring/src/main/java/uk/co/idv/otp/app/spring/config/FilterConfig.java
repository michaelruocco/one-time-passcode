package uk.co.idv.otp.app.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import uk.co.idv.otp.app.spring.filters.validation.DefaultHeaderValidationFilter;
import uk.co.mruoc.spring.filter.logging.mdc.ClearMdcFilter;
import uk.co.mruoc.spring.filter.logging.mdc.HeaderMdcPopulatorFilter;
import uk.co.mruoc.spring.filter.logging.mdc.RequestMdcPopulatorFilter;
import uk.co.mruoc.spring.filter.logging.request.RequestLoggingFilter;
import uk.co.mruoc.spring.filter.logging.response.ResponseLoggingFilter;

import java.time.Clock;

@Configuration
@Slf4j
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<HeaderMdcPopulatorFilter> headerMdcPopulator() {
        FilterRegistrationBean<HeaderMdcPopulatorFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new HeaderMdcPopulatorFilter("correlation-id", "channel-id"));
        bean.setOrder(1);
        bean.addUrlPatterns(getDefaultUrlPatterns());
        bean.setName("headerMdcPopulator");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<RequestMdcPopulatorFilter> requestMdcPopulator(Clock clock) {
        FilterRegistrationBean<RequestMdcPopulatorFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new RequestMdcPopulatorFilter(clock));
        bean.setOrder(2);
        bean.addUrlPatterns(getDefaultUrlPatterns());
        bean.setName("requestMdcPopulator");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<DefaultHeaderValidationFilter> defaultHeaderValidationFilter(HandlerExceptionResolver handlerExceptionResolver) {
        FilterRegistrationBean<DefaultHeaderValidationFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new DefaultHeaderValidationFilter(handlerExceptionResolver));
        bean.setOrder(4);
        bean.addUrlPatterns(getDefaultUrlPatterns());
        bean.setName("defaultHeaderValidationFilter");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> defaultRequestLoggingFilter() {
        FilterRegistrationBean<RequestLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new RequestLoggingFilter());
        bean.setOrder(3);
        bean.addUrlPatterns(getDefaultUrlPatterns());
        bean.setName("defaultRequestLoggingFilter");
        bean.setEnabled(requestLoggingEnabled());
        return bean;
    }

    @Bean
    public FilterRegistrationBean<ResponseLoggingFilter> defaultResponseLoggingFilter() {
        FilterRegistrationBean<ResponseLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new ResponseLoggingFilter());
        bean.setOrder(5);
        bean.addUrlPatterns(getDefaultUrlPatterns());
        bean.setName("defaultResponseLoggingFilter");
        bean.setEnabled(responseLoggingEnabled());
        return bean;
    }

    @Bean
    public FilterRegistrationBean<ClearMdcFilter> clearMdcFilter() {
        FilterRegistrationBean<ClearMdcFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new ClearMdcFilter());
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        bean.addUrlPatterns(getDefaultUrlPatterns());
        bean.setName("clearMdcFilter");
        return bean;
    }

    private static String[] getDefaultUrlPatterns() {
        return new String[]{
                "/v1/otp-verifications",
                "/v1/otp-verifications/*"
        };
    }

    private static boolean requestLoggingEnabled() {
        return loadBooleanSystemProperty("request.logging.enabled");
    }

    private static boolean responseLoggingEnabled() {
        return loadBooleanSystemProperty("response.logging.enabled");
    }

    private static boolean loadBooleanSystemProperty(String key) {
        boolean enabled = Boolean.parseBoolean(System.getProperty(key, "false"));
        log.info("loaded {} value {}", key, enabled);
        return enabled;
    }

}
