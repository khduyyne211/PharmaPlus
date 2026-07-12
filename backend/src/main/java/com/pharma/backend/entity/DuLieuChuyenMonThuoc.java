package com.pharma.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "du_lieu_chuyen_mon_thuoc")
public class DuLieuChuyenMonThuoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_du_lieu_chuyen_mon")
    private Long maDuLieuChuyenMon;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_san_pham", nullable = false)
    private SanPham sanPham;

    @Column(name = "dang_bao_che", length = 100)
    private String dangBaoChe;

    @Column(name = "cong_dung_tham_khao", columnDefinition = "TEXT")
    private String congDungThamKhao;

    @Column(name = "cach_dung_tham_khao", columnDefinition = "TEXT")
    private String cachDungThamKhao;

    @Column(name = "canh_bao_an_toan", columnDefinition = "TEXT")
    private String canhBaoAnToan;

    @Column(name = "phan_loai_thuoc", length = 100)
    private String phanLoaiThuoc;

    @Column(name = "trang_thai_xac_nhan", nullable = false)
    private Boolean trangThaiXacNhan;
}