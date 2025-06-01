import * as React from "react";
import { Label } from "@/components/ui/label";
import { TimePickerInput } from "./ui/time-picker-input";

interface TimePickerDemoProps {
  time: Date | undefined;
  setTime: (time: Date | undefined) => void;
}

const HourMinuteTimePicker: React.FC<TimePickerDemoProps> = ({
  time: date,
  setTime,
}) => {
  const minuteRef = React.useRef<HTMLInputElement>(null);
  const hourRef = React.useRef<HTMLInputElement>(null);

  return (
    <div className="flex items-end gap-2">
      <div className="grid gap-1 text-center">
        <Label htmlFor="hours" className="text-xs">
          Hora
        </Label>
        <TimePickerInput
          picker="hours"
          date={date}
          setDate={setTime}
          ref={hourRef}
          onRightFocus={() => minuteRef.current?.focus()}
        />
      </div>
      <div className="grid gap-1 text-center">
        <Label htmlFor="minutes" className="text-xs">
          Minuto
        </Label>
        <TimePickerInput
          picker="minutes"
          date={date}
          setDate={setTime}
          ref={minuteRef}
          onLeftFocus={() => hourRef.current?.focus()}
        />
      </div>
    </div>
  );
};

export default HourMinuteTimePicker;
