import { Exam } from "@/interfaces/Exam";
import React, { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import ExamGradesChart from "./ExamGradesChart";
import { StudentsData } from "./ExamGradesChart";

interface StudentsGradesSummaryProps {
  studentsGrades: Exam[];
  exam: Exam;
}

const ExamGradesSummary: React.FC<StudentsGradesSummaryProps> = ({
  exam,
  studentsGrades,
}) => {
  const [open, setOpen] = useState<boolean>(false);
  const [chartData, setChartData] = useState<StudentsData>({
    suspenso: 0,
    aprobado: 0,
    bien: 0,
    notable: 0,
    sobresaliente: 0,
  });

  const orderStudentsGrades = () => {
    let newData: StudentsData = {
      suspenso: 0,
      aprobado: 0,
      bien: 0,
      notable: 0,
      sobresaliente: 0,
    };

    studentsGrades.forEach((studentGrade) => {
      const grade = studentGrade.grade;

      if (grade != null) {
        if (grade < 5) {
          newData.suspenso += 1;
        } else if (grade >= 5 && grade < 6) {
          newData.aprobado += 1;
        } else if (grade >= 6 && grade < 7) {
          newData.bien += 1;
        } else if (grade >= 7 && grade < 9) {
          newData.notable += 1;
        } else if (grade >= 9) {
          newData.sobresaliente += 1;
        }
      }
    });

    setChartData(newData);
  };

  useEffect(() => {
    orderStudentsGrades();
  }, [studentsGrades]);

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button variant="secondary">Resultados</Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Estadísticas del Examen: Resumen de Notas</DialogTitle>
          <DialogDescription>Calificación final (escala 0-10 puntos)</DialogDescription>
        </DialogHeader>
        <div className="h-3/4 mx-auto">
          <ExamGradesChart studentsData={chartData} exam={exam} />
        </div>
      </DialogContent>
    </Dialog>
  );
};

export default ExamGradesSummary;
