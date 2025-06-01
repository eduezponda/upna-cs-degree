import React from "react";

const LoggedOut: React.FC = () => {
  return (
    <div className="flex flex-col space-y-2 px-4 sm:px-8 md:px-16 lg:px-32 xl:px-48">
      <h2 className="scroll-m-20 border-b pb-2 text-3xl font-semibold tracking-tight first:mt-0">
        Sesión cerrada
      </h2>
      <p>Has cerrado sesión correctamente</p>
      <p>Serás redirigido a la página de login en unos momentos...</p>
    </div>
  );
};

export default LoggedOut;
