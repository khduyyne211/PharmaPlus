package com.pharma.backend.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GioHangResponseDto {

    private Long maGioHang;

    private Long maKhachHang;

    private String trangThaiGioHang;

    private List<ChiTietGioHangResponseDto> danhSachChiTietGioHang;

    private BigDecimal tongTien;
}