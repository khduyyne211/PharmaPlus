package com.pharma.backend.controller;

import com.pharma.backend.dto.DanhMucSanPhamResponseDto;
import com.pharma.backend.service.DanhMucSanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danh-muc-san-pham")
@RequiredArgsConstructor
public class DanhMucSanPhamController {

    private final DanhMucSanPhamService danhMucSanPhamService;

    @GetMapping("/menu")
    public List<DanhMucSanPhamResponseDto> layDanhMucMenuKhachHang() {
        return danhMucSanPhamService.layDanhMucMenuKhachHang();
    }
}