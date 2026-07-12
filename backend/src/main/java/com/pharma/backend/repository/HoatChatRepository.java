package com.pharma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharma.backend.entity.HoatChat;

public interface HoatChatRepository extends JpaRepository<HoatChat, Long> {
}