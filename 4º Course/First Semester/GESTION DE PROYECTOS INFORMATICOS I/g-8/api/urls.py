from django.urls import include, path
from rest_framework import routers
from . import views

router = routers.DefaultRouter()
router.register(r"users", views.QuizForgeUserViewSet)
router.register(r"exams", views.QuizForgeExamViewSet)
router.register(r"questions", views.QuizForgeExamQuestionViewSet)
router.register(r"submissions", views.QuizForgeExamQuestionAnswerSubmissionViewSet)
router.register(r'question-stats', views.QuizForgeQuestionStatsViewSet, basename='quizforgequestionstats')

urlpatterns = [
    path("", include(router.urls)),
]
