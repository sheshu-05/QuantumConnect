package com.QC.QuantumConnect.validators;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    public static final long MAX_FILE_SIZE = 1024 * 1024 * 2; // 2MB
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            context.disableDefaultConstraintViolation();
            // overriding default message
            context.buildConstraintViolationWithTemplate("File cannot be empty").addConstraintViolation();
            return true;
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size must be less than or equal to 2MB").addConstraintViolation();
            return false;
        }
        return true;
    }                            

}
