import { useEffect, useState } from "react";
import { layDanhSachDiaChiGiaoHangApi } from "../api/DiaChiGiaoHangApi";
import type { DiaChiGiaoHang } from "../types/DiaChiGiaoHang";

export function useDiaChiGiaoHang() {
  const [danhSachDiaChi, setDanhSachDiaChi] = useState<
    DiaChiGiaoHang[]
  >([]);

  const [dangTaiDuLieu, setDangTaiDuLieu] =
    useState(false);

  const [thongBaoLoi, setThongBaoLoi] =
    useState("");

  const layDanhSachDiaChi = () => {
    setDangTaiDuLieu(true);
    setThongBaoLoi("");

    layDanhSachDiaChiGiaoHangApi()
      .then((response) => {
        setDanhSachDiaChi(response.data);
      })
      .catch(() => {
        setThongBaoLoi(
          "Không thể tải danh sách địa chỉ giao hàng."
        );
      })
      .finally(() => {
        setDangTaiDuLieu(false);
      });
  };

  useEffect(() => {
    layDanhSachDiaChi();
  }, []);

  return {
    danhSachDiaChi,
    dangTaiDuLieu,
    thongBaoLoi,
    layDanhSachDiaChi,
  };
}