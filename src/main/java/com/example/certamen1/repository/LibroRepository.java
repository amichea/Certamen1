package com.example.certamen1.repository;

import com.example.certamen1.model.Libro;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface LibroRepository extends MongoRepository<Libro, String> {

    List<Libro> findByAutor(String autor);
    List<Libro> findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(String titulo, String autor);
}
