package cz.nikolaslada.reasoner.factories;

import cz.nikolaslada.reasoner.mappers.LinkMapper;
import cz.nikolaslada.reasoner.repository.model.LinkModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.LinkDomain;
import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import cz.nikolaslada.reasoner.validators.IsoValidator;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class LinkFactory {

    private final IsoValidator validator;


    public LinkFactory(IsoValidator validator) {
        this.validator = validator;
    }

    public LinkModel createModel(LinkDomain domain) throws BadRequestException {
        if (!this.validator.isIsoLanguageValid(domain.getIso())) {
            throw new BadRequestException(
                    IsoValidator.ERROR_MESSAGE_PATTERN,
                    Arrays.asList(
                            new ErrorItem(
                                    IsoValidator.ERROR_MESSAGE_PATTERN,
                                    Arrays.asList(
                                            domain.getIso()
                                    )
                            )
                    )
            );
        }

        return LinkMapper.INSTANCE.domainToModel(domain);
    }

}
