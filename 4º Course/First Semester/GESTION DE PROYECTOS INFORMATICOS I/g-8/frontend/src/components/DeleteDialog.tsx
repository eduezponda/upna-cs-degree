import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";
import { Button } from "@/components/ui/button";
import { Trash2 } from "lucide-react";
import axios from "axios";
import { useToast } from "@/hooks/use-toast";

type DeletionType = "exam" | "question";

interface DeleteDialogProps {
  id: number;
  type: DeletionType;
  updateCallback: () => void;
}

const getCSRFToken = () => {
  const csrfCookie = document.cookie
    .split("; ")
    .find((row) => row.startsWith("csrftoken="));
  return csrfCookie ? csrfCookie.split("=")[1] : null;
};

const DeleteDialog: React.FC<DeleteDialogProps> = ({
  id,
  type,
  updateCallback,
}) => {
  const { toast } = useToast();

  const handleDeleteAction = async () => {
    try {
      const endpoint =
        type === "exam" ? `/api/exams/${id}/` : `/api/questions/${id}/`;
      await axios.delete(endpoint, {
        headers: { "X-CSRFToken": getCSRFToken() || "" },
      });

      toast({
        title: type === "exam" ? "Examen eliminado" : "Pregunta eliminada",
        description: (
          <p>
            {type === "exam"
              ? `El examen ${id} ha sido eliminado.`
              : `La pregunta ${id} ha sido eliminada.`}
          </p>
        ),
      });

      updateCallback();
    } catch (exception) {
      toast({
        title: "Error",
        description: `No se ha podido eliminar el ${
          type === "exam" ? "examen" : "pregunta"
        }.`,
      });
      console.error(exception);
    }
  };

  return (
    <AlertDialog>
      <AlertDialogTrigger asChild>
        {type === "exam" ? (
          <Button
            className="absolute top-2 right-2 hover:text-red-500"
            variant="ghost"
            size="icon"
          >
            <Trash2 className="h-4 w-4" />
          </Button>
        ) : (
          <Button variant="outline" size="icon" className="hover:text-red-500">
            <Trash2 />
          </Button>
        )}
      </AlertDialogTrigger>
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogTitle>¿Estás seguro?</AlertDialogTitle>
          <AlertDialogDescription>
            {type === "exam"
              ? "¿Estás seguro de que quieres borrar el examen? Esta acción es irreversible."
              : "¿Estás seguro de que quieres borrar la pregunta? Esta acción es irreversible."}
          </AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogCancel>Cancelar</AlertDialogCancel>
          <AlertDialogAction onClick={handleDeleteAction}>
            Confirmar
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
};

export default DeleteDialog;
