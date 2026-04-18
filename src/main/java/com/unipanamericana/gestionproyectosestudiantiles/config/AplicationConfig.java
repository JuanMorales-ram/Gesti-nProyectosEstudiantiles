/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unipanamericana.gestionproyectosestudiantiles.config;

import org.glassfish.jersey.server.ResourceConfig;

public class AplicationConfig extends ResourceConfig {

    public AplicationConfig() {
        packages("com.unipanamericana.gestionproyectosestudiantiles");

       
        register(ObjectMapperProvider.class);
    }
}
