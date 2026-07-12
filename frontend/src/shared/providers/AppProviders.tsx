import type { ReactNode } from "react";
import { XacThucProvider } from "../../features/xac-thuc/context/XacThucContext";
import { GioHangProvider } from "../../features/gio-hang/context/GioHangContext";

interface AppProvidersProps {
  children: ReactNode;
}

function AppProviders({ children }: AppProvidersProps) {
  return (
    <XacThucProvider>
      <GioHangProvider>
        {children}
      </GioHangProvider>
    </XacThucProvider>
  );
}

export default AppProviders;