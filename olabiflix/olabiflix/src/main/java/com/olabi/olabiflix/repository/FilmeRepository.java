package com.olabi.olabiflix.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olabi.olabiflix.model.entity.Filme;

public interface FilmeRepository extends JpaRepository<Filme, UUID> {

    Optional<Filme> findByTitle(String title);

    List<Filme> findByGenreContainsIgnoreCase(String genre);

    List<Filme> findByTitleOrActors(String title, String actors);

    boolean existsByTitleAndReleaseYearAndDirectorAndWriter(String title, String releaseYear, String director,
            String writer);
}
