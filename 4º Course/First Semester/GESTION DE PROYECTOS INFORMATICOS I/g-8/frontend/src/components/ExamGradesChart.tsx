import { Bar, BarChart, CartesianGrid, Rectangle, XAxis } from "recharts";
import { TrendingUp } from "lucide-react";
import { Exam } from "@/interfaces/Exam";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  ChartConfig,
  ChartContainer,
  ChartTooltip,
  ChartTooltipContent,
} from "@/components/ui/chart";

const isMobile = window.innerWidth < 768;

const baseChartData = [
  { grade: "suspenso", fill: "var(--color-suspenso)" },
  { grade: "aprobado", fill: "var(--color-aprobado)" },
  { grade: "bien", fill: "var(--color-bien)" },
  { grade: "notable", fill: "var(--color-notable)" },
  { grade: "sobresaliente", fill: "var(--color-sobresaliente)" },
];

export interface StudentsData {
  suspenso: number;
  aprobado: number;
  bien: number;
  notable: number;
  sobresaliente: number;
}

interface BarChartProps {
  studentsData: StudentsData;
  exam: Exam;
}

const ExamGradesChart: React.FC<BarChartProps> = ({ studentsData, exam }) => {
  const chartConfig = {
    students: {
      label: "Alumnos",
    },
    suspenso: {
      label: isMobile ? "S" : "Suspenso",
      color: "hsl(0, 70%, 50%)",
    },
    aprobado: {
      label: isMobile ? "A" : "Aprobado",
      color: "hsl(30, 80%, 55%)",
    },
    bien: {
      label: isMobile ? "B" : "Bien",
      color: "hsl(60, 80%, 60%)",
    },
    notable: {
      label: isMobile ? "N" : "Notable",
      color: "hsl(120, 60%, 50%)",
    },
    sobresaliente: {
      label: isMobile ? "SB" : "Sobresaliente",
      color: "hsl(240, 60%, 50%)",
    },
  } satisfies ChartConfig;

  const chartData = baseChartData.map((baseEntry) => ({
    ...baseEntry,
    students: studentsData[baseEntry.grade as keyof StudentsData] || 0,
  }));

  const totalStudents = chartData.reduce(
    (sum, entry) => sum + entry.students,
    0
  );
  const failedStudents = chartData.find((entry) => entry.grade === "suspenso");
  const outstandingStudents = chartData.find(
    (entry) => entry.grade === "sobresaliente"
  );
  const approvedPercentage = failedStudents
    ? (
        ((totalStudents - failedStudents.students) / totalStudents) *
        100
      ).toFixed(2)
    : "0.00";
  const outstandingPercentage = outstandingStudents
    ? ((outstandingStudents.students / totalStudents) * 100).toFixed(2)
    : "0.00";

  return (
    <Card>
      <CardHeader>
        <CardTitle>Resultados del examen "{exam.exam_name}"</CardTitle>
        <CardDescription>Participación total de estudiantes: {totalStudents}</CardDescription>
      </CardHeader>
      <CardContent>
        <ChartContainer config={chartConfig}>
          <BarChart accessibilityLayer data={chartData}>
            <CartesianGrid vertical={false} />
            <XAxis
              dataKey="grade"
              tickLine={false}
              tickMargin={10}
              axisLine={false}
              tickFormatter={(value) =>
                chartConfig[value as keyof typeof chartConfig]?.label
              }
            />
            <ChartTooltip
              cursor={false}
              content={<ChartTooltipContent hideLabel />}
            />
            <Bar
              dataKey="students"
              strokeWidth={2}
              radius={8}
              activeIndex={2}
              activeBar={({ ...props }) => {
                return (
                  <Rectangle
                    {...props}
                    fillOpacity={0.8}
                    stroke={props.payload.fill}
                    strokeDasharray={4}
                    strokeDashoffset={4}
                  />
                );
              }}
            />
          </BarChart>
        </ChartContainer>
      </CardContent>
      <CardFooter className="flex-col items-start gap-2 text-sm">
        <div className="flex gap-2 font-medium leading-none">
          Aprobados: {approvedPercentage}% de los estudiantes  
          <TrendingUp className="h-4 w-4" />
        </div>
        <div className="leading-none text-muted-foreground">
          Sobresalientes: {outstandingPercentage}% de los estudiantes
        </div>
      </CardFooter>
    </Card>
  );
};

export default ExamGradesChart;
