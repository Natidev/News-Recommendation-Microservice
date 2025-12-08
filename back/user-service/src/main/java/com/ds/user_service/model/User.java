package com.ds.user_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
@Id
    String username;
String password;
String role;
}
