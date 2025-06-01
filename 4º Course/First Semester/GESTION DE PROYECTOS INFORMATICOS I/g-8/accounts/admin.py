from django.contrib import admin
from django.contrib.auth.admin import UserAdmin
from .forms import QuizForgeUserCreationForm, QuizForgeUserChangeForm
from .models import QuizForgeUser


class QuizForgeUserAdmin(UserAdmin):
    model = QuizForgeUser
    add_form = QuizForgeUserCreationForm
    form = QuizForgeUserChangeForm
    ordering = ("email",)
    list_display = (
        "email",
        "date_joined",
        "is_staff",
        "is_active",
    )
    fieldsets = ((None, {"fields": ("email", "password", "groups")}),)
    add_fieldsets = ((None, {"fields": ("email", "password1", "password2", "groups")}),)


admin.site.register(QuizForgeUser, QuizForgeUserAdmin)
