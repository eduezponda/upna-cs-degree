import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "../index.css";
import OneColumnLayout from "@/components/OneColumnLayout";
import StudentMainPage from "@/components/StudentMainPage";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <OneColumnLayout>
      <StudentMainPage />
    </OneColumnLayout>
  </StrictMode>
);
