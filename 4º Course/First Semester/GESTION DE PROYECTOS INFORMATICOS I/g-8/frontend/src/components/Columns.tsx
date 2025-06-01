import { ColumnDef } from "@tanstack/react-table";
import { Checkbox } from "@/components/ui/checkbox";
import { Button } from "@/components/ui/button";
import { ArrowUpDown } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import axios from "axios";
import { User } from "@/interfaces/User";

export const columns = (
  examId: number,
  toast: ReturnType<typeof useToast>["toast"],
  assignedStudents: string[],
  setAssignedStudents: React.Dispatch<React.SetStateAction<string[]>>,
  selectedRows: string[],
  setSelectedRows: React.Dispatch<React.SetStateAction<string[]>>
): ColumnDef<User>[] => [
  {
    id: "select",
    header: () => null,
    cell: ({ row }) => {
      const studentId = row.original.id.toString();
      const isAssigned = assignedStudents.includes(studentId);
      const isSelected = selectedRows.includes(studentId);

      return (
        <Checkbox
          checked={isAssigned}
          onCheckedChange={async (value: boolean) => {
            const headers = {
              "X-CSRFToken":
                document.cookie
                  .split("; ")
                  .find((row) => row.startsWith("csrftoken="))
                  ?.split("=")[1] || "",
            };

            try {
              if (value) {
                await axios.post(
                  `/api/exams/${examId}/assign-students/`,
                  { student_user_ids: [studentId] },
                  { headers }
                );
                setAssignedStudents((prev) => [...prev, studentId]);
                setSelectedRows((prev) => [...prev, studentId]);
                toast({
                  title: "Estudiante Asignado",
                  description: `El usuario: ${row.original.email} ha sido asignado al examen.`,
                });
              } else {
                await axios.post(
                  `/api/exams/${examId}/unassign-students/`,
                  { student_user_ids: [studentId] },
                  { headers }
                );
                setAssignedStudents((prev) =>
                  prev.filter((id) => id !== studentId)
                );
                setSelectedRows((prev) =>
                  prev.filter((id) => id !== studentId)
                );
                toast({
                  title: "Estudiante Desasignado",
                  description: `El usuario: ${row.original.email} ha sido desasignado del examen.`,
                });
              }
            } catch (error) {
              toast({
                title: "Error",
                description: "Ha ocurrido un error en la asignación.",
              });
              console.error(error);
            }
          }}
          aria-label="Select row"
          onChange={() => {
            if (isSelected) {
              setSelectedRows((prev) => prev.filter((id) => id !== studentId));
            } else {
              setSelectedRows((prev) => [...prev, studentId]);
            }
          }}
        />
      );
    },
  },
  {
    accessorKey: "email",
    header: ({ column }) => (
      <Button
        variant="ghost"
        onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
      >
        Email
        <ArrowUpDown className="ml-2 h-4 w-4" />
      </Button>
    ),
    enableSorting: true,
  },
];
