package com.gl.repository;

import com.gl.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepotory extends JpaRepository<Room,Integer> {
    List<Room> findAllByRname(Integer rname);
    List<Room> findAllByRnameAndAndHname(Integer rname,String hname);
}
