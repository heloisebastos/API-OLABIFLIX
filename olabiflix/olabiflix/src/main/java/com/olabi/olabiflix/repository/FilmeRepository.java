package com.olabi.olabiflix.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olabi.olabiflix.model.entity.Filme;

public interface FilmeRepository extends JpaRepository<Filme, UUID> {

    // defini os metodos que vão gerar as queries

}
