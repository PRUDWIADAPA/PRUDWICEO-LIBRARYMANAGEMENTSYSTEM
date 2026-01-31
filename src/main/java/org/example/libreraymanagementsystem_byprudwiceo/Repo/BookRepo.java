package org.example.libreraymanagementsystem_byprudwiceo.Repo;

import org.example.libreraymanagementsystem_byprudwiceo.Model.Author;
import org.example.libreraymanagementsystem_byprudwiceo.Model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Books,Integer> {

    Optional<Books> findByName(String name);


    @Query("select b from Books b where b.name=?1 and b.author.name=?2")
    Optional<Books> customQueryWithIndex(String name , String author);


    @Query("select b from Books b where b.name= :name and b.author.name=:author")
    Optional<Books> customQueryWithName(@Param("name") String name, @Param("author") String author);


    @Query(value = "select b.* from books b join author a on b.author_id=a.id where b.name=:name and a.name=:author",nativeQuery = true)
    Optional<Books> nativeCustomQueryWithParamNames(@Param("name") String name, @Param("author") String author);


    @Query(value = "select b.* from books b join author a on b.author_id=a.id where b.name=?1 and a.name=?2",nativeQuery = true)
    Optional<Books> nativeCustomQueryWithIndex(String name,String author );
}


