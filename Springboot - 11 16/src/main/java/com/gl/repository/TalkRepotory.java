package com.gl.repository;

import com.gl.entity.Talk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TalkRepotory extends JpaRepository<Talk,Integer> {
    List<Talk> findAllByRname(Integer name);
}
