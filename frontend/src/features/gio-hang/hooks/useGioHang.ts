import { useEffect, useState } from "react";
import type { GioHang } from "../types/GioHang";
import { useGioHangContext } from "../context/GioHangContext";
import { useXacThucContext } from "../../xac-thuc/context/XacThucContext";
import {
  capNhatChiTietGioHangApi,
  layGioHangApi,
  xoaSanPhamKhoiGioHangApi,
  xoaTatCaSanPhamTrongGioHangApi,
} from "../api/GioHangApi";

export function useGioHang() {
  const [gioHang, setGioHang] =
    useState<GioHang | undefined>(undefined);

  const [dangTaiDuLieu, setDangTaiDuLieu] =
    useState(false);

  const [dangXuLy, setDangXuLy] =
    useState(false);

  const { daDangNhap } = useXacThucContext();

  const { capNhatSoDongChiTietGioHang } =
    useGioHangContext();

  const layGioHang = () => {
    if (!daDangNhap) {
      setGioHang(undefined);
      return;
    }

    setDangTaiDuLieu(true);

    layGioHangApi()
      .then((response) => {
        setGioHang(response.data);
        capNhatSoDongChiTietGioHang(
          response.data
        );
      })
      .catch(() => {
        setGioHang(undefined);
      })
      .finally(() => {
        setDangTaiDuLieu(false);
      });
  };

  const capNhatChiTietGioHang = (
    maChiTietGioHang: number,
    soLuong: number,
    maDonViSanPham: number
  ) => {
    if (soLuong <= 0 || !daDangNhap) {
      return;
    }

    setDangXuLy(true);

    capNhatChiTietGioHangApi(
      maChiTietGioHang,
      {
        soLuong,
        maDonViSanPham,
      }
    )
      .then((response) => {
        setGioHang(response.data);
        capNhatSoDongChiTietGioHang(
          response.data
        );
      })
      .finally(() => {
        setDangXuLy(false);
      });
  };

  const xoaSanPhamKhoiGioHang = (
    maChiTietGioHang: number
  ) => {
    if (!daDangNhap) {
      return;
    }

    setDangXuLy(true);

    xoaSanPhamKhoiGioHangApi(
      maChiTietGioHang
    )
      .then((response) => {
        setGioHang(response.data);
        capNhatSoDongChiTietGioHang(
          response.data
        );
      })
      .finally(() => {
        setDangXuLy(false);
      });
  };

  const xoaTatCaSanPhamTrongGioHang = () => {
    if (!daDangNhap) {
      return;
    }

    setDangXuLy(true);

    xoaTatCaSanPhamTrongGioHangApi()
      .then((response) => {
        setGioHang(response.data);
        capNhatSoDongChiTietGioHang(
          response.data
        );
      })
      .finally(() => {
        setDangXuLy(false);
      });
  };

  useEffect(() => {
    if (daDangNhap) {
      layGioHang();
    } else {
      setGioHang(undefined);
    }
  }, [daDangNhap]);

  return {
    gioHang,
    dangTaiDuLieu,
    dangXuLy,
    layGioHang,
    capNhatChiTietGioHang,
    xoaSanPhamKhoiGioHang,
    xoaTatCaSanPhamTrongGioHang,
  };
}