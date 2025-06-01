import React from "react";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Plus, X } from "lucide-react";
import {
  Tooltip,
  TooltipContent,
  TooltipProvider,
  TooltipTrigger,
} from "@/components/ui/tooltip";
import { Question } from "@/interfaces/Question";
import DeleteDialog from "@/components/DeleteDialog";

export type QuestionAction = "add" | "remove";

interface AddableQuestionProps {
  question: Question;
  action: QuestionAction;
  alreadyAdded?: boolean;
  onQuestionAction: (questionId: number, action: QuestionAction) => void;
  questionUpdateCallback: () => void;
}

const AddableQuestion: React.FC<AddableQuestionProps> = ({
  question,
  action,
  alreadyAdded,
  onQuestionAction,
  questionUpdateCallback,
}) => {
  return (
    <div className="flex flex-row flex-wrap justify-between items-center">
      <div>
        <p className="font-bold">{question.statement}</p>
        <Badge variant="secondary">{question.score} puntos</Badge>
      </div>
      <TooltipProvider>
        <div className="flex flex-wrap gap-1 items-center">
          <Tooltip>
            {question.stats ? (
              <Badge variant="outline">
                Ratio de fallos: {question.stats.failure_ratio.toFixed(2)}
              </Badge>
            ) : null}
            <TooltipTrigger>
              <Button
                variant="outline"
                size="icon"
                onClick={() => {
                  onQuestionAction(question.id, action);
                }}
                disabled={action === "add" && alreadyAdded}
              >
                {action === "add" ? <Plus /> : <X />}
              </Button>
            </TooltipTrigger>
            <TooltipContent>
              {action === "add" ? (
                <p>Añadir al examen</p>
              ) : (
                <p>Quitar la pregunta del examen</p>
              )}
            </TooltipContent>
          </Tooltip>
          {action === "add" && (
            <Tooltip>
              <TooltipTrigger>
                <DeleteDialog
                  id={question.id}
                  type="question"
                  updateCallback={questionUpdateCallback}
                />
              </TooltipTrigger>
              <TooltipContent>
                <p>Eliminar pregunta</p>
              </TooltipContent>
            </Tooltip>
          )}
        </div>
      </TooltipProvider>
    </div>
  );
};

export default AddableQuestion;
