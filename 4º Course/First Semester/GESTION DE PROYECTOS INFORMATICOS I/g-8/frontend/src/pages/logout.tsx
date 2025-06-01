import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import LoggedOut from "@/components/LoggedOut";
import "../index.css";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <main className="flex h-screen items-center justify-center">
      <LoggedOut />
    </main>
  </StrictMode>
);
