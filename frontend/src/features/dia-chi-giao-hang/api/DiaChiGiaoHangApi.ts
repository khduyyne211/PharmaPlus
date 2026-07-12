import apiClient from "../../../shared/api/apiClient";
import type { DiaChiGiaoHang } from "../types/DiaChiGiaoHang";

export const layDanhSachDiaChiGiaoHangApi = () => {
  return apiClient.get<DiaChiGiaoHang[]>(
    "/dia-chi-giao-hang"
  );
};