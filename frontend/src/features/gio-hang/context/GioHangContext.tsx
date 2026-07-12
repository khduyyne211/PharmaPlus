import {
  createContext,
  useContext,
  useEffect,
  useState,
} from "react";
import type { ReactNode } from "react";
import type { GioHang } from "../types/GioHang";
import { layGioHangApi } from "../api/GioHangApi";
import { useXacThucContext } from "../../xac-thuc/context/XacThucContext";
import "../styles/ThongBaoGioHang.css";

interface GioHangContextValue {
  soDongChiTietGioHang: number;
  capNhatSoDongChiTietGioHang: (
    gioHang: GioHang
  ) => void;
  hienThiThongBaoThemGioHang: () => void;
}

const GioHangContext =
  createContext<GioHangContextValue | undefined>(
    undefined
  );

interface GioHangProviderProps {
  children: ReactNode;
}

export function GioHangProvider({
  children,
}: GioHangProviderProps) {
  const { daDangNhap } = useXacThucContext();

  const [
    soDongChiTietGioHang,
    setSoDongChiTietGioHang,
  ] = useState(0);

  const [
    dangHienThongBao,
    setDangHienThongBao,
  ] = useState(false);

  useEffect(() => {
    if (!daDangNhap) {
      setSoDongChiTietGioHang(0);
      return;
    }

    layGioHangApi()
      .then((response) => {
        setSoDongChiTietGioHang(
          response.data.danhSachChiTietGioHang.length
        );
      })
      .catch(() => {
        setSoDongChiTietGioHang(0);
      });
  }, [daDangNhap]);

  const capNhatSoDongChiTietGioHang = (
    gioHang: GioHang
  ) => {
    setSoDongChiTietGioHang(
      gioHang.danhSachChiTietGioHang.length
    );
  };

  const hienThiThongBaoThemGioHang = () => {
    setDangHienThongBao(true);
  };

  const dongThongBao = () => {
    setDangHienThongBao(false);
  };

  return (
    <GioHangContext.Provider
      value={{
        soDongChiTietGioHang,
        capNhatSoDongChiTietGioHang,
        hienThiThongBaoThemGioHang,
      }}
    >
      {children}

      {dangHienThongBao && (
        <div
          className="thong-bao-gio-hang-overlay"
          onClick={dongThongBao}
        >
          <div
            className="thong-bao-gio-hang-hop"
            onClick={(event) =>
              event.stopPropagation()
            }
          >
            <button
              type="button"
              className="thong-bao-gio-hang-nut-dong"
              onClick={dongThongBao}
            >
              ×
            </button>

            <div className="thong-bao-gio-hang-icon">
              <i className="bi bi-check-lg"></i>
            </div>

            <p>
              Sản phẩm đã được thêm vào Giỏ hàng.
            </p>
          </div>
        </div>
      )}
    </GioHangContext.Provider>
  );
}

export function useGioHangContext() {
  const context = useContext(GioHangContext);

  if (!context) {
    throw new Error(
      "useGioHangContext phải được dùng trong GioHangProvider"
    );
  }

  return context;
}