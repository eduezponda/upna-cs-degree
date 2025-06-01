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
import React, { FormEvent } from "react";
import axios from "axios";
import { HiddenCSRFTokenInput } from "./HiddenCSRFTokenInput";
import DatePicker from "./DatePicker";
import HourMinuteTimePicker from "./HourMinuteTimePicker";

interface ExamCreationDialogProps {
  examsUpdateCallback: () => void;
}

const ExamCreationDialog: React.FC<ExamCreationDialogProps> = ({
  examsUpdateCallback,
}) => {
  const [open, setOpen] = React.useState<boolean>(false);
  const [examDate, setExamDate] = React.useState<Date>();
  const [examTime, setExamTime] = React.useState<Date>();
  const [examName, setExamName] = React.useState<string>("");
  const { toast } = useToast();

  // When scheduling events for the future, saving them as UTC is not the best
  // option since the user will assume a fixed local time. We thus store exam
  // start times as "YYYY-MM-DD hh:mm:ss", in local (CET) time.
  const formatDateTime = (date: Date, time: Date): string => {
    return (
      [
        date.getFullYear(),
        (date.getMonth() + 1).toString().padStart(2, "0"),
        date.getDate().toString().padStart(2, "0"),
      ].join("-") +
      " " +
      [
        time.getHours().toString().padStart(2, "0"),
        time.getMinutes().toString().padStart(2, "0"),
        "00",
      ].join(":")
    );
  };

  const handleFormSubmit = async (event: FormEvent) => {
    event.preventDefault();
    if (!examDate || !examTime) return;
    const formData = new FormData(event.target as HTMLFormElement);
    setOpen(false);
    const dateTime = formatDateTime(examDate, examTime);
    const duration = formData.get("duration");
    const examData = {
      exam_name: examName,
      start_date: dateTime,
      duration_minutes: duration,
      questions: [],
    };
    const headers: any = {
      "X-CSRFToken": formData.get("csrfmiddlewaretoken"),
    };
    try {
      await axios.post("/api/exams/", examData, {
        headers: headers,
      });
      toast({
        title: "Examen creado",
        description: (
          <p>
            El examen <strong>{examName}</strong> se ha creado con éxito.
          </p>
        ),
      });
      examsUpdateCallback();
    } catch (exception) {
      toast({
        title: "Error",
        description: "No se ha podido crear el examen.",
      });
      console.log(exception);
    }
  };

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button className="inline-flex items-center">
          <Plus className="mr-2" />
          Añadir examen
        </Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-[500px]">
        <form id="user-creation-form" onSubmit={handleFormSubmit}>
          <HiddenCSRFTokenInput />
          <DialogHeader>
            <DialogTitle>Añadir examen</DialogTitle>
            <DialogDescription>
              Introduce los datos del nuevo examen.
            </DialogDescription>
          </DialogHeader>
          <div className="grid gap-4 py-4">
            <div className="grid grid-cols-5 items-center gap-4 mb-4">
              <Label htmlFor="name" className="col-span-2">
                Nombre del examen
              </Label>
              <Input
                id="name"
                name="name"
                type="text"
                placeholder="Nombre del examen"
                className="col-span-3"
                value={examName}
                onChange={(event) => setExamName(event.target.value)}
              />
              <Label className="col-span-2">Fecha</Label>
              <div className="col-span-3">
                <DatePicker date={examDate} setDate={setExamDate} />
              </div>
              <Label className="col-span-2">Hora</Label>
              <div className="col-span-3">
                <HourMinuteTimePicker time={examTime} setTime={setExamTime} />
              </div>
              <Label htmlFor="duration" className="col-span-2">
                Duración del examen en minutos
              </Label>
              <Input
                id="duration"
                name="duration"
                type="number"
                placeholder="60"
                className="col-span-3"
              />
            </div>
          </div>
          <DialogFooter>
            <Button 
            type="submit"
            disabled={
              !examName
            }
            >
              Añadir
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
};

export default ExamCreationDialog;
