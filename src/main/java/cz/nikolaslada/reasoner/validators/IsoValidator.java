package cz.nikolaslada.reasoner.validators;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class IsoValidator {

    public static final String ERROR_MESSAGE_PATTERN = "Not supported Language ISO codes: ";

    private static final Set<String> ISO_LANGUAGES = new HashSet<String>(
            Arrays.asList(
                    Locale.getISOLanguages()
            )
    );

    public boolean isIsoLanguageValid(String iso) {
        return ISO_LANGUAGES.contains(iso);
    }

}
