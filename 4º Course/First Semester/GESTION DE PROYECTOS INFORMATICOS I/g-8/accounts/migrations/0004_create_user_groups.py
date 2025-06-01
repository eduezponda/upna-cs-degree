from django.db import migrations
from django.contrib.auth.management import create_permissions


def create_user_groups(apps, schema_editor):
    groups_permissions = {
        "admin": [
            "view_quizforgeuser",
            "add_quizforgeuser",
            "add_quizforgeexam",
            "add_quizforgeexamquestion",
            "assign_exam",
            "view_quizforgeexam",
            "delete_quizforgeuser",
            "delete_quizforgeexam",
            "delete_quizforgeexamquestion",
        ],
        "professor": [
            "view_quizforgeuser",
            "view_quizforgeexam",
            "add_quizforgeexam",
            "change_quizforgeexam",
            "view_quizforgeexamquestion",
            "add_quizforgeexamquestion",
            "change_quizforgeexamquestion",
            "assign_exam",
            "delete_quizforgeexam",
            "delete_quizforgeexamquestion",
        ],
        "student": [
            "view_quizforgeexam",
            "view_quizforgeexamquestion",
            "add_quizforgeexamquestionanswersubmission",
        ],
    }
    # Model permissions are not created until the migration is completed, so we
    # "force" the creation of the default permissions to assign them to user
    # groups.
    for app_config in apps.get_app_configs():
        app_config.models_module = True
        create_permissions(app_config, apps=apps, verbosity=0)
        app_config.models_module = None
    Permission = apps.get_model("auth", "Permission")
    Group = apps.get_model("auth", "Group")
    for group_name, permissions_list in groups_permissions.items():
        group, _ = Group.objects.get_or_create(name=group_name)
        group.permissions.set(
            [
                Permission.objects.get(codename=permission)
                for permission in permissions_list
            ]
        )


class Migration(migrations.Migration):

    dependencies = [
        ("accounts", "0003_quizforgeuser_delete_user"),
        ("exams", "0006_quizforgeexamquestionanswersubmission"),
    ]

    operations = [migrations.RunPython(create_user_groups)]
