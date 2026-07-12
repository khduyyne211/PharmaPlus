package com.pharma.backend.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.backend.dto.PageResponseDto;
import com.pharma.backend.dto.SanPhamChiTietResponseDto;
import com.pharma.backend.dto.SanPhamResponseDto;
import com.pharma.backend.service.SanPhamService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/san-pham")
@RequiredArgsConstructor
public class SanPhamController {
    private final SanPhamService sanPhamService;

    @GetMapping
    public PageResponseDto<SanPhamResponseDto> layDanhSachSanPham(
            @RequestParam(name = "sapXep", required = false) String sapXep,
            @RequestParam(name = "giaTu", required = false) BigDecimal giaTu,
            @RequestParam(name = "giaDen", required = false) BigDecimal giaDen,
            @RequestParam(name = "maNhaSanXuat", required = false) Long maNhaSanXuat,
            @RequestParam(name = "maDanhMuc", required = false) Long maDanhMuc,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "12") int size
    ) {
        return sanPhamService.layDanhSachSanPham(
                sapXep,
                giaTu,
                giaDen,
                maNhaSanXuat,
                maDanhMuc,
                page,
                size
        );
    }

    @GetMapping("/{maSanPham}")
    public SanPhamChiTietResponseDto layChiTietSanPham(@PathVariable Long maSanPham) {
        return sanPhamService.layChiTietSanPham(maSanPham);
    }
}
