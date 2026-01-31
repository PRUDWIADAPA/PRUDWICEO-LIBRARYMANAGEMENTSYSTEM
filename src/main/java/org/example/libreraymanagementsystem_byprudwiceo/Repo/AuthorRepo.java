package org.example.libreraymanagementsystem_byprudwiceo.Repo;

import org.example.libreraymanagementsystem_byprudwiceo.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepo extends JpaRepository<Author,Integer> {

}
