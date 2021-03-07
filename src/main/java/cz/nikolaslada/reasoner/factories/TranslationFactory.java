package cz.nikolaslada.reasoner.factories;

import cz.nikolaslada.reasoner.mappers.TranslationMapper;
import cz.nikolaslada.reasoner.repository.model.TranslationModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.TranslationDomain;
import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import cz.nikolaslada.reasoner.validators.IsoValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Component
public class TranslationFactory {

    private final IsoValidator validator;


    public TranslationFactory(IsoValidator validator) {
        this.validator = validator;
    }

    public List<TranslationDomain> createDomainList(List<TranslationModel> list) {
        Iterator<TranslationModel> i = list.iterator();
        List<TranslationDomain> domainList = new ArrayList<>();

        while (i.hasNext()) {
            domainList.add(
                    TranslationMapper.INSTANCE.modelToDomain(i.next())
            );
        }

        return domainList;
    }

    public List<TranslationModel> createModelList(List<TranslationDomain> list) throws BadRequestException {
        Iterator<TranslationDomain> i = list.iterator();
        List<TranslationModel> modelList = new ArrayList<>();
        List<String> notValidIsoList = new ArrayList<>();

        while (i.hasNext()) {
            TranslationDomain d = i.next();
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
