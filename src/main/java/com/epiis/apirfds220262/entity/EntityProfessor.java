package com.epiis.apirfds220262.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tprofessor")
@Setter
@Getter
public class EntityProfessor {
    @Id
    @Column(name = "idProfessor")
    private String idProfessor;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "surName")
    private String surName;

    @Column(name = "email")
    private String email;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    @OneToMany(mappedBy = "parentProfessor", cascade = CascadeType.ALL)
    private List<EntityComplaint> childComplaint;
}