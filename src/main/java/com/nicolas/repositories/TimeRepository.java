package com.nicolas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicolas.entities.Time;

public interface TimeRepository extends JpaRepository<Time, Integer> {

}
