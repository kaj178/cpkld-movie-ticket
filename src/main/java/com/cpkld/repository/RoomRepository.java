package com.cpkld.repository;

import com.cpkld.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Room getRoomByRoomId(Integer roomId);
}
