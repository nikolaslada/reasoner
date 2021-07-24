package cz.nikolaslada.reasoner.services;

import cz.nikolaslada.reasoner.rest.swagger.error.BadRequestBuilder;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class ValidatorService {

    private Validator validator;


    public Validator getValidator() {
        if (this.validator == null) {
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            this.validator = validatorFactory.getValidator();
        }

        return this.validator;
    }

    public void validate(Object o) throws BadRequestException {
        Set<ConstraintViolation<Object>> violations = this.getValidator().validate(o);

        if (!violations.isEmpty()) {
            BadRequestBuilder builder = new BadRequestBuilder();

            for (ConstraintViolation<Object> v : violations) {
                builder.addErrorItem(v.getMessage(), v.getInvalidValue().toString());
            }

            throw builder.build();
        }
    }

}
