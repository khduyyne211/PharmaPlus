package com.pharma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pharma.backend.entity.QuyDoiDonVi;

public interface QuyDoiDonViRepository extends JpaRepository<QuyDoiDonVi, Long> {

    @Query("""
            SELECT qd
            FROM QuyDoiDonVi qd
            JOIN FETCH qd.sanPham sp
            JOIN FETCH qd.donViNguon dvNguon
            JOIN FETCH dvNguon.donViTinh dvtNguon
            JOIN FETCH qd.donViDich dvDich
            JOIN FETCH dvDich.donViTinh dvtDich
            WHERE sp.maSanPham IN :danhSachMaSanPham
              AND qd.trangThai = true
            """)
    List<QuyDoiDonVi> findBySanPham_MaSanPhamInAndTrangThaiTrue(
            @Param("danhSachMaSanPham") List<Long> danhSachMaSanPham
    );

    @Query("""
            SELECT qd
            FROM QuyDoiDonVi qd
            JOIN FETCH qd.sanPham sp
            JOIN FETCH qd.donViNguon dvNguon
            JOIN FETCH dvNguon.donViTinh dvtNguon
            JOIN FETCH qd.donViDich dvDich
            JOIN FETCH dvDich.donViTinh dvtDich
            WHERE sp.maSanPham = :maSanPham
              AND qd.trangThai = true
            """)
    List<QuyDoiDonVi> findBySanPham_MaSanPhamAndTrangThaiTrue(
            @Param("maSanPham") Long maSanPham
    );
}