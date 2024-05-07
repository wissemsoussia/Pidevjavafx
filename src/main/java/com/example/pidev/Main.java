package com.example.pidev;

import com.example.pidev.entities.Association;
import services.AssociationService;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Association association = new Association();
        association.setNom("Association 1");
        association.setDescription("Description 1");
        association.setAdresse("Adresse 1");
        association.setEmail("Email 1");
        association.setNumeroTelephone("NumeroTelephone 1");
        association.setDateCreation(LocalDate.now());
        AssociationService.getInstance().add(association);

        System.out.println(association);
        List<Association> associationList = AssociationService.getInstance().getAll();
        System.out.println(associationList);

        association.setNom("Association 2");
        association.setDescription("Description 2");

        AssociationService.getInstance().edit(association);

//        AssociationService.getInstance().delete(association.getId());
    }
}
