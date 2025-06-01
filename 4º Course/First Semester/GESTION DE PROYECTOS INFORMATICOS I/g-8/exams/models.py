from django.db import models
from enum import StrEnum
from accounts.models import QuizForgeUser
import datetime
import pytz


class QuizForgeExamStatus(StrEnum):
    PENDING = "pending"
    ACTIVE = "active"
    FINISHED = "finished"


class QuizForgeExam(models.Model):
    exam_name = models.CharField(max_length=512)
    start_date = models.DateTimeField()
    duration_minutes = models.IntegerField()
    questions = models.ManyToManyField("QuizForgeExamQuestion")
    created_by = models.ForeignKey(
        "accounts.QuizForgeUser", on_delete=models.CASCADE, null=True
    )
    assigned_students = models.ManyToManyField(
        "accounts.QuizForgeUser", related_name="assigned_students"
    )

    class Meta:
        permissions = [
            ("assign_exam", "Assign exam to student"),
        ]

    @property
    def status(self) -> QuizForgeExamStatus:
        timezone = pytz.timezone("Europe/Madrid")
        start_date = timezone.localize(self.start_date.replace(tzinfo=None))
        now = datetime.datetime.now(timezone)
        if now < start_date:
            return QuizForgeExamStatus.PENDING

        # Evitar desbordamiento
        MAX_DAYS = 365 * 30
        max_minutes = MAX_DAYS * 24 * 60

        if self.duration_minutes > max_minutes:
            self.duration_minutes = max_minutes

        end_datetime = start_date + datetime.timedelta(minutes=self.duration_minutes)
        if now < end_datetime:
            return QuizForgeExamStatus.ACTIVE
        return QuizForgeExamStatus.FINISHED

    def calculate_grade(self, student: QuizForgeUser) -> float:
        submissions = QuizForgeExamQuestionAnswerSubmission.objects.filter(
            exam=self, student=student
        )
        grade = sum(
            submission.question.score
            for submission in submissions
            if submission.choice is not None and submission.choice.is_correct
        )
        total = sum(
            QuizForgeExamQuestion.objects.get(id=question_id).score
            for question_id in self.questions.values_list("id", flat=True)
        )
        if total != 0:
            final_grade = (grade / total) * 10
            return round(final_grade, 1) if final_grade % 1 != 0 else int(final_grade)

        return 0


class QuizForgeExamQuestion(models.Model):
    statement = models.CharField(max_length=512)
    score = models.FloatField()
    is_multiple_choice = models.BooleanField(default=False)


class QuizForgeExamQuestionChoice(models.Model):
    answer_option = models.CharField(max_length=512)
    is_correct = models.BooleanField(default=False)
    question = models.ForeignKey(
        QuizForgeExamQuestion, related_name="choices", on_delete=models.CASCADE
    )


class QuizForgeExamQuestionAnswerSubmission(models.Model):
    exam = models.ForeignKey(
        QuizForgeExam, related_name="exam", on_delete=models.CASCADE
    )
    student = models.ForeignKey(
        QuizForgeUser, related_name="student", on_delete=models.CASCADE
    )
    question = models.ForeignKey(
        QuizForgeExamQuestion, related_name="question", on_delete=models.CASCADE
    )
    choice = models.ForeignKey(
        QuizForgeExamQuestionChoice,
        related_name="choice",
        on_delete=models.CASCADE,
        null=True,
    )

    class Meta:
        unique_together = ("exam", "student", "question")
