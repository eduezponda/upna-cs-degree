export enum UserGroupName {
  Student = "student",
  Professor = "professor",
  Administrator = "admin",
}

export interface UserGroup {
  name: UserGroupName;
}

export interface User {
  id: string;
  email: string;
  groups: UserGroup[];
}

export const getUserGroupName = (groups: UserGroup[]): string => {
  if (groups.length === 0) return "Desconocido";
  const names: Record<UserGroupName, string> = {
    [UserGroupName.Student]: "Estudiante",
    [UserGroupName.Professor]: "Profesor",
    [UserGroupName.Administrator]: "Administrador",
  };
  return names[groups[0].name];
};
