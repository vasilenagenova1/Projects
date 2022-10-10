package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.validation.Validator;

@Component
public class ValidationImpl implements Validation {
    private final Validator validator;

    public ValidationImpl() {
        validator = javax.validation.Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    @Override
    public <E> boolean isValid(E entity) {
        return validator.validate(entity).isEmpty();
    }
}
