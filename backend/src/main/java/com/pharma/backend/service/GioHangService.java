package com.pharma.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.pharma.backend.dto.CapNhatChiTietGioHangRequestDto;
import com.pharma.backend.dto.ChiTietGioHangResponseDto;
import com.pharma.backend.dto.DonViBanSanPhamResponseDto;
import com.pharma.backend.dto.GioHangResponseDto;
import com.pharma.backend.dto.ThemVaoGioHangRequestDto;
import com.pharma.backend.entity.ChiTietGioHang;
import com.pharma.backend.entity.DonViSanPham;
import com.pharma.backend.entity.GioHang;
import com.pharma.backend.entity.KhachHang;
import com.pharma.backend.entity.SanPham;
import com.pharma.backend.repository.ChiTietGioHangRepository;
import com.pharma.backend.repository.DonViSanPhamRepository;
import com.pharma.backend.repository.GioHangRepository;
import com.pharma.backend.repository.KhachHangRepository;
import com.pharma.backend.repository.SanPhamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GioHangService {

    private final GioHangRepository gioHangRepository;

    private final ChiTietGioHangRepository chiTietGioHangRepository;

    private final SanPhamRepository sanPhamRepository;

    private final DonViSanPhamRepository donViSanPhamRepository;

    private final KhachHangRepository khachHangRepository;

    public GioHangResponseDto layGioHang(Long maKhachHang) {
        GioHang gioHang = layHoacTaoGioHang(maKhachHang);

        List<ChiTietGioHang> danhSachChiTiet =
                chiTietGioHangRepository.findByGioHang_MaGioHang(
                        gioHang.getMaGioHang()
                );

        return chuyenSangGioHangResponseDto(gioHang, danhSachChiTiet);
    }

    @Transactional
    public GioHangResponseDto themSanPhamVaoGioHang(
            Long maKhachHang,
            ThemVaoGioHangRequestDto request
    ) {
        kiemTraRequestThemGioHang(request);

        GioHang gioHang = layHoacTaoGioHang(maKhachHang);

        SanPham sanPham = sanPhamRepository.findById(request.getMaSanPham())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        if (Boolean.TRUE.equals(sanPham.getLaThuocKeDon())) {
            throw new RuntimeException("Thuốc kê đơn không được thêm trực tiếp vào giỏ hàng");
        }

        DonViSanPham donViSanPham =
                donViSanPhamRepository.findById(request.getMaDonViSanPham())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn vị bán của sản phẩm"));

        kiemTraDonViSanPhamHopLe(sanPham, donViSanPham);

        ChiTietGioHang chiTietGioHang =
                chiTietGioHangRepository
                        .findByGioHang_MaGioHangAndSanPham_MaSanPhamAndDonViSanPham_MaDonViSanPham(
                                gioHang.getMaGioHang(),
                                sanPham.getMaSanPham(),
                                donViSanPham.getMaDonViSanPham()
                        )
                        .orElse(null);

        BigDecimal donGia = layDonGiaTheoDonVi(sanPham, donViSanPham);

        if (chiTietGioHang == null) {
            chiTietGioHang = new ChiTietGioHang();
            chiTietGioHang.setGioHang(gioHang);
            chiTietGioHang.setSanPham(sanPham);
            chiTietGioHang.setDonViSanPham(donViSanPham);
            chiTietGioHang.setSoLuong(request.getSoLuong());
            chiTietGioHang.setDonGia(donGia);
        } else {
            chiTietGioHang.setSoLuong(
                    chiTietGioHang.getSoLuong() + request.getSoLuong()
            );
        }

        capNhatThanhTien(chiTietGioHang);

        chiTietGioHangRepository.save(chiTietGioHang);

        capNhatNgayGioHang(gioHang);

        return layGioHang(maKhachHang);
    }

    @Transactional
        public GioHangResponseDto capNhatChiTietGioHang(
                Long maKhachHang,
                Long maChiTietGioHang,
                CapNhatChiTietGioHangRequestDto request
        ) {
        kiemTraRequestCapNhatChiTietGioHang(request);

        GioHang gioHang = layHoacTaoGioHang(maKhachHang);

        ChiTietGioHang chiTietGioHang =
                chiTietGioHangRepository
                        .findByMaChiTietGioHangAndGioHang_MaGioHang(
                                maChiTietGioHang,
                                gioHang.getMaGioHang()
                        )
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng"));

        SanPham sanPham = chiTietGioHang.getSanPham();

        DonViSanPham donViSanPhamMoi =
                donViSanPhamRepository.findById(request.getMaDonViSanPham())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn vị bán của sản phẩm"));

        kiemTraDonViSanPhamHopLe(sanPham, donViSanPhamMoi);

        ChiTietGioHang chiTietTrungDonVi =
                chiTietGioHangRepository
                        .findByGioHang_MaGioHangAndSanPham_MaSanPhamAndDonViSanPham_MaDonViSanPham(
                                gioHang.getMaGioHang(),
                                sanPham.getMaSanPham(),
                                donViSanPhamMoi.getMaDonViSanPham()
                        )
                        .orElse(null);

        if (chiTietTrungDonVi != null
                && !chiTietTrungDonVi.getMaChiTietGioHang().equals(chiTietGioHang.getMaChiTietGioHang())) {

                chiTietTrungDonVi.setSoLuong(
                        chiTietTrungDonVi.getSoLuong() + request.getSoLuong()
                );

                chiTietTrungDonVi.setDonGia(
                        layDonGiaTheoDonVi(sanPham, donViSanPhamMoi)
                );

                capNhatThanhTien(chiTietTrungDonVi);

                chiTietGioHangRepository.save(chiTietTrungDonVi);
                chiTietGioHangRepository.delete(chiTietGioHang);
        } else {
                chiTietGioHang.setSoLuong(request.getSoLuong());
                chiTietGioHang.setDonViSanPham(donViSanPhamMoi);
                chiTietGioHang.setDonGia(
                        layDonGiaTheoDonVi(sanPham, donViSanPhamMoi)
                );

                capNhatThanhTien(chiTietGioHang);

                chiTietGioHangRepository.save(chiTietGioHang);
        }

        capNhatNgayGioHang(gioHang);

        return layGioHang(maKhachHang);
        }

    @Transactional
    public GioHangResponseDto xoaSanPhamKhoiGioHang(
            Long maKhachHang,
            Long maChiTietGioHang
    ) {
        GioHang gioHang = layHoacTaoGioHang(maKhachHang);

        ChiTietGioHang chiTietGioHang =
                chiTietGioHangRepository
                        .findByMaChiTietGioHangAndGioHang_MaGioHang(
                                maChiTietGioHang,
                                gioHang.getMaGioHang()
                        )
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng"));

        chiTietGioHangRepository.delete(chiTietGioHang);

        capNhatNgayGioHang(gioHang);

        return layGioHang(maKhachHang);
    }

    @Transactional
    public GioHangResponseDto xoaTatCaSanPhamTrongGioHang(Long maKhachHang) {
        GioHang gioHang = layHoacTaoGioHang(maKhachHang);

        chiTietGioHangRepository.deleteByGioHang_MaGioHang(
                gioHang.getMaGioHang()
        );

        capNhatNgayGioHang(gioHang);

        return layGioHang(maKhachHang);
    }

    private GioHang layHoacTaoGioHang(Long maKhachHang) {
        return gioHangRepository
        .findByKhachHang_MaKhachHang(maKhachHang)
        .orElseGet(() -> taoGioHangMoi(maKhachHang));
    }

    private GioHang taoGioHangMoi(Long maKhachHang) {
        KhachHang khachHang = khachHangRepository
                .findById(maKhachHang)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Khách hàng không tồn tại."
                ));

        GioHang gioHangMoi = new GioHang();
        gioHangMoi.setKhachHang(khachHang);
        gioHangMoi.setTrangThaiGioHang("DANG_SU_DUNG");

        return gioHangRepository.save(gioHangMoi);
    }

    private void kiemTraRequestThemGioHang(ThemVaoGioHangRequestDto request) {
        if (request.getMaSanPham() == null) {
            throw new RuntimeException("Mã sản phẩm không được để trống");
        }

        if (request.getMaDonViSanPham() == null) {
            throw new RuntimeException("Mã đơn vị bán không được để trống");
        }

        if (request.getSoLuong() == null || request.getSoLuong() <= 0) {
            throw new RuntimeException("Số lượng sản phẩm phải lớn hơn 0");
        }
    }

    private void kiemTraDonViSanPhamHopLe(
            SanPham sanPham,
            DonViSanPham donViSanPham
    ) {
        if (!donViSanPham.getSanPham().getMaSanPham().equals(sanPham.getMaSanPham())) {
            throw new RuntimeException("Đơn vị bán không thuộc sản phẩm này");
        }

        if (!Boolean.TRUE.equals(donViSanPham.getChoPhepBan())) {
            throw new RuntimeException("Đơn vị này không được phép bán");
        }

        if (!Boolean.TRUE.equals(donViSanPham.getTrangThai())) {
            throw new RuntimeException("Đơn vị bán đang ngừng hoạt động");
        }
    }

    private BigDecimal layDonGiaTheoDonVi(
            SanPham sanPham,
            DonViSanPham donViSanPham
    ) {
        if (donViSanPham.getGiaBanTheoDonVi() != null) {
            return donViSanPham.getGiaBanTheoDonVi();
        }

        return sanPham.getGiaBan();
    }

    private void capNhatThanhTien(ChiTietGioHang chiTietGioHang) {
        chiTietGioHang.setThanhTien(
                chiTietGioHang.getDonGia()
                        .multiply(BigDecimal.valueOf(chiTietGioHang.getSoLuong()))
        );
    }

    private void capNhatNgayGioHang(GioHang gioHang) {
        gioHang.setNgayCapNhat(LocalDateTime.now());
        gioHangRepository.save(gioHang);
    }

    private GioHangResponseDto chuyenSangGioHangResponseDto(
            GioHang gioHang,
            List<ChiTietGioHang> danhSachChiTiet
    ) {
        List<ChiTietGioHangResponseDto> danhSachChiTietDto =
                danhSachChiTiet.stream()
                        .map(this::chuyenSangChiTietGioHangResponseDto)
                        .toList();

        BigDecimal tongTien = danhSachChiTiet.stream()
                .map(ChiTietGioHang::getThanhTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new GioHangResponseDto(
                gioHang.getMaGioHang(),
                gioHang.getKhachHang().getMaKhachHang(),
                gioHang.getTrangThaiGioHang(),
                danhSachChiTietDto,
                tongTien
        );
    }

    private void kiemTraRequestCapNhatChiTietGioHang( CapNhatChiTietGioHangRequestDto request ) {
        if (request.getSoLuong() == null || request.getSoLuong() <= 0) {
                throw new RuntimeException("Số lượng sản phẩm phải lớn hơn 0");
        }

        if (request.getMaDonViSanPham() == null) {
                throw new RuntimeException("Mã đơn vị bán không được để trống");
        }
    }

    private ChiTietGioHangResponseDto chuyenSangChiTietGioHangResponseDto(
        ChiTietGioHang chiTietGioHang
        ) {
        DonViSanPham donViSanPham = chiTietGioHang.getDonViSanPham();

        List<DonViSanPham> danhSachDonViBan =
                donViSanPhamRepository.findBySanPham_MaSanPhamAndChoPhepBanTrueAndTrangThaiTrue(
                        chiTietGioHang.getSanPham().getMaSanPham()
                );

        List<DonViBanSanPhamResponseDto> danhSachDonViBanDto =
                danhSachDonViBan.stream()
                        .map(this::chuyenSangDonViBanSanPhamResponseDto)
                        .toList();

        return new ChiTietGioHangResponseDto(
                chiTietGioHang.getMaChiTietGioHang(),
                chiTietGioHang.getSanPham().getMaSanPham(),
                chiTietGioHang.getSanPham().getTenSanPham(),
                chiTietGioHang.getSanPham().getHinhAnh(),
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
                chiTietGioHang.getSoLuong(),
                chiTietGioHang.getDonGia(),
                chiTietGioHang.getThanhTien(),
                danhSachDonViBanDto
        );
    }

    private DonViBanSanPhamResponseDto chuyenSangDonViBanSanPhamResponseDto(
        DonViSanPham donViSanPham
        ) {
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
}