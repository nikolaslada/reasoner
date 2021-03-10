package cz.nikolaslada.reasoner.factories;

import cz.nikolaslada.reasoner.mappers.LinkMapper;
import cz.nikolaslada.reasoner.mappers.TranslationMapper;
import cz.nikolaslada.reasoner.repository.model.LinkModel;
import cz.nikolaslada.reasoner.repository.model.TranslationModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.LinkDomain;
import cz.nikolaslada.reasoner.rest.swagger.domains.TranslationDomain;
import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import cz.nikolaslada.reasoner.validators.IsoValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SharedFactory {

    private final IsoValidator validator;


    public SharedFactory(IsoValidator validator) {
        this.validator = validator;
    }

    public LinkModel createLinkModel(LinkDomain domain) throws BadRequestException {
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

    public List<TranslationDomain> createTranslationDomainList(List<TranslationModel> list) {
        List<TranslationDomain> domainList = new ArrayList<>();

        for (TranslationModel m : list) {
            domainList.add(
                    TranslationMapper.INSTANCE.modelToDomain(m)
            );
        }

        return domainList;
    }

    public List<TranslationModel> createTranslationModelList(List<TranslationDomain> list) throws BadRequestException {
        List<TranslationModel> modelList = new ArrayList<>();
        List<String> notValidIsoList = new ArrayList<>();

        for (TranslationDomain d : list) {
            if (!this.validator.isIsoLanguageValid(d.getIso())) {
                notValidIsoList.add(d.getIso());
            }

            modelList.add(
                    TranslationMapper.INSTANCE.domainToModel(d)
            );
        }

        if (!notValidIsoList.isEmpty()) {
            throw new BadRequestException(
                    IsoValidator.ERROR_MESSAGE_PATTERN,
                    Arrays.asList(
                            new ErrorItem(
                                    IsoValidator.ERROR_MESSAGE_PATTERN,
                                    notValidIsoList
                            )
                    )
            );
        }

        return modelList;
    }

}
