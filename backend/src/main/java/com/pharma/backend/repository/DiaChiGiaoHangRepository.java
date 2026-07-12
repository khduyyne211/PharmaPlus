package com.pharma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.backend.entity.DiaChiGiaoHang;

@Repository
public interface DiaChiGiaoHangRepository extends JpaRepository<DiaChiGiaoHang, Long> {

    List<DiaChiGiaoHang>
        findByKhachHang_MaKhachHangAndTrangThaiSuDungTrueOrderByLaMacDinhDescMaDiaChiDesc(
            Long maKhachHang
        );
}