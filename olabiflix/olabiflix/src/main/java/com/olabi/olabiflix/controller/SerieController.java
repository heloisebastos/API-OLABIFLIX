package com.olabi.olabiflix.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olabi.olabiflix.model.entity.Serie;
import com.olabi.olabiflix.repository.SerieRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/criar")
    public Serie createSerie(@RequestBody Serie serieBody) {
        return serieRepository.save(serieBody);
    }

}
