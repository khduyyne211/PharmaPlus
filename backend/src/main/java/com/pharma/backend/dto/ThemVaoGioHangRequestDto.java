package com.pharma.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThemVaoGioHangRequestDto {

    private Long maSanPham;

    private Long maDonViSanPham;

    private Integer soLuong;
}