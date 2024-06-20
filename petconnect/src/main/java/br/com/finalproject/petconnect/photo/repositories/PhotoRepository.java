package br.com.finalproject.petconnect.photo.repositories;

import br.com.finalproject.petconnect.photo.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
