import axiosClient from "../../../api/axiosClient";
import apiClient from "../../../shared/api/apiClient";

import type { PhanTrangResponse } from "../../../types/PhanTrangResponse";
import type { PageResponse } from "../../../shared/types/PageResponse";
import type { SanPham } from "../types/SanPham";

/*
 * Tham số dành cho màn hình quản lý sản phẩm của dược sĩ.
 */
export interface ThamSoLocSanPham {
  page: number;
  size: number;

  keyword?: string;
  laThuocKeDon?: boolean | string;
  trangThaiSanPham?: boolean | string;
  maDanhMuc?: number | string;
  maNhaSanXuat?: number | string;
}

/*
 * Dữ liệu gửi lên backend khi dược sĩ thêm hoặc sửa sản phẩm.
 */
export interface SanPhamRequest {
  maDanhMuc: number;
  maNhaSanXuat: number | null;
  tenSanPham: string;
  hinhAnh: string | null;
  giaBan: number;
  laThuocKeDon: boolean;
  moTaNgan: string | null;
}

/*
 * Tham số dành cho danh sách sản phẩm phía khách hàng.
 */
export interface LayDanhSachSanPhamParams {
  sapXep?: string;
  giaTu?: number;
  giaDen?: number;
  maNhaSanXuat?: number;
  maDanhMuc?: number;
  page?: number;
  size?: number;
}

/*
 * API danh sách phân trang dành cho màn hình quản lý của dược sĩ.
 */
export const layDanhSachSanPhamPhanTrang = (
  thamSo: ThamSoLocSanPham
) => {
  return axiosClient.get<PhanTrangResponse<SanPham>>(
    "/san-pham/phan-trang",
    {
      params: thamSo,
    }
  );
};

/*
 * API lấy đầy đủ thông tin, đơn vị và quy đổi của một sản phẩm.
 */
export const layChiTietSanPhamDayDu = (maSanPham: number) => {
  return axiosClient.get<SanPham>(
    `/san-pham/${maSanPham}/chi-tiet-day-du`
  );
};

/*
 * API thêm sản phẩm trong màn hình quản trị.
 */
export const themSanPham = (duLieu: SanPhamRequest) => {
  return axiosClient.post<SanPham>("/san-pham", duLieu);
};

/*
 * API cập nhật sản phẩm.
 */
export const capNhatSanPham = (
  maSanPham: number,
  duLieu: SanPhamRequest
) => {
  return axiosClient.put<SanPham>(
    `/san-pham/${maSanPham}`,
    duLieu
  );
};

/*
 * API ẩn sản phẩm.
 */
export const anSanPham = (maSanPham: number) => {
  return axiosClient.put<SanPham>(
    `/san-pham/${maSanPham}/an`
  );
};

/*
 * API hiển thị lại sản phẩm.
 */
export const hienSanPham = (maSanPham: number) => {
  return axiosClient.put<SanPham>(
    `/san-pham/${maSanPham}/hien`
  );
};

/*
 * API lấy danh sách sản phẩm dành cho giao diện khách hàng.
 */
export const layDanhSachSanPhamApi = (
  params: LayDanhSachSanPhamParams
) => {
  return apiClient.get<PageResponse<SanPham>>("/san-pham", {
    params,
  });
};