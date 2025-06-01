import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useToast } from "@/hooks/use-toast";
import { Plus } from "lucide-react";
import { getUserGroupName, UserGroupName } from "@/interfaces/User";
import React, { FormEvent } from "react";
import axios from "axios";
import { HiddenCSRFTokenInput } from "./HiddenCSRFTokenInput";

interface UserCreationDialogProps {
  usersUpdateCallback: () => void;
}

const UserCreationDialog: React.FC<UserCreationDialogProps> = ({
  usersUpdateCallback,
}) => {
  const [open, setOpen] = React.useState<boolean>(false);
  const { toast } = useToast();

  const handleFormSubmit = async (event: FormEvent) => {
    event.preventDefault();
    setOpen(false);
    const formData = new FormData(event.target as HTMLFormElement);
    const email = formData.get("email")?.toString().toLowerCase();
    const userData = {
      email: email,
      password: formData.get("password"),
      groups: [
        {
          name: formData.get("user-group"),
        },
      ],
    };
    const headers: any = {
      "X-CSRFToken": formData.get("csrfmiddlewaretoken"),
    };
    try {
      await axios.post("/api/users/", userData, {
        headers: headers,
      });
      toast({
        title: "Usuario creado",
        description: (
          <p>
            El usuario <strong>{email}</strong> se ha creado con éxito.
          </p>
        ),
      });
      usersUpdateCallback();
    } catch (exception) {
      toast({
        title: "Error",
        description: "No se ha podido crear el usuario.",
      });
      console.log(exception);
    }
  };

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button className="inline-flex items-center">
          <Plus className="mr-2" />
          Añadir usuario
        </Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-[500px]">
        <form id="user-creation-form" onSubmit={handleFormSubmit}>
          <HiddenCSRFTokenInput />
          <DialogHeader>
            <DialogTitle>Añadir usuario</DialogTitle>
            <DialogDescription>
              Introduce los datos del nuevo usuario.
            </DialogDescription>
          </DialogHeader>
          <div className="grid gap-4 py-4">
            <div className="grid grid-cols-5 items-center gap-4 mb-4">
              <Label htmlFor="email" className="col-span-2">
                Dirección de correo
              </Label>
              <Input
                id="email"
                name="email"
                type="email"
                placeholder="ejemplo@unavarra.es"
                className="col-span-3"
              />
              <Label htmlFor="password" className="col-span-2">
                Contraseña
              </Label>
              <Input
                id="password"
                name="password"
                type="password"
                className="col-span-3"
              />
            </div>
            <div className="grid grid-cols-2 items-center">
              <Label>Tipo de usuario</Label>
              <RadioGroup defaultValue="student" name="user-group">
                {Object.keys(UserGroupName).map((userGroup) => {
                  //@ts-ignore
                  const id: string = UserGroupName[userGroup];
                  return (
                    <div className="flex items-center space-x-2">
                      <RadioGroupItem value={id} id={id} />
                      <Label htmlFor={id}>
                        {getUserGroupName([{ name: id as UserGroupName }])}
                      </Label>
                    </div>
                  );
                })}
              </RadioGroup>
            </div>
          </div>
          <DialogFooter>
            <Button type="submit">Añadir</Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
};

export default UserCreationDialog;
