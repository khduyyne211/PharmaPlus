package com.pharma.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dia_chi_giao_hang")
public class DiaChiGiaoHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_dia_chi")
    private Long maDiaChi;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "ma_khach_hang",
        nullable = false
    )
    private KhachHang khachHang;

    @Column(
        name = "ten_nguoi_nhan",
        nullable = false,
        length = 100
    )
    private String tenNguoiNhan;

    @Column(
        name = "so_dien_thoai_nhan",
        nullable = false,
        length = 20
    )
    private String soDienThoaiNhan;

    @Column(
        name = "tinh_thanh",
        nullable = false,
        length = 100
    )
    private String tinhThanh;

    @Column(
        name = "quan_huyen",
        nullable = false,
        length = 100
    )
    private String quanHuyen;

    @Column(
        name = "phuong_xa",
        nullable = false,
        length = 100
    )
    private String phuongXa;

    @Column(
        name = "dia_chi_chi_tiet",
        nullable = false,
        length = 255
    )
    private String diaChiChiTiet;

    @Column(
        name = "la_mac_dinh",
        nullable = false
    )
    private Boolean laMacDinh;

    @Column(
        name = "trang_thai_su_dung",
        nullable = false
    )
    private Boolean trangThaiSuDung;
}