import apiClient from "../../../shared/api/apiClient";
import type {
  DangNhapRequest,
  DangNhapResponse,
} from "../types/XacThuc";

export const dangNhapApi = (request: DangNhapRequest) => {
  return apiClient.post<DangNhapResponse>(
    "/xac-thuc/dang-nhap",
    request
  );
};