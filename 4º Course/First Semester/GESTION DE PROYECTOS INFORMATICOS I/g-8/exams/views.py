from django.contrib.auth.decorators import login_required
from django.shortcuts import redirect, render


@login_required
def main_view(request):
    user = request.user

    # If the user has 2FA enabled and hasn't passed the OTP verification, redirect to the 2FA page
    if user.two_factor_enabled and not request.session.get("otp_verified", False):
        return redirect("accounts:initiate_2fa")  # Redirect to OTP initiation page

    group_names = list(user.groups.values_list("name", flat=True))
    group = "student" if not group_names else group_names[0]
    pages_per_group = {
        "student": "student.html",
        "professor": "professor.html",
        "admin": "admin.html",
    }
    return render(request, pages_per_group[group])
