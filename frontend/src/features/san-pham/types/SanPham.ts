import type { DonViBanSanPham } from "../../../shared/types/DonViBanSanPham";

/*
 * Đơn vị sản phẩm dùng trong màn hình quản lý của dược sĩ.
 */
export interface DonViSanPham {
  maDonViSanPham: number;
  maSanPham: number;
  tenSanPham: string;

  maDonViTinh: number;
  tenDonViTinh: string;
  kyHieu: string;

  giaBanTheoDonVi: number | null;

  laDonViCoSo: boolean;
  choPhepBan: boolean;
  choPhepNhap: boolean;
  trangThai: boolean;
}

/*
 * Thông tin quy đổi đơn vị dùng trong màn hình quản lý của dược sĩ.
 */
export interface QuyDoiDonVi {
  maQuyDoi: number;
  maSanPham: number;

  maDonViNguon: number;
  tenDonViNguon: string;
  kyHieuDonViNguon: string;
  soLuongNguon: number;

  maDonViDich: number;
  tenDonViDich: string;
  kyHieuDonViDich: string;
  soLuongDich: number;

  trangThai: boolean;
}

/*
 * Kiểu sản phẩm dùng chung cho:
 * - giao diện quản lý sản phẩm của dược sĩ;
 * - giao diện danh sách sản phẩm của khách hàng.
 */
export interface SanPham {
  maSanPham: number;

  maDanhMuc: number;
  tenDanhMuc: string;

  maNhaSanXuat: number | null;
  tenNhaSanXuat: string | null;

  tenSanPham: string;
  hinhAnh: string | null;
  giaBan: number;
  laThuocKeDon: boolean;
  trangThaiSanPham: boolean;
  moTaNgan: string | null;
  ngayTao: string;

  /*
   * Dữ liệu dành cho phần quản lý của dược sĩ.
   */
  danhSachDonViSanPham?: DonViSanPham[] | null;
  danhSachQuyDoiDonVi?: QuyDoiDonVi[] | null;

  /*
   * Dữ liệu dành cho phần hiển thị sản phẩm của khách hàng.
   */
  moTaQuyDoi: string | null;
  danhSachDonViBan: DonViBanSanPham[];
}

/*
 * Kiểu dữ liệu danh mục trong các ô chọn của màn hình quản lý.
 */
export interface DanhMucSanPhamOption {
  maDanhMuc: number;
  tenDanhMuc: string;
  trangThaiHienThi: boolean;
}

/*
 * Kiểu dữ liệu nhà sản xuất trong các ô chọn của màn hình quản lý.
 */
export interface NhaSanXuatOption {
  maNhaSanXuat: number;
  tenNhaSanXuat: string;
  trangThai: boolean;
}