package com.example.careerboostspringai.repository;

import com.example.careerboostspringai.entity.ChatData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatDataRepository extends JpaRepository<ChatData, Long> {
}