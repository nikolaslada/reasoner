package cz.nikolaslada.reasoner.validators;

import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Component
public class IsoValidator {

    private static final Set<String> ISO_LANGUAGES = new HashSet<String>(
            Arrays.asList(
                    Locale.getISOLanguages()
            )
    );

    public boolean isIsoLanguageValid(String iso) {
        return ISO_LANGUAGES.contains(iso);
    }

}
