package com.olabi.olabiflix.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.olabi.olabiflix.model.entity.Filme;
import com.olabi.olabiflix.repository.FilmeRepository;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/filmes")
public class FilmeController {
    private final FilmeRepository filmeRepository;

    public FilmeController(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    @GetMapping
    public List<Filme> getFilmes() {
        return filmeRepository.findAll();
    }

    @GetMapping("/buscar-title")
    public ResponseEntity<Filme> findByTitle(@RequestParam(name = "title", defaultValue = "") String title) {
        Optional<Filme> filmeEncontrado = filmeRepository.findByTitle(title);
        if (filmeEncontrado.isPresent()) {
            Filme filme = filmeEncontrado.get();
            return ResponseEntity.ok(filme);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/buscar-genre")
    public ResponseEntity<List<Filme>> findByGenre(@RequestParam(name = "genre", defaultValue = "") String genre) {
        List<Filme> filmes = filmeRepository.findByGenreContainsIgnoreCase(genre);
        return ResponseEntity.ok(filmes);

    }

    @GetMapping("/{id}")
    public Optional<Filme> getById(@PathVariable UUID id) {
        return filmeRepository.findById(id);
    }

    @PostMapping("/criar")
    public Filme createFilme(@RequestBody Filme filmeBody) {
        return filmeRepository.save(filmeBody);
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        filmeRepository.deleteById(id);
    }

}
