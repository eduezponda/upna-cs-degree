import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import LoginForm from "../components/LoginForm";
import "../index.css";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <main className="flex h-screen items-center justify-center">
      <LoginForm />
    </main>
  </StrictMode>
);
