package com.pharma.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pharma.backend.dto.CapNhatChiTietGioHangRequestDto;
import com.pharma.backend.dto.GioHangResponseDto;
import com.pharma.backend.dto.ThemVaoGioHangRequestDto;
import com.pharma.backend.security.NguoiDungDangNhap;
import com.pharma.backend.service.GioHangService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/gio-hang")
@RequiredArgsConstructor
public class GioHangController {

    private final GioHangService gioHangService;

    @GetMapping
    public GioHangResponseDto layGioHang(
        @AuthenticationPrincipal
        NguoiDungDangNhap nguoiDungDangNhap
    ) {
        Long maKhachHang = layMaKhachHangDangNhap(
            nguoiDungDangNhap
        );

        return gioHangService.layGioHang(maKhachHang);
    }

    @PostMapping("/them")
    public GioHangResponseDto themVaoGioHang(
        @AuthenticationPrincipal
        NguoiDungDangNhap nguoiDungDangNhap,

        @RequestBody
        ThemVaoGioHangRequestDto request
    ) {
        Long maKhachHang = layMaKhachHangDangNhap(
            nguoiDungDangNhap
        );

        return gioHangService.themSanPhamVaoGioHang(
            maKhachHang,
            request
        );
    }

    @PutMapping("/chi-tiet/{maChiTietGioHang}")
    public GioHangResponseDto capNhatChiTietGioHang(
        @AuthenticationPrincipal
        NguoiDungDangNhap nguoiDungDangNhap,

        @PathVariable
        Long maChiTietGioHang,

        @RequestBody
        CapNhatChiTietGioHangRequestDto request
    ) {
        Long maKhachHang = layMaKhachHangDangNhap(
            nguoiDungDangNhap
        );

        return gioHangService.capNhatChiTietGioHang(
            maKhachHang,
            maChiTietGioHang,
            request
        );
    }

    @DeleteMapping("/chi-tiet/{maChiTietGioHang}")
    public GioHangResponseDto xoaSanPhamKhoiGioHang(
        @AuthenticationPrincipal
        NguoiDungDangNhap nguoiDungDangNhap,

        @PathVariable
        Long maChiTietGioHang
    ) {
        Long maKhachHang = layMaKhachHangDangNhap(
            nguoiDungDangNhap
        );

        return gioHangService.xoaSanPhamKhoiGioHang(
            maKhachHang,
            maChiTietGioHang
        );
    }

    @DeleteMapping
    public GioHangResponseDto xoaTatCaSanPhamTrongGioHang(
        @AuthenticationPrincipal
        NguoiDungDangNhap nguoiDungDangNhap
    ) {
        Long maKhachHang = layMaKhachHangDangNhap(
            nguoiDungDangNhap
        );

        return gioHangService
            .xoaTatCaSanPhamTrongGioHang(maKhachHang);
    }

    private Long layMaKhachHangDangNhap(
        NguoiDungDangNhap nguoiDungDangNhap
    ) {
        if (
            nguoiDungDangNhap == null ||
            nguoiDungDangNhap.getMaKhachHang() == null
        ) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Không xác định được khách hàng đang đăng nhập."
            );
        }

        return nguoiDungDangNhap.getMaKhachHang();
    }
}