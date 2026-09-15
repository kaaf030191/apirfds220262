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
@Table(name = "toffice")
@Setter
@Getter
public class EntityOffice {
    @Id
    @Column(name = "idOffice")
    private String idOffice;

    @Column(name = "name")
    private String name;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    @OneToMany(mappedBy = "parentOffice", cascade = CascadeType.ALL)
    private List<EntitySuggestion> childSuggestion;

    @OneToMany(mappedBy = "parentOffice", cascade = CascadeType.ALL)
    private List<EntityComplaint> childComplaint;
}