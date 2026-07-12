package com.pharma.backend.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pharma.backend.dto.DonViBanSanPhamResponseDto;
import com.pharma.backend.dto.DuLieuChuyenMonThuocResponseDto;
import com.pharma.backend.dto.PageResponseDto;
import com.pharma.backend.dto.SanPhamChiTietResponseDto;
import com.pharma.backend.dto.SanPhamResponseDto;
import com.pharma.backend.dto.ThanhPhanHoatChatResponseDto;
import com.pharma.backend.entity.DanhMucSanPham;
import com.pharma.backend.entity.DonViSanPham;
import com.pharma.backend.entity.DuLieuChuyenMonThuoc;
import com.pharma.backend.entity.QuyDoiDonVi;
import com.pharma.backend.entity.SanPham;
import com.pharma.backend.entity.ThanhPhanHoatChat;
import com.pharma.backend.repository.DanhMucSanPhamRepository;
import com.pharma.backend.repository.DonViSanPhamRepository;
import com.pharma.backend.repository.DuLieuChuyenMonThuocRepository;
import com.pharma.backend.repository.QuyDoiDonViRepository;
import com.pharma.backend.repository.SanPhamRepository;
import com.pharma.backend.repository.ThanhPhanHoatChatRepository;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SanPhamService {

    private final SanPhamRepository sanPhamRepository;

    private final DanhMucSanPhamRepository danhMucSanPhamRepository;

    private final DonViSanPhamRepository donViSanPhamRepository;

    private final QuyDoiDonViRepository quyDoiDonViRepository;

    private final ThanhPhanHoatChatRepository thanhPhanHoatChatRepository;

    private final DuLieuChuyenMonThuocRepository duLieuChuyenMonThuocRepository;

    public PageResponseDto<SanPhamResponseDto> layDanhSachSanPham(String sapXep,BigDecimal giaTu,BigDecimal giaDen,Long maNhaSanXuat,Long maDanhMuc,int page,int size) {
        int pageHopLe = Math.max(page, 0);
        int sizeHopLe = Math.min(Math.max(size, 1), 24);

        List<Long> danhSachMaDanhMucCanLoc = layDanhSachMaDanhMucCanLoc(maDanhMuc);

        Specification<SanPham> dieuKienLoc = taoDieuKienLocSanPham(
                giaTu,
                giaDen,
                maNhaSanXuat,
                danhSachMaDanhMucCanLoc
        );

        Sort sort = taoSapXepSanPham(sapXep);

        Pageable pageable = PageRequest.of(pageHopLe, sizeHopLe, sort);

        Page<SanPham> trangSanPham = sanPhamRepository.findAll(
                dieuKienLoc,
                pageable
        );

        List<SanPham> danhSachSanPham = trangSanPham.getContent();

        if (danhSachSanPham.isEmpty()) {
                return new PageResponseDto<>(
                        List.of(),
                        trangSanPham.getNumber(),
                        trangSanPham.getSize(),
                        trangSanPham.getTotalElements(),
                        trangSanPham.getTotalPages(),
                        trangSanPham.isLast()
                );
        }

        List<Long> danhSachMaSanPham = danhSachSanPham.stream()
                .map(SanPham::getMaSanPham)
                .toList();


        List<DonViSanPham> danhSachDonViBan =
                donViSanPhamRepository
                        .findBySanPham_MaSanPhamInAndChoPhepBanTrueAndTrangThaiTrue(
                                danhSachMaSanPham
                        );
        
        Map<Long, List<DonViSanPham>> donViBanTheoSanPham =
                danhSachDonViBan.stream()
                        .collect(Collectors.groupingBy(
                                donViSanPham -> donViSanPham.getSanPham().getMaSanPham()
                        ));

        List<QuyDoiDonVi> danhSachQuyDoiDonVi =
                quyDoiDonViRepository.findBySanPham_MaSanPhamInAndTrangThaiTrue(
                        danhSachMaSanPham
                );

        Map<Long, List<QuyDoiDonVi>> quyDoiTheoSanPham =
                danhSachQuyDoiDonVi.stream()
                        .collect(Collectors.groupingBy(
                                quyDoi -> quyDoi.getSanPham().getMaSanPham()
                        ));

        List<SanPhamResponseDto> danhSachSanPhamDto =
                danhSachSanPham.stream()
                        .map(sanPham -> chuyenSangSanPhamResponseDto(
                                sanPham,
                                donViBanTheoSanPham.getOrDefault(
                                        sanPham.getMaSanPham(),
                                        Collections.emptyList()
                                ),
                                quyDoiTheoSanPham.getOrDefault(
                                        sanPham.getMaSanPham(),
                                        Collections.emptyList()
                                )
                        ))
                        .toList();

        return new PageResponseDto<>(
                danhSachSanPhamDto,
                trangSanPham.getNumber(),
                trangSanPham.getSize(),
                trangSanPham.getTotalElements(),
                trangSanPham.getTotalPages(),
                trangSanPham.isLast()
        );
    }

    private Specification<SanPham> taoDieuKienLocSanPham(BigDecimal giaTu, BigDecimal giaDen, Long maNhaSanXuat, List<Long> danhSachMaDanhMucCanLoc) {
        return (root, query, criteriaBuilder) -> {
                List<Predicate> danhSachDieuKien = new ArrayList<>();

                danhSachDieuKien.add(
                        criteriaBuilder.equal(
                                root.get("trangThaiSanPham"),
                                1
                        )
                );

                if (giaTu != null) {
                danhSachDieuKien.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("giaBan"),
                                giaTu
                        )
                );
                }

                if (giaDen != null) {
                danhSachDieuKien.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("giaBan"),
                                giaDen
                        )
                );
                }

                if (maNhaSanXuat != null) {
                danhSachDieuKien.add(
                        criteriaBuilder.equal(
                                root.get("nhaSanXuat").get("maNhaSanXuat"),
                                maNhaSanXuat
                        )
                );
                }

                if (danhSachMaDanhMucCanLoc != null
                        && !danhSachMaDanhMucCanLoc.isEmpty()) {
                danhSachDieuKien.add(
                        root.get("danhMucSanPham")
                                .get("maDanhMuc")
                                .in(danhSachMaDanhMucCanLoc)
                );
                }

                return criteriaBuilder.and(
                        danhSachDieuKien.toArray(new Predicate[0])
                );
        };
    }

    private List<Long> layDanhSachMaDanhMucCanLoc(Long maDanhMuc) {
        if (maDanhMuc == null) {
                return Collections.emptyList();
        }

        List<DanhMucSanPham> tatCaDanhMuc =
                danhMucSanPhamRepository.findByTrangThaiHienThiTrueOrderByThuTuHienThiAsc();

        Map<Long, List<DanhMucSanPham>> danhMucConTheoMaCha =
                tatCaDanhMuc.stream()
                        .filter(danhMuc -> danhMuc.getDanhMucCha() != null)
                        .collect(Collectors.groupingBy(
                                danhMuc -> danhMuc.getDanhMucCha().getMaDanhMuc()
                        ));

        Set<Long> ketQua = new LinkedHashSet<>();

        themDanhMucVaDanhMucCon(
                maDanhMuc,
                danhMucConTheoMaCha,
                ketQua
        );

        return new ArrayList<>(ketQua);
        }

        private void themDanhMucVaDanhMucCon(
                Long maDanhMuc,
                Map<Long, List<DanhMucSanPham>> danhMucConTheoMaCha,
                Set<Long> ketQua
        ) {
        if (maDanhMuc == null) {
                return;
        }

        if (!ketQua.add(maDanhMuc)) {
                return;
        }

        List<DanhMucSanPham> danhSachDanhMucCon =
                danhMucConTheoMaCha.getOrDefault(
                        maDanhMuc,
                        Collections.emptyList()
                );

        for (DanhMucSanPham danhMucCon : danhSachDanhMucCon) {
                themDanhMucVaDanhMucCon(
                        danhMucCon.getMaDanhMuc(),
                        danhMucConTheoMaCha,
                        ketQua
                );
        }
    }

    private Sort taoSapXepSanPham(String sapXep) {
        if ("GIA_TANG_DAN".equals(sapXep)) {
            return Sort.by(Sort.Direction.ASC, "giaBan");
        }

        if ("GIA_GIAM_DAN".equals(sapXep)) {
            return Sort.by(Sort.Direction.DESC, "giaBan");
        }

        return Sort.by(Sort.Direction.DESC, "maSanPham");
    }

    private SanPhamResponseDto chuyenSangSanPhamResponseDto(
                SanPham sanPham,
                List<DonViSanPham> danhSachDonViBan,
                List<QuyDoiDonVi> danhSachQuyDoiDonVi
    ) {
        List<DonViBanSanPhamResponseDto> danhSachDonViBanDto =
                danhSachDonViBan.stream()
                        .map(this::chuyenSangDonViBanSanPhamResponseDto)
                        .toList();

        return new SanPhamResponseDto(
                sanPham.getMaSanPham(),
                sanPham.getTenSanPham(),
                sanPham.getHinhAnh(),
                sanPham.getGiaBan(),
                sanPham.getLaThuocKeDon(),
                sanPham.getNhaSanXuat() != null
                        ? sanPham.getNhaSanXuat().getTenNhaSanXuat()
                        : null,
                sanPham.getDanhMucSanPham() != null
                        ? sanPham.getDanhMucSanPham().getMaDanhMuc()
                        : null,
                sanPham.getDanhMucSanPham() != null
                        ? sanPham.getDanhMucSanPham().getTenDanhMuc()
                        : null,
                sanPham.getMoTaNgan(),
                taoMoTaQuyDoi(danhSachQuyDoiDonVi),
                danhSachDonViBanDto
        );
    }

    private DonViBanSanPhamResponseDto chuyenSangDonViBanSanPhamResponseDto(DonViSanPham donViSanPham) {
        return new DonViBanSanPhamResponseDto(
                donViSanPham.getMaDonViSanPham(),
                donViSanPham.getDonViTinh() != null
                        ? donViSanPham.getDonViTinh().getMaDonViTinh()
                        : null,
                donViSanPham.getDonViTinh() != null
                        ? donViSanPham.getDonViTinh().getTenDonViTinh()
                        : null,
                donViSanPham.getDonViTinh() != null
                        ? donViSanPham.getDonViTinh().getKyHieu()
                        : null,
                donViSanPham.getGiaBanTheoDonVi(),
                donViSanPham.getLaDonViCoSo()
        );
    }

    @Transactional(readOnly = true)
    public SanPhamChiTietResponseDto layChiTietSanPham(Long maSanPham) {
        SanPham sanPham = sanPhamRepository.findById(maSanPham)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        List<DonViSanPham> danhSachDonViBan =
                donViSanPhamRepository.findBySanPham_MaSanPhamAndChoPhepBanTrueAndTrangThaiTrue(
                        maSanPham
                );

        List<ThanhPhanHoatChat> danhSachThanhPhanHoatChat =
                thanhPhanHoatChatRepository.findBySanPham_MaSanPham(maSanPham);

        DuLieuChuyenMonThuoc duLieuChuyenMonThuoc =
                duLieuChuyenMonThuocRepository.findBySanPham_MaSanPham(maSanPham)
                        .orElse(null);

        List<QuyDoiDonVi> danhSachQuyDoiDonVi =
                quyDoiDonViRepository.findBySanPham_MaSanPhamAndTrangThaiTrue(
                        maSanPham
                );

        return chuyenSangSanPhamChiTietResponseDto(
                sanPham,
                danhSachDonViBan,
                danhSachThanhPhanHoatChat,
                duLieuChuyenMonThuoc,
                danhSachQuyDoiDonVi
        );
    }

    private SanPhamChiTietResponseDto chuyenSangSanPhamChiTietResponseDto(
        SanPham sanPham,
        List<DonViSanPham> danhSachDonViBan,
        List<ThanhPhanHoatChat> danhSachThanhPhanHoatChat,
        DuLieuChuyenMonThuoc duLieuChuyenMonThuoc,
        List<QuyDoiDonVi> danhSachQuyDoiDonVi
    ) {
        List<DonViBanSanPhamResponseDto> danhSachDonViBanDto =
                danhSachDonViBan.stream()
                        .map(this::chuyenSangDonViBanSanPhamResponseDto)
                        .toList();

        List<ThanhPhanHoatChatResponseDto> danhSachThanhPhanHoatChatDto =
                danhSachThanhPhanHoatChat.stream()
                        .map(this::chuyenSangThanhPhanHoatChatResponseDto)
                        .toList();

        return new SanPhamChiTietResponseDto(
                sanPham.getMaSanPham(),
                sanPham.getTenSanPham(),
                sanPham.getHinhAnh(),
                sanPham.getGiaBan(),
                sanPham.getLaThuocKeDon(),
                sanPham.getTrangThaiSanPham(),
                sanPham.getMoTaNgan(),
                sanPham.getMoTa(),
                sanPham.getDanhMucSanPham() != null
                        ? sanPham.getDanhMucSanPham().getMaDanhMuc()
                        : null,
                sanPham.getDanhMucSanPham() != null
                        ? sanPham.getDanhMucSanPham().getTenDanhMuc()
                        : null,
                sanPham.getNhaSanXuat() != null
                        ? sanPham.getNhaSanXuat().getTenNhaSanXuat()
                        : null,
                taoMoTaQuyDoi(danhSachQuyDoiDonVi),
                danhSachDonViBanDto,
                danhSachThanhPhanHoatChatDto,
                chuyenSangDuLieuChuyenMonThuocResponseDto(duLieuChuyenMonThuoc)
        );
    }

    private ThanhPhanHoatChatResponseDto chuyenSangThanhPhanHoatChatResponseDto(ThanhPhanHoatChat thanhPhanHoatChat) {
        return new ThanhPhanHoatChatResponseDto(
                thanhPhanHoatChat.getMaThanhPhan(),
                thanhPhanHoatChat.getHoatChat() != null
                        ? thanhPhanHoatChat.getHoatChat().getMaHoatChat()
                        : null,
                thanhPhanHoatChat.getHoatChat() != null
                        ? thanhPhanHoatChat.getHoatChat().getTenHoatChat()
                        : null,
                thanhPhanHoatChat.getHamLuong(),
                thanhPhanHoatChat.getDonViHamLuong(),
                thanhPhanHoatChat.getVaiTroHoatChat(),
                thanhPhanHoatChat.getGhiChu()
        );
    }

    private DuLieuChuyenMonThuocResponseDto chuyenSangDuLieuChuyenMonThuocResponseDto(DuLieuChuyenMonThuoc duLieuChuyenMonThuoc) {
        if (duLieuChuyenMonThuoc == null) {
            return null;
        }

        return new DuLieuChuyenMonThuocResponseDto(
                duLieuChuyenMonThuoc.getDangBaoChe(),
                duLieuChuyenMonThuoc.getCongDungThamKhao(),
                duLieuChuyenMonThuoc.getCachDungThamKhao(),
                duLieuChuyenMonThuoc.getCanhBaoAnToan(),
                duLieuChuyenMonThuoc.getPhanLoaiThuoc(),
                duLieuChuyenMonThuoc.getTrangThaiXacNhan()
        );
    }

        private String taoMoTaQuyDoi(List<QuyDoiDonVi> danhSachQuyDoiDonVi) {
                if (danhSachQuyDoiDonVi == null || danhSachQuyDoiDonVi.isEmpty()) {
                        return null;
                }

                List<QuyDoiDonVi> danhSachQuyDoiHopLe = danhSachQuyDoiDonVi.stream()
                        .filter(quyDoi -> Boolean.TRUE.equals(quyDoi.getTrangThai()))
                        .filter(quyDoi -> laDonViDuocPhepBan(quyDoi.getDonViNguon()))
                        .filter(quyDoi -> laDonViDuocPhepBan(quyDoi.getDonViDich()))
                        .toList();

                if (danhSachQuyDoiHopLe.isEmpty()) {
                        return null;
                }

                Map<Long, QuyDoiDonVi> quyDoiTheoDonViNguon =
                        danhSachQuyDoiHopLe.stream()
                                .collect(Collectors.toMap(
                                        quyDoi -> quyDoi.getDonViNguon().getMaDonViSanPham(),
                                        quyDoi -> quyDoi,
                                        (quyDoiCu, quyDoiMoi) -> quyDoiCu
                                ));

                Set<Long> danhSachMaDonViDich = danhSachQuyDoiHopLe.stream()
                        .map(quyDoi -> quyDoi.getDonViDich().getMaDonViSanPham())
                        .collect(Collectors.toSet());

                Optional<QuyDoiDonVi> quyDoiDauTienOptional = danhSachQuyDoiHopLe.stream()
                        .filter(quyDoi -> !danhSachMaDonViDich.contains(
                                quyDoi.getDonViNguon().getMaDonViSanPham()
                        ))
                        .findFirst();

                QuyDoiDonVi quyDoiHienTai =
                        quyDoiDauTienOptional.orElse(danhSachQuyDoiHopLe.get(0));

                StringBuilder moTaQuyDoi = new StringBuilder();

                moTaQuyDoi.append(layTenDonViTinh(quyDoiHienTai.getDonViNguon()));

                Set<Long> donViDaDuyet = new HashSet<>();

                while (quyDoiHienTai != null) {
                        Long maDonViNguon = quyDoiHienTai.getDonViNguon().getMaDonViSanPham();

                        if (donViDaDuyet.contains(maDonViNguon)) {
                                break;
                        }

                        donViDaDuyet.add(maDonViNguon);

                        moTaQuyDoi
                                .append(" ")
                                .append(dinhDangSoLuongQuyDoi(quyDoiHienTai.getSoLuongDich()))
                                .append(" ")
                                .append(layTenDonViTinh(quyDoiHienTai.getDonViDich()));

                        Long maDonViTiepTheo = quyDoiHienTai.getDonViDich().getMaDonViSanPham();

                        quyDoiHienTai = quyDoiTheoDonViNguon.get(maDonViTiepTheo);

                        if (quyDoiHienTai != null) {
                        moTaQuyDoi.append(" x");
                        }
                }

                return moTaQuyDoi.toString();
        }

        private String layTenDonViTinh(DonViSanPham donViSanPham) {
                if (donViSanPham == null || donViSanPham.getDonViTinh() == null) {
                        return "";
                }

                return donViSanPham.getDonViTinh().getTenDonViTinh();
        }

        private String dinhDangSoLuongQuyDoi(BigDecimal soLuong) {
                if (soLuong == null) {
                        return "";
                }

                return soLuong.stripTrailingZeros().toPlainString();
        }

        private boolean laDonViDuocPhepBan(DonViSanPham donViSanPham) {
                return donViSanPham != null
                        && Boolean.TRUE.equals(donViSanPham.getChoPhepBan())
                        && Boolean.TRUE.equals(donViSanPham.getTrangThai());
        }
}