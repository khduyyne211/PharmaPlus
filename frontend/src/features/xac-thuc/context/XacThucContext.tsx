import {
  createContext,
  useContext,
  useEffect,
  useState,
} from "react";
import type { ReactNode } from "react";
import { dangNhapApi } from "../api/XacThucApi";
import type {
  DangNhapRequest,
  DangNhapResponse,
  NguoiDungDangNhap,
} from "../types/XacThuc";
import HopThoaiDangNhap from "../components/HopThoaiDangNhap";
import "../styles/XacThuc.css";

interface XacThucContextValue {
  nguoiDungDangNhap: NguoiDungDangNhap | null;
  daDangNhap: boolean;
  dangNhap: (request: DangNhapRequest) => Promise<void>;
  dangXuat: () => void;
  moHopThoaiDangNhap: () => void;
  dongHopThoaiDangNhap: () => void;
}

const XacThucContext =
  createContext<XacThucContextValue | undefined>(
    undefined
  );

interface XacThucProviderProps {
  children: ReactNode;
}

function layNguoiDungDaLuu():
  | NguoiDungDangNhap
  | null {
  const accessToken = localStorage.getItem(
    "pharma_access_token"
  );

  const nguoiDungJson = localStorage.getItem(
    "pharma_nguoi_dung"
  );

  if (!accessToken || !nguoiDungJson) {
    return null;
  }

  try {
    return JSON.parse(
      nguoiDungJson
    ) as NguoiDungDangNhap;
  } catch {
    localStorage.removeItem("pharma_access_token");
    localStorage.removeItem("pharma_nguoi_dung");

    return null;
  }
}

function chuyenSangNguoiDungDangNhap(
  response: DangNhapResponse
): NguoiDungDangNhap {
  return {
    maTaiKhoan: response.maTaiKhoan,
    maKhachHang: response.maKhachHang,
    hoTen: response.hoTen,
    soDienThoai: response.soDienThoai,
    email: response.email,
    vaiTro: response.vaiTro,
  };
}

export function XacThucProvider({
  children,
}: XacThucProviderProps) {
  const [
    nguoiDungDangNhap,
    setNguoiDungDangNhap,
  ] = useState<NguoiDungDangNhap | null>(
    layNguoiDungDaLuu
  );

  const [
    dangHienHopThoaiDangNhap,
    setDangHienHopThoaiDangNhap,
  ] = useState(false);

  const daDangNhap = nguoiDungDangNhap !== null;

  const dangNhap = async (
    request: DangNhapRequest
  ) => {
    const response = await dangNhapApi(request);
    const duLieuDangNhap = response.data;

    const nguoiDungMoi =
      chuyenSangNguoiDungDangNhap(duLieuDangNhap);

    localStorage.setItem(
      "pharma_access_token",
      duLieuDangNhap.accessToken
    );

    localStorage.setItem(
      "pharma_nguoi_dung",
      JSON.stringify(nguoiDungMoi)
    );

    setNguoiDungDangNhap(nguoiDungMoi);
    setDangHienHopThoaiDangNhap(false);
  };

  const dangXuat = () => {
    localStorage.removeItem("pharma_access_token");
    localStorage.removeItem("pharma_nguoi_dung");

    setNguoiDungDangNhap(null);
    setDangHienHopThoaiDangNhap(false);
  };

  const moHopThoaiDangNhap = () => {
    setDangHienHopThoaiDangNhap(true);
  };

  const dongHopThoaiDangNhap = () => {
    setDangHienHopThoaiDangNhap(false);
  };

  useEffect(() => {
    const xuLyTokenKhongHopLe = () => {
      setNguoiDungDangNhap(null);
      setDangHienHopThoaiDangNhap(true);
    };

    window.addEventListener(
      "pharma:dang-xuat",
      xuLyTokenKhongHopLe
    );

    return () => {
      window.removeEventListener(
        "pharma:dang-xuat",
        xuLyTokenKhongHopLe
      );
    };
  }, []);

  return (
    <XacThucContext.Provider
      value={{
        nguoiDungDangNhap,
        daDangNhap,
        dangNhap,
        dangXuat,
        moHopThoaiDangNhap,
        dongHopThoaiDangNhap,
      }}
    >
      {children}

      <HopThoaiDangNhap
        dangHien={dangHienHopThoaiDangNhap}
        dongHopThoai={dongHopThoaiDangNhap}
        dangNhap={dangNhap}
      />
    </XacThucContext.Provider>
  );
}

export function useXacThucContext() {
  const context = useContext(XacThucContext);

  if (!context) {
    throw new Error(
      "useXacThucContext phải được dùng trong XacThucProvider"
    );
  }

  return context;
}