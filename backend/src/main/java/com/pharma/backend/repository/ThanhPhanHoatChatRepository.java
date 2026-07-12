package com.pharma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharma.backend.entity.ThanhPhanHoatChat;

public interface ThanhPhanHoatChatRepository extends JpaRepository<ThanhPhanHoatChat, Long> {

    List<ThanhPhanHoatChat> findBySanPham_MaSanPham(Long maSanPham);
}