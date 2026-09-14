package com.epiis.apirfds220262.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tsuggestionfile")
@Setter
@Getter
public class EntitySuggestionFile {
    @Id
    @Column(name = "idSuggestionfile")
    private String idSuggestionfile;

    @Column(name = "idSuggestion")
    private String idSuggestion;

    @Column(name = "name")
    private String name;

    @Column(name = "extension")
    private String extension;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSuggestion", insertable = false, updatable = false)
    private EntitySuggestion parentSuggestion;
}