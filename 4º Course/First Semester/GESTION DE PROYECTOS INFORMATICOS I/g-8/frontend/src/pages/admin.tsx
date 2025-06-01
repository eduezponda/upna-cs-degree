import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "../index.css";
import OneColumnLayout from "@/components/OneColumnLayout";
import UsersTable from "@/components/UsersTable";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <OneColumnLayout>
      <h2 className="scroll-m-20 border-b pb-2 text-3xl font-semibold tracking-tight first:mt-0">
        Usuarios registrados
      </h2>
      <p className="mb-3">
        Consulta los usuarios registrados en QuizForge, o añade nuevos pinchando
        sobre el botón de la derecha.
      </p>
      <UsersTable />
    </OneColumnLayout>
  </StrictMode>
);
