import apiClient from "../../../shared/api/apiClient";
import type { GioHang } from "../types/GioHang";

interface ThemVaoGioHangRequest {
  maSanPham: number;
  maDonViSanPham: number;
  soLuong: number;
}

interface CapNhatChiTietGioHangRequest {
  soLuong: number;
  maDonViSanPham: number;
}

export const layGioHangApi = () => {
  return apiClient.get<GioHang>("/gio-hang");
};

export const themVaoGioHangApi = (
  request: ThemVaoGioHangRequest
) => {
  return apiClient.post<GioHang>(
    "/gio-hang/them",
    request
  );
};

export const capNhatChiTietGioHangApi = (
  maChiTietGioHang: number,
  request: CapNhatChiTietGioHangRequest
) => {
  return apiClient.put<GioHang>(
    `/gio-hang/chi-tiet/${maChiTietGioHang}`,
    request
  );
};

export const xoaSanPhamKhoiGioHangApi = (
  maChiTietGioHang: number
) => {
  return apiClient.delete<GioHang>(
    `/gio-hang/chi-tiet/${maChiTietGioHang}`
  );
};

export const xoaTatCaSanPhamTrongGioHangApi =
  () => {
    return apiClient.delete<GioHang>("/gio-hang");
  };