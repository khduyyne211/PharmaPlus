package com.pharma.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DiaChiGiaoHangResponseDto {

    private Long maDiaChi;

    private String tenNguoiNhan;

    private String soDienThoaiNhan;

    private String tinhThanh;

    private String quanHuyen;

    private String phuongXa;

    private String diaChiChiTiet;

    private Boolean laMacDinh;

    private Boolean trangThaiSuDung;
}