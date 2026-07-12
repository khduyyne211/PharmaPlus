package com.pharma.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharma.backend.entity.DuLieuChuyenMonThuoc;

public interface DuLieuChuyenMonThuocRepository extends JpaRepository<DuLieuChuyenMonThuoc, Long> {

    Optional<DuLieuChuyenMonThuoc> findBySanPham_MaSanPham(Long maSanPham);
}