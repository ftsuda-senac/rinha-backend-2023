package ftsuda.rinhabackend.model;

import java.util.Collection;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PessoaStackValidator implements ConstraintValidator<PessoaStack, Collection<String>> {

    private int itemMaxChars;

    @Override
    public void initialize(PessoaStack parameters) {
        itemMaxChars = parameters.maxChars();
    }

    @Override
    public boolean isValid(Collection<String> values, ConstraintValidatorContext context) {
        if (values == null || values.size() < 1) {
            return true;
        }
        for (String val : values) {
            if (StringUtils.isBlank(val) || val.length() > itemMaxChars) {
                return false;
            }
        }
        return true;
    }

}
