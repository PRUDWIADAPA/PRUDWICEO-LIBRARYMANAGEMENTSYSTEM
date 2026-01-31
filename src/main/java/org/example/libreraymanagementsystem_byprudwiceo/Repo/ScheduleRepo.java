package org.example.libreraymanagementsystem_byprudwiceo.Repo;

import org.example.libreraymanagementsystem_byprudwiceo.Model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule,Integer> {
}
