import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "../index.css";
import OneColumnLayout from "@/components/OneColumnLayout";
import ProfessorMainPage from "@/components/ProfessorMainPage";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <OneColumnLayout>
      <ProfessorMainPage />
    </OneColumnLayout>
  </StrictMode>
);
