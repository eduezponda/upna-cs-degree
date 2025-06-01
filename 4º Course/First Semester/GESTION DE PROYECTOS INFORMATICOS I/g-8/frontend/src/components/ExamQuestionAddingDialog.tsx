import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import {
  ResizableHandle,
  ResizablePanel,
  ResizablePanelGroup,
} from "@/components/ui/resizable";
import { ScrollArea } from "@/components/ui/scroll-area";
import { Separator } from "@/components/ui/separator";
import axios from "axios";
import { Exam } from "@/interfaces/Exam";
import React from "react";
import AddableQuestion, { QuestionAction } from "./AddableQuestion";
import { Question } from "@/interfaces/Question";
import { getCSRFToken } from "./HiddenCSRFTokenInput";

interface ExamAuthoringDialogProps {
  exam: Exam;
  availableQuestions: Question[];
  updateCallback: () => void;
}

const ExamQuestionAddingDialog: React.FC<ExamAuthoringDialogProps> = ({
  exam,
  availableQuestions,
  updateCallback,
}) => {
  const [open, setOpen] = React.useState<boolean>(false);
  const [examQuestions, setExamQuestions] = React.useState<Question[]>([]);

  const totalExamScoreDigits = 2;
  const totalExamScore = examQuestions
    .reduce((sum, question) => sum + question.score, 0)
    .toFixed(totalExamScoreDigits)
    .replace(".", ",");

  const sortedAvailableQuestions = availableQuestions.sort((a, b) => {
    const statsA = a.stats;
    const statsB = b.stats;
    if (statsA && statsB) {
      return statsB.failure_ratio - statsA.failure_ratio;
    }
    return 0;
  });

  const onQuestionAction = async (
    questionId: number,
    action: QuestionAction
  ) => {
    const path = action === "add" ? "add-questions/" : "remove-questions/";
    const headers: any = {
      "X-CSRFToken": getCSRFToken(),
    };
    await axios.post(
      `/api/exams/${exam.id}/` + path,
      {
        question_ids: [questionId],
      },
      { headers: headers }
    );
    if (action === "add") {
      const response = await axios.get(`/api/questions/${questionId}/`);
      const question = response.data;
      setExamQuestions((examQuestions) => [...examQuestions, question]);
    } else {
      setExamQuestions((examQuestions) =>
        examQuestions.filter((question) => question.id !== questionId)
      );
    }
  };

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button>Preguntas</Button>
      </DialogTrigger>
      <DialogContent className="flex flex-col max-w-screen-xl h-5/6">
        <DialogHeader>
          <DialogTitle>Editando examen "{exam.exam_name}"</DialogTitle>
          <DialogDescription>Añade preguntas al examen.</DialogDescription>
        </DialogHeader>
        <ResizablePanelGroup direction="horizontal">
          <ResizablePanel defaultSize={50} className="p-4">
            <h4 className="scroll-m-20 text-xl font-semibold tracking-tight">
              Preguntas disponibles
            </h4>
            <ScrollArea className="h-full w-full p-4">
              {sortedAvailableQuestions.map((question) => (
                <>
                  <AddableQuestion
                    question={question}
                    alreadyAdded={examQuestions.some(
                      (examQuestion) => examQuestion.id === question.id
                    )}
                    action="add"
                    onQuestionAction={onQuestionAction}
                    questionUpdateCallback={updateCallback}
                  />
                  <Separator className="my-4" />
                </>
              ))}
            </ScrollArea>
          </ResizablePanel>
          <ResizableHandle withHandle />
          <ResizablePanel defaultSize={50} className="p-4">
            <div className="flex flex-row justify-between">
              <h4 className="scroll-m-20 text-xl font-semibold tracking-tight">
                Preguntas del examen
              </h4>
              <p>Total: {totalExamScore} puntos</p>
            </div>
            <ScrollArea className="h-full w-full p-4">
              {examQuestions.map((question) => {
                question.stats = undefined;
                return (
                  <>
                    <AddableQuestion
                      question={question}
                      action="remove"
                      onQuestionAction={onQuestionAction}
                      questionUpdateCallback={updateCallback}
                    />
                    <Separator className="my-4" />
                  </>
                );
              })}
            </ScrollArea>
          </ResizablePanel>
        </ResizablePanelGroup>
      </DialogContent>
    </Dialog>
  );
};

export default ExamQuestionAddingDialog;
