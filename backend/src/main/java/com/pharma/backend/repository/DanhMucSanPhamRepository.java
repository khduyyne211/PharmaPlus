package com.pharma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pharma.backend.entity.DanhMucSanPham;

public interface DanhMucSanPhamRepository extends JpaRepository<DanhMucSanPham, Long> {

    @EntityGraph(attributePaths = "danhMucCha")
    List<DanhMucSanPham> findByTrangThaiHienThiTrueOrderByThuTuHienThiAsc();
    
}