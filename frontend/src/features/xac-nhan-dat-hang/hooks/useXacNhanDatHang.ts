import { useEffect, useMemo, useState } from "react";
import { layGioHangApi } from "../../gio-hang/api/GioHangApi";
import type { GioHang } from "../../gio-hang/types/GioHang";
import { layDanhSachDiaChiGiaoHangApi } from "../../dia-chi-giao-hang/api/DiaChiGiaoHangApi";
import type { DiaChiGiaoHang } from "../../dia-chi-giao-hang/types/DiaChiGiaoHang";
import type { PhuongThucThanhToan } from "../types/XacNhanDatHang";

export function useXacNhanDatHang() {
  const [gioHang, setGioHang] = useState<GioHang | undefined>(undefined);

  const [danhSachDiaChi, setDanhSachDiaChi] = useState<
    DiaChiGiaoHang[]
  >([]);

  const [maDiaChiDangChon, setMaDiaChiDangChon] = useState<
    number | undefined
  >(undefined);

  const [phuongThucThanhToan, setPhuongThucThanhToan] =
    useState<PhuongThucThanhToan>("COD");

  const [ghiChu, setGhiChu] = useState("");

  const [dangTaiDuLieu, setDangTaiDuLieu] = useState(false);
  const [thongBaoLoi, setThongBaoLoi] = useState("");
  const [dangMoDanhSachDiaChi, setDangMoDanhSachDiaChi] =
    useState(false);

  const taiDuLieuXacNhanDatHang = () => {
    setDangTaiDuLieu(true);
    setThongBaoLoi("");

    Promise.all([
      layGioHangApi(),
      layDanhSachDiaChiGiaoHangApi(),
    ])
      .then(([gioHangResponse, diaChiResponse]) => {
        const gioHangMoi = gioHangResponse.data;
        const danhSachDiaChiMoi = diaChiResponse.data;

        setGioHang(gioHangMoi);
        setDanhSachDiaChi(danhSachDiaChiMoi);

        const diaChiMacDinh = danhSachDiaChiMoi.find(
          (diaChi) => diaChi.laMacDinh
        );

        if (diaChiMacDinh) {
          setMaDiaChiDangChon(diaChiMacDinh.maDiaChi);
        } else {
          setMaDiaChiDangChon(undefined);
        }
      })
      .catch(() => {
        setThongBaoLoi(
          "Không thể tải thông tin xác nhận đặt hàng."
        );
      })
      .finally(() => {
        setDangTaiDuLieu(false);
      });
  };

  useEffect(() => {
    taiDuLieuXacNhanDatHang();
  }, []);

  const diaChiDangChon = useMemo(() => {
    return danhSachDiaChi.find(
      (diaChi) => diaChi.maDiaChi === maDiaChiDangChon
    );
  }, [danhSachDiaChi, maDiaChiDangChon]);

  const gioHangRong =
    !gioHang ||
    gioHang.danhSachChiTietGioHang.length === 0;

  const coTheHoanTat =
    !gioHangRong && diaChiDangChon !== undefined;

  const chonDiaChi = (maDiaChi: number) => {
    setMaDiaChiDangChon(maDiaChi);
    setDangMoDanhSachDiaChi(false);
  };

  return {
    gioHang,
    danhSachDiaChi,
    diaChiDangChon,
    phuongThucThanhToan,
    ghiChu,
    dangTaiDuLieu,
    thongBaoLoi,
    dangMoDanhSachDiaChi,
    gioHangRong,
    coTheHoanTat,

    setPhuongThucThanhToan,
    setGhiChu,
    setDangMoDanhSachDiaChi,
    chonDiaChi,
    taiDuLieuXacNhanDatHang,
  };
}