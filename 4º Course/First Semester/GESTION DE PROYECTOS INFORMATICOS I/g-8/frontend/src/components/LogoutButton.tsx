import { Button } from "@/components/ui/button";
import React from "react";
import { HiddenCSRFTokenInput } from "./HiddenCSRFTokenInput";

const LogoutButton: React.FC = () => {
  return (
    <form method="post" action="/accounts/logout/">
      <HiddenCSRFTokenInput />
      <div className="grid w-full items-center gap-4">
        <div className="flex flex-col pt-2">
          <Button variant="outline" type="submit">
            Cerrar sesión
          </Button>
        </div>
      </div>
    </form>
  );
};

export default LogoutButton;
