package com.gl.repository;

import com.gl.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepotory extends JpaRepository<Book,Integer> {
    List<Book> findAllByName(String name);
}
