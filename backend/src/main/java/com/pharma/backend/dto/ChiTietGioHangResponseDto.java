package com.pharma.backend.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChiTietGioHangResponseDto {

    private Long maChiTietGioHang;

    private Long maSanPham;

    private String tenSanPham;

    private String hinhAnh;

    private Long maDonViSanPham;

    private Long maDonViTinh;

    private String tenDonViTinh;

    private String kyHieuDonViTinh;

    private Integer soLuong;

    private BigDecimal donGia;

    private BigDecimal thanhTien;

    private List<DonViBanSanPhamResponseDto> danhSachDonViBan;
}