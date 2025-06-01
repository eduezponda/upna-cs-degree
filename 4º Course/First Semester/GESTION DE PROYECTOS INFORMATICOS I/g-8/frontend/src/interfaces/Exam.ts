export interface Exam {
  id: number;
  exam_name: string;
  start_date: Date;
  duration_minutes: number;
  status: "pending" | "active" | "finished";
  questions: number[];
  grade: number | null;
}
