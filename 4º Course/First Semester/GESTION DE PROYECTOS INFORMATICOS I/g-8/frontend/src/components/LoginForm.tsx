import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { LoadingSpinner } from "@/components/ui/loading_spinner";
import React, { useState, useEffect } from "react";
import { HiddenCSRFTokenInput } from "./HiddenCSRFTokenInput";

const LoginForm: React.FC = () => {
  const [loginError, setLoginError] = useState<string>("");
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const dataDiv = document.getElementById("data") as HTMLDivElement;
  const next = dataDiv.dataset.next as string;
  const formAction = `/accounts/login/?next=${next}`;

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const error = urlParams.get("error");

    if (error === "invalid_otp") {
      setLoginError("Invalid or expired OTP.");
    }
  }, []);

  const handleSubmitLoginForm = async (
    event: React.FormEvent<HTMLFormElement>
  ) => {
    event.preventDefault();
    setLoginError("");

    const formData = new FormData(event.currentTarget);

    try {
      setIsLoading(true);
      const response = await fetch(formAction, {
        method: "POST",
        headers: {
          "X-Requested-With": "XMLHttpRequest",
          "X-CSRFToken": formData.get("csrfmiddlewaretoken") as string,
        },
        body: formData,
      });

      if (response.redirected) {
        window.location.href = response.url;
      } else {
        setLoginError("Email y contraseña incorrectos");
      }
    } catch (error) {
      setLoginError("Error inesperado. Vuelva a intentarlo.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Card className="w-[350px]">
      <CardHeader>
        <CardTitle>¡Hola!</CardTitle>
        <CardDescription>
          Bienvenid@ a QuizForge. Por favor, introduce tu dirección de correo y
          contraseña.
        </CardDescription>
      </CardHeader>
      <CardContent>
        {isLoading ? (
          <div className="spinner">
            <LoadingSpinner className="text-blue-500 w-12 h-12" />
          </div>
        ) : (
          <form onSubmit={handleSubmitLoginForm}>
            <HiddenCSRFTokenInput />
            <input type="hidden" name="next" value={next} />
            <div className="grid w-full items-center gap-4">
              {loginError && (
                <div className="mt-4 text-red-500 text-sm">{loginError}</div>
              )}
              <div className="flex flex-col space-y-1.5">
                <Label htmlFor="id_username">Correo electrónico</Label>
                <Input
                  id="id_username"
                  name="username"
                  type="email"
                  placeholder="ejemplo@unavarra.es"
                  required
                />
              </div>
              <div className="flex flex-col space-y-1.5">
                <Label htmlFor="id_password">Contraseña</Label>
                <Input
                  id="id_password"
                  name="password"
                  type="password"
                  placeholder="Introduce tu contraseña"
                  required
                />
              </div>
              <div className="flex flex-col pt-2">
                <Button type="submit">Entrar</Button>
              </div>
            </div>
          </form>
        )}
      </CardContent>
    </Card>
  );
};

export default LoginForm;
