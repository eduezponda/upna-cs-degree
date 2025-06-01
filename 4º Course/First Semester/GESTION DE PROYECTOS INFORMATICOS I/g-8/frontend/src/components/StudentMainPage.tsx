import React, { useEffect } from "react";
import { Exam } from "@/interfaces/Exam";
import ExamCard from "./ExamCard";
import { fetchExamsData } from "@/lib/utils";
import StudentExamTakingDialog from "./StudentExamTakingDialog";

const StudentMainPage: React.FC = () => {
  const [exams, setExams] = React.useState<Exam[]>([]);
  const sortedExams = exams.sort((a, b) => {
    if (a.status === b.status) return 0;
    if (a.status === "active") return -1;
    if (b.status === "active") return 1;
    if (a.status === "pending") return -1;
    if (b.status === "pending") return 1;
    return 0;
  });

  const fetchExamsDataAndSet = async () => {
    const exams = await fetchExamsData();
    setExams(exams);
  };

  useEffect(() => {
    fetchExamsDataAndSet();
  }, []);

  return (
    <>
      <h2 className="scroll-m-20 border-b pb-2 text-3xl font-semibold tracking-tight first:mt-0">
        Exámenes asignados
      </h2>
      <div className="mt-3">
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-2">
          {sortedExams.map((exam) => (
            <ExamCard exam={exam} professor={false}>
              <StudentExamTakingDialog exam={exam} />
            </ExamCard>
          ))}
        </div>
      </div>
    </>
  );
};

export default StudentMainPage;
