import { clsx, type ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";
import { Exam } from "@/interfaces/Exam";
import axios from "axios";

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export function formatDate(input: string | number): string {
  const date = new Date(input);
  return date.toLocaleDateString("en-US", {
    month: "long",
    day: "numeric",
    year: "numeric",
  });
}

export function absoluteUrl(path: string) {
  return `${process.env.NEXT_PUBLIC_APP_URL}${path}`;
}

export async function fetchExamsData() {
  let nextUrl = "/api/exams/";
  let newExams: Exam[] = [];
  while (nextUrl) {
    const response = await axios.get(nextUrl);
    const fetchedExams = response.data.results;
    fetchedExams.forEach((exam: any) => {
      const dateTimeString = exam.start_date
        .toUpperCase()
        .replace("T", " ")
        .replace("Z", "");
      exam.start_date = new Date(dateTimeString);
    });
    newExams = newExams.concat(fetchedExams);
    nextUrl = response.data.next;
  }
  return newExams;
}
