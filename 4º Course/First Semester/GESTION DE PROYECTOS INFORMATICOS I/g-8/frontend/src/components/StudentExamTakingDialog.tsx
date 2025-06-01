import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import ExamQuestion from "./ExamQuestion";
import { Exam } from "@/interfaces/Exam";
import { Question } from "@/interfaces/Question";
import axios from "axios";
import React from "react";

interface ExamAuthoringDialogProps {
  exam: Exam;
}

const StudentExamTakingDialog: React.FC<ExamAuthoringDialogProps> = ({
  exam,
}) => {
  const [open, setOpen] = React.useState<boolean>(false);
  const [questions, setQuestions] = React.useState<Question[]>([]);

  let dialogOpenButton = <Button disabled>No ha empezado</Button>;
  if (exam.status === "active")
    dialogOpenButton = <Button>Realizar examen</Button>;
  else if (exam.status === "finished")
    dialogOpenButton = <Button disabled>Examen finalizado</Button>;

  const handleOpenChange = async (open: boolean) => {
    setOpen(open);
    if (!open) return;
    const examInfo: Exam = (await axios.get(`/api/exams/${exam.id}/`)).data;
    let questions = [];
    for (const questionId of examInfo.questions) {
      const question: Question = (
        await axios.get(`/api/questions/${questionId}/`)
      ).data;
      questions.push(question);
    }
    setQuestions(questions);
  };

  return (
    <Dialog open={open} onOpenChange={handleOpenChange}>
      <DialogTrigger asChild>{dialogOpenButton}</DialogTrigger>
      <DialogContent className="flex flex-col max-w-screen-xl h-5/6 overflow-y-scroll max-h-screen">
        <DialogHeader>
          <DialogTitle>{exam.exam_name}</DialogTitle>
        </DialogHeader>
        {questions.map((question) => (
          <ExamQuestion question={question} examId={exam.id} />
        ))}
      </DialogContent>
    </Dialog>
  );
};

export default StudentExamTakingDialog;
