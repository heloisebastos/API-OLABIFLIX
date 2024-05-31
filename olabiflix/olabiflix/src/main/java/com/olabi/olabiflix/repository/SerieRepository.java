package com.olabi.olabiflix.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olabi.olabiflix.model.entity.Serie;

public interface SerieRepository extends JpaRepository<Serie, UUID> {

}