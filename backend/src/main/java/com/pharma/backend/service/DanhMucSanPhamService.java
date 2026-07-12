package com.pharma.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.backend.dto.DanhMucSanPhamResponseDto;
import com.pharma.backend.entity.DanhMucSanPham;
import com.pharma.backend.repository.DanhMucSanPhamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DanhMucSanPhamService {
    private final DanhMucSanPhamRepository danhMucSanPhamRepository;

    public List<DanhMucSanPhamResponseDto> layDanhMucMenuKhachHang() {
        List<DanhMucSanPham> danhSachDanhMuc = danhMucSanPhamRepository.findByTrangThaiHienThiTrueOrderByThuTuHienThiAsc();

        return danhSachDanhMuc.stream()
                .filter(danhMuc -> danhMuc.getDanhMucCha() == null)
                .map(danhMuc -> chuyenSangResponseDto(danhMuc, danhSachDanhMuc))
                .toList();
    }

    private DanhMucSanPhamResponseDto chuyenSangResponseDto(DanhMucSanPham danhMuc, List<DanhMucSanPham> danhSachDanhMuc) {
        List<DanhMucSanPhamResponseDto> danhSachDanhMucCon = danhSachDanhMuc.stream()
                .filter(danhMucCon -> danhMucCon.getDanhMucCha() != null && danhMucCon.getDanhMucCha().getMaDanhMuc().equals(danhMuc.getMaDanhMuc())
                )
                .map(danhMucCon -> chuyenSangResponseDto(danhMucCon, danhSachDanhMuc))
                .toList();

        return new DanhMucSanPhamResponseDto(
                danhMuc.getMaDanhMuc(),
                danhMuc.getTenDanhMuc(),
                danhSachDanhMucCon
        );
    }
}
