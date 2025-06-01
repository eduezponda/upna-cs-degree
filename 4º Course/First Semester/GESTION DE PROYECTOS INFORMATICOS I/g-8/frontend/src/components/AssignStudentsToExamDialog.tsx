import { Exam } from "@/interfaces/Exam";
import React, { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { columns as createColumns } from "./Columns";
import { useToast } from "@/hooks/use-toast";
import { User } from "@/interfaces/User";
import { DataTable } from "./DataTable";
import axios from "axios";

interface ExamAuthoringDialogProps {
  exam: Exam;
}

const AssignStudentsToExamDialog: React.FC<ExamAuthoringDialogProps> = ({
  exam,
}) => {
  const [open, setOpen] = useState<boolean>(false);
  const [availableUsers, setAvailableUsers] = useState<User[]>([]);
  const [assignedStudents, setAssignedStudents] = useState<string[]>([]);
  const [selectedRows, setSelectedRows] = useState<string[]>([]);

  const fetchAvailableUser = async () => {
    let nextUrl = "/api/users/";
    let newUsers: User[] = [];
    while (nextUrl) {
      const response = await axios.get(nextUrl);
      const fetchedUsers = response.data.results;
      newUsers = newUsers.concat(fetchedUsers);
      nextUrl = response.data.next;
    }
    setAvailableUsers(newUsers);
  };

  const fetchAssignedStudents = async () => {
    const headers = {
      "X-CSRFToken":
        document.cookie
          .split("; ")
          .find((row) => row.startsWith("csrftoken="))
          ?.split("=")[1] || "",
    };

    try {
      const response = await axios.get(`/api/exams/${exam.id}`, { headers });
      setAssignedStudents(response.data.assigned_students.map(String));
    } catch (error) {
      console.error("Error al obtener estudiantes asignados al examen", error);
    }
  };

  useEffect(() => {
    fetchAvailableUser();
    fetchAssignedStudents();
  }, []);

  const { toast } = useToast();
  const columns = createColumns(
    exam.id,
    toast,
    assignedStudents,
    setAssignedStudents,
    selectedRows,
    setSelectedRows
  );

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button variant="secondary">Estudiantes</Button>
      </DialogTrigger>
      <DialogContent className="flex flex-col max-w-screen-xl h-5/6">
        <DialogHeader>
          <DialogTitle>
            Asignar examen "{exam.exam_name}" a estudiantes
          </DialogTitle>
          <DialogDescription>
            Asigna el examen a los estudiantes.
          </DialogDescription>
          <DataTable columns={columns} data={availableUsers} examId={exam.id} />
        </DialogHeader>
      </DialogContent>
    </Dialog>
  );
};

export default AssignStudentsToExamDialog;
