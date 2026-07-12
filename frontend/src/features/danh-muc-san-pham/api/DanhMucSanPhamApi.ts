import apiClient from "../../../shared/api/apiClient";
import type { DanhMucSanPham } from "../types/DanhMucSanPham";

export const layDanhMucMenuApi = () => {
  return apiClient.get<DanhMucSanPham[]>("/danh-muc-san-pham/menu");
};