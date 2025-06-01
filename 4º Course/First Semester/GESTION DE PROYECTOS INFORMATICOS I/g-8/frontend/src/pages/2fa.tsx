import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "../index.css";
import { InputOTPForm } from "@/components/InputOTPForm";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <main className="flex h-screen items-center justify-center">
      <InputOTPForm />
    </main>
  </StrictMode>
);
