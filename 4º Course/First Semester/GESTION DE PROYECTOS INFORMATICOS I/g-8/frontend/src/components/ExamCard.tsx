import React, { ReactNode } from "react";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Exam } from "@/interfaces/Exam";
import { Badge } from "./ui/badge";
import DeleteDialog from "@/components/DeleteDialog";

interface ExamCardProps {
  exam: Exam;
  children: ReactNode;
  examsUpdateCallback?: () => void;
  professor: boolean;
}

const ExamCard: React.FC<ExamCardProps> = ({
  exam,
  children,
  examsUpdateCallback,
  professor,
}) => {
  const dateFormatOptions: object = {
    year: "numeric",
    month: "long",
    day: "numeric",
  };
  const timeFormatOptions: object = { hour: "2-digit", minute: "2-digit" };

  return (
    <Card className="relative flex flex-col flex-wrap justify-between min-w-52">
      <CardHeader>
        <div className="flex flex-row justify-between">
          <CardTitle>{exam.exam_name}</CardTitle>
          {exam.grade !== null && (
            <Badge variant="outline">Nota: {exam.grade}</Badge>
          )}
          {professor && (
            <DeleteDialog
              id={exam.id}
              type={"exam"}
              updateCallback={examsUpdateCallback || (() => {})}
            />
          )}
        </div>
        <CardDescription>
          {exam.start_date.toLocaleDateString("es-ES", dateFormatOptions)},{" "}
          {exam.start_date.toLocaleTimeString("es-ES", timeFormatOptions)}
          <br />
          Duración: {exam.duration_minutes} minutos
        </CardDescription>
      </CardHeader>
      <CardContent className="flex flex-row justify-between">
        {children}
      </CardContent>
    </Card>
  );
};

export default ExamCard;
