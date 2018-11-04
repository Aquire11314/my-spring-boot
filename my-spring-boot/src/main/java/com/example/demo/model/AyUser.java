package com.example.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name="ay_user")
public class AyUser implements Serializable {
    @Id
    private String id;
    private String name;
    private String password;
}
