package com.pharma.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "nha_san_xuat")
public class NhaSanXuat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_nha_san_xuat")
    private Long maNhaSanXuat;

    @Column(name = "ten_nha_san_xuat", nullable = false, length = 150)
    private String tenNhaSanXuat;

    @Column(name = "quoc_gia", length = 100)
    private String quocGia;

    @Column(name = "dia_chi", length = 255)
    private String diaChi;

    @Column(name = "trang_thai", nullable = false)
    private Boolean trangThai;
}