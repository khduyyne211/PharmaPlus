import apiClient from "../../../shared/api/apiClient";
import type { PageResponse } from "../../../shared/types/PageResponse";
import type { SanPham } from "../types/SanPham";

export interface LayDanhSachSanPhamParams {
  sapXep?: string;
  giaTu?: number;
  giaDen?: number;
  maNhaSanXuat?: number;
  maDanhMuc?: number;
  page?: number;
  size?: number;
}

export const layDanhSachSanPhamApi = (params: LayDanhSachSanPhamParams) => {
  return apiClient.get<PageResponse<SanPham>>("/san-pham", {
    params,
  });
};