package com.olabi.olabiflix.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.olabi.olabiflix.exception.FilmeException;
import com.olabi.olabiflix.model.entity.Filme;
import com.olabi.olabiflix.repository.FilmeRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/filmes")
public class FilmeController {
    private final FilmeRepository filmeRepository;

    private static Logger log = LoggerFactory.getLogger(FilmeController.class);

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
    public ResponseEntity<Object> createFilme(@RequestBody Filme filmeBody) {
        boolean filmeExiste = filmeRepository.existsByTitleAndReleaseYearAndDirectorAndWriter(
                filmeBody.getTitle(), filmeBody.getReleaseYear(), filmeBody.getDirector(), filmeBody.getWriter());

        if (filmeExiste) {
            String mensagemErro = new FilmeException.DuplicateFilmeException().getMessage();
            return new ResponseEntity<>(mensagemErro, HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok(filmeRepository.save(filmeBody));
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        filmeRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filme> put(@PathVariable UUID id,
            @RequestBody Filme filmeBody) {
        Optional<Filme> filmeEncontrado = filmeRepository.findById(id);

        if (filmeEncontrado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Filme filme = filmeEncontrado.get();

        log.info(String.valueOf(filme));

        filme.setTitle(filmeBody.getTitle());
        filme.setReleaseYear(filmeBody.getReleaseYear());
        filme.setRated(filmeBody.getRated());
        filme.setReleased(filmeBody.getReleased());
        filme.setRuntime(filmeBody.getRuntime());
        filme.setGenre(filmeBody.getGenre());
        filme.setDirector(filmeBody.getDirector());
        filme.setWriter(filmeBody.getWriter());
        filme.setActors(filmeBody.getActors());
        filme.setPlot(filmeBody.getPlot());
        filme.setLanguage(filmeBody.getLanguage());
        filme.setCountry(filmeBody.getCountry());
        filme.setAwards(filmeBody.getAwards());

        Filme filmeAtualizado = filmeRepository.save(filme);

        return ResponseEntity.ok(filmeAtualizado);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Filme> pacth(@PathVariable UUID id, @RequestBody Map<String, String> requestBody)
            throws IllegalAccessException {
        Optional<Filme> filmeEncontrado = filmeRepository.findById(id);

        if (filmeEncontrado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Filme filme = filmeEncontrado.get();

        List<Field> camposDaModel = List.of(filme.getClass().getDeclaredFields());

        for (Field campo : camposDaModel) {
            campo.setAccessible(true);
            String nomeCampo = campo.getName();

            if (requestBody.containsKey(nomeCampo)) {
                String atualizacaoRequest = requestBody.get(nomeCampo);
                campo.set(filme, atualizacaoRequest);

            }

        }

        filmeRepository.save(filme);

        return ResponseEntity.ok(filme);

    }

}
