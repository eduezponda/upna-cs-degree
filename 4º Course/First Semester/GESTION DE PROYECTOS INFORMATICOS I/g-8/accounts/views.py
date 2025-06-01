# accounts/views.py
from django.views import View
from django.core.mail import send_mail
from django.shortcuts import redirect, render
from django.conf import settings
from .models import OTP
from django.contrib.auth import login
from django.contrib import messages
from django.urls import reverse


class Initiate2FAView(View):
    def get(self, request, *args, **kwargs):
        user = request.user

        if user.is_authenticated and user.two_factor_enabled:
            if not request.session.get("otp_verified", False):
                self.send_otp_email(user)
                return redirect("accounts:verify_2fa")
            else:
                return redirect("main")
        return redirect(reverse("login"))

    def send_otp_email(self, user):
        otp = OTP.generate_code(user)
        message = f"Your OTP code is {otp.code}. It will expire in {settings.OTP_EXPIRATION_TIME // 60} minutes."
        send_mail(
            "Your OTP Code",
            message,
            settings.EMAIL_HOST_USER,
            [user.email],
            fail_silently=False,
        )


class VerifyOTPView(View):
    def get(self, request, *args, **kwargs):
        user = request.user
        if user.is_authenticated and user.two_factor_enabled:
            if not request.session.get("otp_verified", False):
                return render(request, "verify_otp.html")
            else:
                return redirect("main")
        return redirect(reverse("login"))

    def post(self, request, *args, **kwargs):
        user = request.user
        otp_code = request.POST.get("otp")

        otp = OTP.objects.filter(user=user).order_by("-created_at").first()

        if otp and otp.code == otp_code and not otp.is_expired():
            request.session["otp_verified"] = True
            login(request, user)
            return redirect("main")
        else:
            messages.error(request, "Invalid OTP. Please try again.")
            return redirect(reverse("accounts:verify_2fa"))
