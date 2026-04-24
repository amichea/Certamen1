package com.example.certamen1.controller;

import com.example.certamen1.model.Libro;
import com.example.certamen1.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @GetMapping("/libros")
    public ResponseEntity<List<Libro>> obtenerLibros(@RequestParam(required = false) String search) {
        try {
            if (search != null && !search.isEmpty()) {
                return new ResponseEntity<>(
                        libroRepository.findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(search, search),
                        HttpStatus.OK
                );
            }
            return new ResponseEntity<>(libroRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/crearLibro")
    public ResponseEntity<Libro> crearLibro(@Valid @RequestBody Libro libro) {
        try {
            Libro nuevoLibro = libroRepository.save(libro);
            return new ResponseEntity<>(nuevoLibro, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/libros/{autor}")
    public ResponseEntity<List<Libro>> obtenerLibrosPorAutor(@PathVariable String autor) {
        try {
            List<Libro> libros = libroRepository.findByAutor(autor);
            if (libros.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(libros, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}