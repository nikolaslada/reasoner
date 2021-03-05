package cz.nikolaslada.reasoner.validators;

import cz.nikolaslada.reasoner.rest.swagger.domains.Translation;
import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class IsoValidator {

    private static final String ERROR_MESSAGE_PATTERN = "Not supported Language ISO codes: ";

    private static final Set<String> ISO_LANGUAGES = new HashSet<String>(
            Arrays.asList(
                    Locale.getISOLanguages()
            )
    );

    public boolean isIsoLanguageValid(String iso) {
        return ISO_LANGUAGES.contains(iso);
    }

    public void checkIsoList(List<Translation> translationList) throws BadRequestException {
        Iterator<Translation> i = translationList.iterator();
        List<String> notValidIsoList = new ArrayList<>();

        while (i.hasNext()) {
            String iso = i.next().getIso();
            if (!this.isIsoLanguageValid(iso)) {
                notValidIsoList.add(iso);
            }
        }

        if (!notValidIsoList.isEmpty()) {
            throw new BadRequestException(
                    ERROR_MESSAGE_PATTERN,
                    Arrays.asList(
                            new ErrorItem(
                                    ERROR_MESSAGE_PATTERN,
                                    notValidIsoList
                            )
                    )
            );
        }
    }

}
