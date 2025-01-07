package com.example.server.component;

import com.example.server.annotation.NoExtraFields;
import com.example.server.entity.Note;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Iterator;

public class NoExtraFieldsValidator implements ConstraintValidator<NoExtraFields, Note> {
    private static final String[] ALLOWED_FIELDS = { "title", "date", "time", "id" };

    @Override
    public boolean isValid(Note note, ConstraintValidatorContext context) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.valueToTree(note);

            Iterator<String> fieldNames = jsonNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                boolean isAllowed = false;
                for (String allowedField : ALLOWED_FIELDS) {
                    if (allowedField.equals(fieldName)) {
                        isAllowed = true;
                        break;
                    }
                }
                if (!isAllowed) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("Unexpected field: " + fieldName)
                            .addConstraintViolation();
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
