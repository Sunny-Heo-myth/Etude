package com.chatboard.etude.dto.comment;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;
import java.util.stream.Collectors;

import static com.chatboard.etude.factory.dto.CommentReadConditionFactory.createCommentReadCondition;
import static org.assertj.core.api.Assertions.assertThat;

public class CommentReadConditionDtoTest {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validateTest() {
        // given
        CommentReadConditionDto condition = createCommentReadCondition();

        // when
        Set<ConstraintViolation<CommentReadConditionDto>> violations = validator.validate(condition);

        // then
        assertThat(violations).isEmpty();
    }

    @Test
    void invalidateByNegativePostIdTest() {
        // given
        Long invalidValue = -1L;
        CommentReadConditionDto condition = createCommentReadCondition(invalidValue);

        // when
        Set<ConstraintViolation<CommentReadConditionDto>> violations = validator.validate(condition);

        // then
        assertThat(violations).isNotEmpty();
        assertThat(violations.stream()
                .map(ConstraintViolation::getInvalidValue)
                .collect(Collectors.toSet()))
                .contains(invalidValue);
    }
}
