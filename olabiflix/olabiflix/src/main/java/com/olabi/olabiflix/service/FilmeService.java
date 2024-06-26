package com.olabi.olabiflix.service;

import com.olabi.olabiflix.exception.FilmeException;
import com.olabi.olabiflix.model.entity.Filme;
import com.olabi.olabiflix.repository.FilmeRepository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class FilmeService {

    private final FilmeRepository repository;

    public FilmeService(FilmeRepository repository) {
        this.repository = repository;
    }

    public List<Filme> getAll() {
        return repository.findAll();
    }

    public Filme create(Filme filmeBody) {

        boolean filmeExiste = repository.existsByTitleAndReleaseYearAndDirectorAndWriter(
                filmeBody.getTitle(), filmeBody.getReleaseYear(), filmeBody.getDirector(), filmeBody.getWriter());

        if (filmeExiste) {
            throw new FilmeException.DuplicateFilmeException();
        }

        return repository.save(filmeBody);
    }

    public Filme update(UUID id, Filme filmeBody) {

        Filme filme = repository.findById(id)
                .orElseThrow(() -> new FilmeException.FilmNotFoundException("Filme não encontrado com id: " + id));

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

        return repository.save(filme);
    }

    public Filme patch(UUID id, Map<String, String> requestBody) throws IllegalAccessException {
        Filme filme = repository.findById(id)
                .orElseThrow(() -> new FilmeException.FilmNotFoundException("Filme não encontrado com id: " + id));

        // lista dos campos da minha model
        List<Field> camposDaModel = List.of(filme.getClass().getDeclaredFields());

        for (Field campo : camposDaModel) {
            // tirando o privado
            campo.setAccessible(true);
            String nomeCampo = campo.getName();

            if (requestBody.containsKey(nomeCampo)) {
                // log.info(nomeCampo);
                String atualizacaoRequest = requestBody.get(nomeCampo);
                campo.set(filme, atualizacaoRequest);
            }
        }
        return repository.save(filme);
    }

}
