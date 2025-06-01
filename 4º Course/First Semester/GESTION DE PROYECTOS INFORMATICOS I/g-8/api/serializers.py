from django.contrib.auth.models import Group
from accounts.models import QuizForgeUser
from exams.models import (
    QuizForgeExamQuestionChoice,
    QuizForgeExamQuestion,
    QuizForgeExam,
    QuizForgeExamStatus,
    QuizForgeExamQuestionAnswerSubmission,
)
from rest_framework import serializers


class GroupSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Group
        fields = ["name"]
        extra_kwargs = {
            "name": {"validators": []},
        }


class QuizForgeUserSerializer(serializers.HyperlinkedModelSerializer):
    groups = GroupSerializer(many=True)

    class Meta:
        model = QuizForgeUser
        fields = ["id", "url", "email", "password", "groups"]
        extra_kwargs = {
            "password": {"write_only": True, "required": True},
            "email": {"required": True},
            "groups": {"required": True},
        }

    def create(self, validated_data):
        group_names = [group["name"] for group in validated_data.pop("groups")]
        groups = [Group.objects.get(name=group_name) for group_name in group_names]
        user = QuizForgeUser.objects.create_user(**validated_data)
        user.groups.set(groups)
        return user


class QuizForgeExamQuestionChoiceSerializer(serializers.ModelSerializer):
    class Meta:
        model = QuizForgeExamQuestionChoice
        fields = ["id", "answer_option", "is_correct"]


class QuizForgeExamQuestionSerializer(serializers.ModelSerializer):
    choices = QuizForgeExamQuestionChoiceSerializer(many=True)

    class Meta:
        model = QuizForgeExamQuestion
        fields = ["id", "statement", "score", "choices", "is_multiple_choice"]

    def validate(self, data):
        is_multiple_choice = data.get("is_multiple_choice")
        choices = data.get("choices", [])

        correct_choices_count = sum(1 for choice in choices if choice.get("is_correct"))

        if is_multiple_choice:
            if correct_choices_count == 0:
                raise serializers.ValidationError(
                    "Debe haber al menos una respuesta correcta."
                )
        else:
            if correct_choices_count != 1:
                raise serializers.ValidationError(
                    "Las preguntas de respuesta única, deben tener como máximo una respuesta correcta."
                )

        return data

    def create(self, validated_data):
        choices_data = validated_data.pop("choices")
        question = QuizForgeExamQuestion.objects.create(**validated_data)
        for choice_data in choices_data:
            QuizForgeExamQuestionChoice.objects.create(question=question, **choice_data)
        return question


class QuizForgeExamSerializer(serializers.ModelSerializer):
    # Question's IDs
    questions = serializers.SerializerMethodField()
    assigned_students = serializers.PrimaryKeyRelatedField(
        queryset=QuizForgeUser.objects.all().filter(groups__name="student"),
        many=True,
        required=False,
    )
    status = serializers.SerializerMethodField()
    grade = serializers.SerializerMethodField()

    class Meta:
        model = QuizForgeExam
        fields = [
            "id",
            "exam_name",
            "start_date",
            "duration_minutes",
            "questions",
            "created_by",
            "assigned_students",
            "status",
            "grade",
        ]

    def create(self, validated_data):
        validated_data["created_by"] = self.context["request"].user
        exam = QuizForgeExam.objects.create(**validated_data)
        exam.questions.set([])
        return exam

    def get_status(self, exam: QuizForgeExam):
        return exam.status

    def get_questions(self, exam: QuizForgeExam):
        if self.context["request"].user.groups.filter(name="student").exists():
            if exam.status != QuizForgeExamStatus.ACTIVE:
                return []
        return exam.questions.values_list("id", flat=True)

    def get_grade(self, exam: QuizForgeExam):
        user = self.context.get("student", self.context["request"].user)
        if (
            not user.groups.filter(name="student").exists()
            or exam.status != QuizForgeExamStatus.FINISHED
        ):
            return None
        return exam.calculate_grade(user)


class QuizForgeExamQuestionAnswerSubmissionSerializer(serializers.ModelSerializer):
    exam = serializers.PrimaryKeyRelatedField(queryset=QuizForgeExam.objects.all())
    student = serializers.PrimaryKeyRelatedField(
        read_only=True,
    )
    question = serializers.PrimaryKeyRelatedField(
        queryset=QuizForgeExamQuestion.objects.all()
    )
    choice = serializers.PrimaryKeyRelatedField(
        queryset=QuizForgeExamQuestionChoice.objects.all()
    )

    class Meta:
        model = QuizForgeExamQuestionAnswerSubmission
        fields = ["exam", "student", "question", "choice"]

    def create(self, validated_data):
        exam = validated_data.get("exam", None)
        if exam is None or exam.status != QuizForgeExamStatus.ACTIVE:
            raise serializers.ValidationError("Exam is not currently active")
        request = self.context.get("request", None)
        student = request.user if request is not None else None
        answer, created = QuizForgeExamQuestionAnswerSubmission.objects.get_or_create(
            exam=validated_data.get("exam", None),
            student=student,
            question=validated_data.get("question", None),
        )
        answer.choice = validated_data.get("choice")
        answer.save()
        return answer


class QuizForgeQuestionStatsSerializer(serializers.Serializer):
    def to_representation(self, instance):
        submissions = QuizForgeExamQuestionAnswerSubmission.objects.filter(
            question=instance
        )
        submissions = submissions.order_by("-id")

        total_attempts = submissions.count()
        correct_attempts = submissions.filter(choice__is_correct=True).count()

        failure_ratio = (
            (total_attempts - correct_attempts) / total_attempts
            if total_attempts > 0
            else 0.0
        )

        return {
            "question_id": instance.id,
            "total_attempts": total_attempts,
            "correct_attempts": correct_attempts,
            "failure_ratio": failure_ratio,
        }
