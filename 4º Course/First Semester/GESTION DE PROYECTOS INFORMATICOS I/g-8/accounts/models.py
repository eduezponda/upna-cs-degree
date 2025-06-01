from django.db import models
from django.contrib.auth.models import AbstractUser
from .managers import QuizForgeUsersManager
from django.utils import timezone
from django.conf import settings
import random


class QuizForgeUser(AbstractUser):
    username = first_name = last_name = None
    USERNAME_FIELD = EMAIL_FIELD = "email"
    REQUIRED_FIELDS = []
    email = models.EmailField(unique=True)
    two_factor_enabled = models.BooleanField(default=True)

    objects = QuizForgeUsersManager()  # type: ignore

    def __str__(self) -> str:
        return self.email


class OTP(models.Model):
    user = models.ForeignKey(
        QuizForgeUser, on_delete=models.CASCADE, related_name="otp_codes"
    )
    code = models.CharField(max_length=6)
    created_at = models.DateTimeField(auto_now_add=True)
    expires_at = models.DateTimeField()

    def is_expired(self):
        """Check if the OTP has expired."""
        return timezone.now() > self.expires_at

    @classmethod
    def generate_code(cls, user):
        """Generate and save a new OTP code for a given user."""
        code = random.randint(100000, 999999)
        expires_at = timezone.now() + timezone.timedelta(
            seconds=settings.OTP_EXPIRATION_TIME
        )
        otp = cls.objects.create(user=user, code=str(code), expires_at=expires_at)
        return otp

    def __str__(self):
        return f"OTP for {self.user.username} - Code: {self.code} - Expires at: {self.expires_at}"
