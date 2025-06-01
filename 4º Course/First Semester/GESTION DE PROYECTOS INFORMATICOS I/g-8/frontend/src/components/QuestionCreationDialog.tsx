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
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useToast } from "@/hooks/use-toast";
import { Plus } from "lucide-react";
import React from "react";
import { Checkbox } from "@/components/ui/checkbox";
import { QuestionChoice } from "@/interfaces/Question";
import { getCSRFToken } from "./HiddenCSRFTokenInput";
import axios from "axios";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";

interface QuestionCreationDialogProps {
  questionsUpdateCallback: () => void;
}

const QuestionCreationDialog: React.FC<QuestionCreationDialogProps> = ({
  questionsUpdateCallback,
}) => {
  const [open, setOpen] = React.useState<boolean>(false);
  const [choices, setChoices] = React.useState<QuestionChoice[]>([]);
  const [statement, setStatement] = React.useState<string>("");
  const [selectedSingle, setSelectedSingle] = React.useState(true);
  const [selectedRadio, setSelectedRadio] = React.useState<number | null>(null);
  const { toast } = useToast();

  const onOpenChange = (open: boolean) => {
    if (!open) {
      setChoices([]);
      setStatement("");
    }
    setOpen(open);
  };

  const handleRadioClick = () => {
    setSelectedSingle(true);
    setSelectedRadio(null);
    setChoices((prevChoices) =>
      prevChoices.map((choice) => ({ ...choice, is_correct: false }))
    );
  };

  const handleCheckboxChange = (checked: boolean) => {
    setSelectedSingle(!checked);
    setChoices((prevChoices) =>
      prevChoices.map((choice) => ({ ...choice, is_correct: false }))
    );
  };

  const createQuestion = async () => {
    const headers: any = {
      "X-CSRFToken": getCSRFToken(),
    };
    const question: any = {
      statement: statement,
      score: 1.0,
      choices: choices,
      is_multiple_choice: !selectedSingle,
    };
    try {
      await axios.post("/api/questions/", question, {
        headers: headers,
      });
      toast({
        title: "Pregunta creada",
        description: <p>Pregunta añadida con éxito.</p>,
      });
      questionsUpdateCallback();
      setOpen(false);
    } catch (e: any) {
      toast({
        title: "Error al crear la pregunta",
        description: (
          <p>
            {e.response?.data?.non_field_errors?.[0] ||
              "Ocurrió un error inesperado."}
          </p>
        ),
      });
      console.log(e);
    }
  };

  const addNewChoice = () => {
    setChoices((choices) => [
      ...choices,
      {
        answer_option: "",
        is_correct: false,
        id: 0,
      },
    ]);
  };

  const setChecked = (index: number, checked: boolean) => {
    if (selectedSingle) {
      setSelectedRadio(index);
      setChoices((choices) =>
        choices.map((choice, i) => ({
          ...choice,
          is_correct: i === index,
        }))
      );
    } else {
      setChoices((choices) => {
        const updatedChoices = [...choices];
        updatedChoices[index].is_correct = checked;
        return updatedChoices;
      });
    }
  };

  const setText = (index: number, content: string) => {
    let currentChoices = [...choices];
    currentChoices[index].answer_option = content;
    setChoices(currentChoices);
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogTrigger asChild>
        <Button className="inline-flex items-center" variant="secondary">
          <Plus className="mr-2" />
          Añadir pregunta
        </Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-[500px]">
        <DialogHeader>
          <DialogTitle>Añadir pregunta</DialogTitle>
          <DialogDescription>
            Introduce los datos de la nueva pregunta.
          </DialogDescription>
        </DialogHeader>
        <div className="grid gap-4 py-4">
          <div className="grid grid-cols-5 items-center gap-4 mb-4">
            <Label htmlFor="statement" className="col-span-2">
              Texto de la pregunta
            </Label>
            <Input
              id="statement"
              name="statement"
              type="text"
              className="col-span-3"
              value={statement}
              onChange={(event) => setStatement(event.target.value)}
            />
            <div className="flex flex-row col-span-5 justify-center flex-wrap gap-4 mt-2">
              <div>
                <RadioGroup>
                  <div className="flex items-center space-x-2">
                    <RadioGroupItem
                      value="option-one"
                      id="option-one"
                      checked={selectedSingle}
                      onClick={handleRadioClick}
                    />
                    <Label htmlFor="option-one">Respuesta Única</Label>
                  </div>
                </RadioGroup>
              </div>
              <div className="flex items-center space-x-2">
                <Checkbox
                  id="terms"
                  checked={!selectedSingle}
                  onCheckedChange={handleCheckboxChange}
                />
                <label
                  htmlFor="terms"
                  className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                >
                  Respuesta Múltiple
                </label>
              </div>
            </div>
            {choices.map((_choice, index) => (
              <div className="col-span-5 space-x-3 flex flex-row items-center">
                {selectedSingle ? (
                  <RadioGroup defaultValue="comfortable">
                    <RadioGroupItem
                      value="option-one"
                      id={index.toString()}
                      checked={selectedRadio === index}
                      onClick={() => setChecked(index, true)}
                    />
                  </RadioGroup>
                ) : (
                  <Checkbox
                    id={index.toString()}
                    onCheckedChange={(checked) =>
                      setChecked(index, checked.valueOf() as boolean)
                    }
                  />
                )}
                <label
                  htmlFor={index.toString()}
                  className="text-sm font-medium col-span-1 peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                ></label>
                <Input
                  id={index.toString()}
                  name={index.toString()}
                  type="text"
                  className="cols-span-4"
                  onChange={(event) => setText(index, event.target.value)}
                />
              </div>
            ))}
            <Button
              className="col-span-5 mt-2"
              variant="secondary"
              onClick={addNewChoice}
            >
              Nueva opción de respuesta
            </Button>
          </div>
        </div>
        <DialogFooter>
          <Button
            type="submit"
            onClick={createQuestion}
            disabled={
              choices.length == 0 ||
              !choices.some((choice) => choice.is_correct) ||
              !choices.every((choice) => choice.answer_option) ||
              !statement
            }
          >
            Añadir
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default QuestionCreationDialog;
