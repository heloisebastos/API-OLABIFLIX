package com.olabi.olabiflix.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.olabi.olabiflix.exception.FilmeException;
import com.olabi.olabiflix.model.entity.Filme;
import com.olabi.olabiflix.model.entity.Serie;
import com.olabi.olabiflix.model.value.Ratings;
import com.olabi.olabiflix.repository.SerieRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/series")
public class SerieController {

    private final SerieRepository serieRepository;

    public SerieController(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    @GetMapping
    public List<Serie> getSeries() {
        return serieRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Serie> getById(@PathVariable UUID id) {
        return serieRepository.findById(id);
    }

    @GetMapping("/buscar-title")
    public ResponseEntity<Serie> findByTitle(@RequestParam(name = "title", defaultValue = "") String title) {
        Optional<Serie> serieEncontrada = serieRepository.findByTitle(title);

        if (serieEncontrada.isPresent()) {
            Serie serie = serieEncontrada.get();
            return ResponseEntity.ok(serie);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/buscar-genre")
    public ResponseEntity<List<Serie>> findByGenre(@RequestParam(name = "genre", defaultValue = "") String genre) {
        List<Serie> series = serieRepository.findByGenreContainsIgnoreCase(genre);
        return ResponseEntity.ok(series);

    }

    @PostMapping("/criar")
    public Serie createSerie(@RequestBody Serie serieBody) {
        return serieRepository.save(serieBody);
    }

    @DeleteMapping("{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delte(@PathVariable UUID id) {
        serieRepository.deleteById(id);
    }

    @PatchMapping("/{id}/like")
    public ResponseEntity<Serie> like(@PathVariable UUID id) {
        Optional<Serie> serieEncontrada = serieRepository.findById(id);

        if (serieEncontrada.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Serie serie = serieEncontrada.get();
        Ratings avaliacao = serie.getRatings();

        Integer likesAtuais = Integer.parseInt(avaliacao.getLinkes());
        Integer like = likesAtuais + 1;
        avaliacao.setLinkes(String.valueOf(like));

        return ResponseEntity.ok(serieRepository.save(serie));

    }
}
