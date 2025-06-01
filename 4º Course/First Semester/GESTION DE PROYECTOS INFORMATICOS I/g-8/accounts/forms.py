from django.contrib.auth.forms import UserCreationForm, UserChangeForm
from .models import QuizForgeUser


class QuizForgeUserCreationForm(UserCreationForm):
    class Meta:
        model = QuizForgeUser
        fields = ["email", "password"]


class QuizForgeUserChangeForm(UserChangeForm):
    class Meta:
        model = QuizForgeUser
        fields = ["email", "password"]
