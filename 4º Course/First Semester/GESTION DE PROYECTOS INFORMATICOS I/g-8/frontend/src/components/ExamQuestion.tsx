import React from "react";
import { Label } from "@/components/ui/label";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { Question } from "@/interfaces/Question";
import { getCSRFToken } from "./HiddenCSRFTokenInput";
import axios from "axios";
import { Checkbox } from "@/components/ui/checkbox";

interface ExamQuestionProps {
  examId: number;
  question: Question;
}

const ExamQuestion: React.FC<ExamQuestionProps> = ({ examId, question }) => {
  const localStorageSelectedKey = `question${question.id}Selection`;
  const lastChoice = localStorage.getItem(localStorageSelectedKey) || undefined;

  const handleValueChange = async (choiceIdString: string) => {
    const choiceId = parseInt(choiceIdString);
    const headers: any = {
      "X-CSRFToken": getCSRFToken(),
    };
    try {
      await axios.post(
        "/api/submissions/",
        {
          question: question.id,
          choice: choiceId,
          exam: examId,
        },
        { headers: headers }
      );
      localStorage.setItem(localStorageSelectedKey, choiceIdString);
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <div className="p-3">
      <div className="flex flex-row justify-between mb-2">
        <strong>{question.statement}</strong>
        <p>{question.score} puntos</p>
      </div>
      {question.is_multiple_choice ? (
        question.choices.map((choice) => (
          <div key={choice.id} className="flex items-center space-x-2 mt-2">
            <Checkbox id={choice.id.toString()} />
            <Label htmlFor={choice.answer_option}>{choice.answer_option}</Label>
          </div>
        ))
      ) : (
        <RadioGroup onValueChange={handleValueChange} defaultValue={lastChoice}>
          {question.choices.map((choice) => (
            <div className="flex items-center space-x-2">
              <RadioGroupItem
                value={choice.id.toString()}
                id={choice.id.toString()}
              />
              <Label htmlFor={choice.answer_option}>
                {choice.answer_option}
              </Label>
            </div>
          ))}
        </RadioGroup>
      )}
    </div>
  );
};

export default ExamQuestion;
