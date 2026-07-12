import axios from "axios";

const apiClient = axios.create({
    baseURL: "http://localhost:8080/api",
});

apiClient.interceptors.request.use((config) => {
  const accessToken = localStorage.getItem(
    "pharma_access_token"
  );

  if (accessToken) {
    config.headers.Authorization = `Bearer ${accessToken}`;
  }

  return config;
});

apiClient.interceptors.response.use(
  (response) => response,

  (error) => {
    const laApiDangNhap = String(
      error.config?.url || ""
    ).includes("/xac-thuc/dang-nhap");

    if (
      error.response?.status === 401 &&
      !laApiDangNhap
    ) {
      localStorage.removeItem("pharma_access_token");
      localStorage.removeItem("pharma_nguoi_dung");

      window.dispatchEvent(
        new Event("pharma:dang-xuat")
      );
    }

    return Promise.reject(error);
  }
);

export default apiClient;