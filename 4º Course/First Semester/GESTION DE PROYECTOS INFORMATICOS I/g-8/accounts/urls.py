# accounts/urls.py
from django.urls import path
from .views import Initiate2FAView, VerifyOTPView

app_name = "accounts"

urlpatterns = [
    path("initiate_2fa/", Initiate2FAView.as_view(), name="initiate_2fa"),
    path("verify_otp/", VerifyOTPView.as_view(), name="verify_2fa"),
]
