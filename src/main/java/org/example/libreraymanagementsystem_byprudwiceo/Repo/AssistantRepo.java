package org.example.libreraymanagementsystem_byprudwiceo.Repo;

import org.example.libreraymanagementsystem_byprudwiceo.Model.Assistant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssistantRepo extends JpaRepository<Assistant,Integer> {


}
