import React, { useEffect } from "react";
import { Exam } from "@/interfaces/Exam";
import ExamCreationDialog from "@/components/ExamCreationDialog";
import ExamQuestionAddingDialog from "@/components/ExamQuestionAddingDialog";
import AssignStudentsToExamDialog from "@/components/AssignStudentsToExamDialog";
import QuestionCreationDialog from "./QuestionCreationDialog";
import ExamCard from "@/components/ExamCard";
import { fetchExamsData } from "@/lib/utils";
import { Question, QuestionStats } from "@/interfaces/Question";
import axios from "axios";
import ExamGradesSummary from "./ExamGradesSummary";

interface StudentsExams {
  exam: Exam;
  students_exams: Exam[];
}

const ProfessorMainPage: React.FC = () => {
  const [exams, setExams] = React.useState<Exam[]>([]);
  const [availableQuestions, setAvailableQuestions] = React.useState<
    Question[]
  >([]);
  const [examGrades, setExamGrades] = React.useState<StudentsExams[]>([]);

  const fetchExamsDataAndSet = async () => {
    const exams = await fetchExamsData();
    setExams(exams);
  };

  const fetchExamGradesData = async () => {
    try {
      const promises = exams.map(async (exam) => {
        const response = await fetch(`/api/exams/${exam.id}/students-grades/`);
        if (!response.ok) {
          if (response.status === 404) {
            console.warn(`Exam ${exam.id} has no assigned students.`);
            return { exam, students_exams: [] };
          } else if (response.status === 400) {
            console.warn(`Exam ${exam.id} is not finished yet.`);
            return { exam, students_exams: [] };
          }
          throw new Error(`Error fetching data for exam ${exam.id}`);
        }
        const data = await response.json();
        return {
          exam,
          students_exams: data,
        };
      });

      const results = await Promise.all(promises);

      setExamGrades(results);
    } catch (error) {
      console.error("Error fetching exam grades:", error);
    }
  };

  const fetchQuestionsDataAndSet = async () => {
    const fetchQuestionStats = async (
      questionId: number
    ): Promise<QuestionStats | null> => {
      try {
        const response = await axios.get(`/api/question-stats/${questionId}/`);
        return response.data;
      } catch (error) {
        console.error("Error fetching question stats", error);
        return null;
      }
    };

    let nextUrl = "/api/questions/";
    let newQuestions: Question[] = [];
    while (nextUrl) {
      const response = await axios.get(nextUrl);
      const fetchedQuestions = response.data.results;
      newQuestions = newQuestions.concat(fetchedQuestions);
      nextUrl = response.data.next;
    }
    for (let question of newQuestions) {
      const stats = await fetchQuestionStats(question.id);
      if (stats) question.stats = stats;
    }
    setAvailableQuestions(newQuestions);
  };

  const renderExamStatusComponent = (exam: Exam, students_exams: Exam[]) => {
    switch (exam.status) {
      case "finished":
        return (
          <ExamGradesSummary exam={exam} studentsGrades={students_exams} />
        );
      case "pending":
        return (
          <>
            <ExamQuestionAddingDialog
              exam={exam}
              availableQuestions={availableQuestions}
              updateCallback={fetchQuestionsDataAndSet}
            />
            <AssignStudentsToExamDialog exam={exam} />
          </>
        );
      case "active":
        return <AssignStudentsToExamDialog exam={exam} />;
      default:
        return null;
    }
  };

  useEffect(() => {
    fetchExamsDataAndSet();
    fetchQuestionsDataAndSet();
  }, []);

  useEffect(() => {
    fetchExamGradesData();
  }, [exams]);

  return (
    <>
      <div className="flex flex-row flex-wrap justify-between">
        <h2 className="scroll-m-20 border-b pb-2 text-3xl font-semibold tracking-tight first:mt-0">
          Exámenes programados
        </h2>
        <div className="flex flex-row flex-wrap gap-2 mt-2 justify-left md:justify-end">
          <ExamCreationDialog examsUpdateCallback={fetchExamsDataAndSet} />
          <QuestionCreationDialog
            questionsUpdateCallback={fetchQuestionsDataAndSet}
          />
        </div>
      </div>
      <div className="mt-3">
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-2">
          {examGrades.map(({ exam, students_exams }) => (
            <ExamCard
              exam={exam}
              examsUpdateCallback={fetchExamsDataAndSet}
              professor={true}
            >
              {renderExamStatusComponent(exam, students_exams)}
            </ExamCard>
          ))}
        </div>
      </div>
    </>
  );
};

export default ProfessorMainPage;
