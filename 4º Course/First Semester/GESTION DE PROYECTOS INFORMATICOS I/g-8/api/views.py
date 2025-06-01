from django.shortcuts import render
from rest_framework import viewsets, status
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework.permissions import DjangoModelPermissions, BasePermission
from accounts.models import QuizForgeUser
from exams.models import (
    QuizForgeExamQuestion,
    QuizForgeExam,
    QuizForgeExamQuestionAnswerSubmission,
    QuizForgeExamStatus,
)
from .serializers import (
    QuizForgeUserSerializer,
    QuizForgeExamQuestionSerializer,
    QuizForgeExamSerializer,
    QuizForgeExamQuestionAnswerSubmissionSerializer,
    QuizForgeQuestionStatsSerializer,
)


class ViewAccessRequiredModelPermissions(DjangoModelPermissions):
    perms_map = {
        **DjangoModelPermissions.perms_map,
        "GET": ["%(app_label)s.view_%(model_name)s"],
    }


class AssignStudentsToExamsPermission(BasePermission):
    def has_permission(self, request, view):
        return request.user.has_perm("exams.assign_exam")


class QuizForgeUserViewSet(viewsets.ModelViewSet):
    permission_classes = [ViewAccessRequiredModelPermissions]
    queryset = QuizForgeUser.objects.all().order_by("-date_joined")
    serializer_class = QuizForgeUserSerializer

    def get_queryset(self):
        # Non-admin users can only query student users.
        queryset = super().get_queryset()
        if not self.request.user.groups.filter(name="admin").exists():
            queryset = queryset.filter(groups__name="student")
        return queryset


class QuizForgeExamQuestionViewSet(viewsets.ModelViewSet):
    permission_classes = [ViewAccessRequiredModelPermissions]
    queryset = QuizForgeExamQuestion.objects.all()
    serializer_class = QuizForgeExamQuestionSerializer

    def get_serializer_context(self):
        context = super().get_serializer_context()
        context.update({"user": self.request.user})
        return context


class QuizForgeExamViewSet(viewsets.ModelViewSet):
    permission_classes = [ViewAccessRequiredModelPermissions]
    queryset = QuizForgeExam.objects.all()
    serializer_class = QuizForgeExamSerializer

    def get_queryset(self):  # type: ignore
        queryset = super().get_queryset().order_by("id")
        user_groups = self.request.user.groups
        if user_groups.filter(name="professor").exists():
            return queryset.filter(created_by=self.request.user)
        if user_groups.filter(name="student").exists():
            return queryset.filter(assigned_students=self.request.user)

    @action(
        detail=True,
        methods=["get"],
        url_path="students-grades",
        permission_classes=[ViewAccessRequiredModelPermissions],
    )
    def students_grades(self, request, pk=None):
        exam = self.get_object()

        if not request.user.groups.filter(name="professor").exists():
            return Response(
                {"detail": "No permissions to access this information"},
                status=status.HTTP_403_FORBIDDEN,
            )

        if exam.status != QuizForgeExamStatus.FINISHED:
            return Response(
                {"detail": "Grades are only available for completed exams"},
                status=status.HTTP_400_BAD_REQUEST,
            )

        assigned_students = exam.assigned_students.all()
        if not assigned_students.exists():
            return Response(
                {"detail": f"No students assigned to this exam: {assigned_students}"},
                status=status.HTTP_404_NOT_FOUND,
            )

        students_exams_data = []
        for student in assigned_students:
            exam_data = QuizForgeExamSerializer(
                exam, context={"request": request, "student": student}
            ).data
            students_exams_data.append(exam_data)

        return Response(students_exams_data, status=status.HTTP_200_OK)

    @action(
        detail=True,
        methods=["post"],
        url_path="add-questions",
        permission_classes=[ViewAccessRequiredModelPermissions],
    )
    def add_questions(self, request, pk=None):
        exam = self.get_object()
        question_ids = request.data.get("question_ids", [])

        questions = QuizForgeExamQuestion.objects.filter(id__in=question_ids)
        if not questions.exists():
            return Response(
                {"detail": "No valid questions provided"},
                status=status.HTTP_400_BAD_REQUEST,
            )

        exam.questions.add(*questions)
        return Response(
            {"detail": "Questions added to exam"}, status=status.HTTP_200_OK
        )

    @action(
        detail=True,
        methods=["post"],
        url_path="remove-questions",
        permission_classes=[ViewAccessRequiredModelPermissions],
    )
    def remove_questions(self, request, pk=None):
        exam = self.get_object()
        question_ids = request.data.get("question_ids", [])

        questions = QuizForgeExamQuestion.objects.filter(id__in=question_ids)
        if not questions.exists():
            return Response(
                {"detail": "No valid questions provided"},
                status=status.HTTP_400_BAD_REQUEST,
            )

        exam.questions.remove(*questions)
        return Response(
            {"detail": "Questions removed from exam"}, status=status.HTTP_200_OK
        )

    @action(
        detail=True,
        methods=["post"],
        url_path="assign-students",
        permission_classes=[
            ViewAccessRequiredModelPermissions & AssignStudentsToExamsPermission
        ],
    )
    def assign_students(self, request, pk=None):
        exam = self.get_object()
        student_user_ids = request.data.get("student_user_ids", [])
        students = QuizForgeUser.objects.filter(id__in=student_user_ids).filter(
            groups__name="student"
        )

        if not students.exists():
            return Response(
                {"detail": "No valid student user IDs provided"},
                status=status.HTTP_400_BAD_REQUEST,
            )

        exam.assigned_students.add(*students)
        return Response(
            {"detail": "Exam assigned to students"}, status=status.HTTP_200_OK
        )

    @action(
        detail=True,
        methods=["post"],
        url_path="unassign-students",
        permission_classes=[
            ViewAccessRequiredModelPermissions & AssignStudentsToExamsPermission
        ],
    )
    def unassign_students(self, request, pk=None):
        exam = self.get_object()
        student_user_ids = request.data.get("student_user_ids", [])
        students = QuizForgeUser.objects.filter(id__in=student_user_ids).filter(
            groups__name="student"
        )

        if not students.exists():
            return Response(
                {"detail": "No valid student user IDs provided"},
                status=status.HTTP_400_BAD_REQUEST,
            )

        exam.assigned_students.remove(*students)
        return Response(
            {"detail": "Exam unassigned to students"}, status=status.HTTP_200_OK
        )


class QuizForgeExamQuestionAnswerSubmissionViewSet(viewsets.ModelViewSet):
    permission_classes = [ViewAccessRequiredModelPermissions]
    queryset = QuizForgeExamQuestionAnswerSubmission.objects.all()
    serializer_class = QuizForgeExamQuestionAnswerSubmissionSerializer


class QuizForgeQuestionStatsViewSet(viewsets.ReadOnlyModelViewSet):
    permission_classes = [ViewAccessRequiredModelPermissions]
    queryset = QuizForgeExamQuestion.objects.all()
    serializer_class = QuizForgeQuestionStatsSerializer

    def retrieve(self, request, *args, **kwargs):
        question_id = kwargs.get("pk")
        try:
            question = QuizForgeExamQuestion.objects.get(id=question_id)
        except QuizForgeExamQuestion.DoesNotExist:
            return Response(
                {"error": "Pregunta no encontrada"},
                status=status.HTTP_400_BAD_REQUEST,
            )

        serializer = QuizForgeQuestionStatsSerializer(question)
        stats = serializer.to_representation(question)
        return Response(stats)
