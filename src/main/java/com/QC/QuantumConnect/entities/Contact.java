package com.QC.QuantumConnect.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    private String id; 
    @Column(name="name", nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String phoneNumber;
    private String address;
    @Column(length = 2000)
    private String picture;
    @Column(length = 1000)
    private String description;
    private boolean favorite = false;
    private String webSiteLink;
    private String linkedInLink;
    
    private String cloudinaryImagePublicId;

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SocialLink> links = new ArrayList<>();
}
