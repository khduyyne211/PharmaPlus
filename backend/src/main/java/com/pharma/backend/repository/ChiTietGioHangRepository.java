package com.pharma.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharma.backend.entity.ChiTietGioHang;

public interface ChiTietGioHangRepository extends JpaRepository<ChiTietGioHang, Long> {

    List<ChiTietGioHang> findByGioHang_MaGioHang(Long maGioHang);

    Optional<ChiTietGioHang> findByGioHang_MaGioHangAndSanPham_MaSanPhamAndDonViSanPham_MaDonViSanPham(
            Long maGioHang,
            Long maSanPham,
            Long maDonViSanPham
    );

    Optional<ChiTietGioHang> findByMaChiTietGioHangAndGioHang_MaGioHang(
            Long maChiTietGioHang,
            Long maGioHang
    );

    void deleteByGioHang_MaGioHang(Long maGioHang);
}