package com.QC.QuantumConnect.forms;

import org.springframework.web.multipart.MultipartFile;

import com.QC.QuantumConnect.validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactForm {
    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "Invalid Email Address")
    @NotBlank(message = "Email is required")
    private String email;
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
    private String phoneNumber;
    @NotBlank(message = "Address is required")
    private String address;
    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedInLink;
    // annotation to validate file size and resolution
    @ValidFile(message = "Invalid file")
    private MultipartFile contactImage;

    private String picture;
}
