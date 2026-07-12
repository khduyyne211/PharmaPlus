package com.pharma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pharma.backend.entity.DonViSanPham;

public interface DonViSanPhamRepository extends JpaRepository<DonViSanPham, Long> {

    @EntityGraph(attributePaths = {
            "sanPham",
            "donViTinh"
    })
    List<DonViSanPham> findBySanPham_MaSanPhamAndChoPhepBanTrueAndTrangThaiTrue(
            Long maSanPham
    );

    @EntityGraph(attributePaths = {
            "sanPham",
            "donViTinh"
    })
    List<DonViSanPham> findBySanPham_MaSanPhamInAndChoPhepBanTrueAndTrangThaiTrue(
            List<Long> danhSachMaSanPham
    );
}