export interface QuestionChoice {
  id: number;
  answer_option: string;
  is_correct: boolean;
}

export interface Question {
  id: number;
  statement: string;
  score: number;
  choices: QuestionChoice[];
  is_multiple_choice: boolean;
  stats?: QuestionStats;
}

export interface QuestionStats {
  question_id: number;
  total_attempts: number;
  correct_attempts: number;
  failure_ratio: number;
}
