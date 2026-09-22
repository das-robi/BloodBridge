package com.robindas.bloodbridge.Config;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

/** Removes HTML from all request DTO text fields before Bean Validation and service use. */
@ControllerAdvice(annotations = Controller.class)
public class DtoSanitizationAdvice extends RequestBodyAdviceAdapter {

    private static final String DTO_PACKAGE = "com.robindas.bloodbridge.DTO";
    private static final PolicyFactory NO_HTML = new HtmlPolicyBuilder().toFactory();
    private static final Pattern HTML_TAG = Pattern.compile("(?is)<[^>]*>");

    @Override
    public boolean supports(MethodParameter methodParameter,
                            java.lang.reflect.Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                java.lang.reflect.Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {

        if (body != null && body.getClass().getPackageName().startsWith(DTO_PACKAGE)) {
            sanitize(body);
        }
        return body;

    }

    private void sanitize(Object dto) {

        for (Class<?> type = dto.getClass(); type != null; type = type.getSuperclass()) {

            for (Field field : type.getDeclaredFields()) {
                // Passwords are credentials, never rendered; changing them would break authentication.
                if (field.getType() != String.class || field.getName().toLowerCase().contains("password")) continue;

                try {
                    field.setAccessible(true);
                    String value = (String) field.get(dto);
                    if (value != null) field.set(dto, sanitizeText(value));
                }
                catch (IllegalAccessException e) {
                    throw new IllegalStateException("Unable to sanitize request", e);
                }

            }
        }
    }

    private String sanitizeText(String value) {
        // The OWASP library emits safe HTML, which encodes '@' as '&#64;'. DTOs hold
        // plain text, so decode entities only after markup has been removed, then remove
        // any markup that may have arrived entity-encoded (for example &lt;script&gt;).
        String withoutMarkup = NO_HTML.sanitize(value);
        String decodedText = HtmlUtils.htmlUnescape(withoutMarkup);
        return HTML_TAG.matcher(decodedText).replaceAll("").trim();
    }
}
