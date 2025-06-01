import React from "react";
import {
  ColumnDef,
  SortingState,
  flexRender,
  getCoreRowModel,
  getFilteredRowModel,
  getPaginationRowModel,
  getSortedRowModel,
  useReactTable,
} from "@tanstack/react-table";
import { ArrowUpDown } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import axios from "axios";
import { User, UserGroup, getUserGroupName } from "@/interfaces/User";
import UserCreationDialog from "./UserCreationDialog";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";
import { Trash2 } from "lucide-react";
import { useToast } from "@/hooks/use-toast";

const columns = (
  availableUserCallback: () => void,
  toast: ReturnType<typeof useToast>["toast"]
): ColumnDef<User>[] => {
  return [
    {
      accessorKey: "groups",
      header: "Tipo de usuario",
      cell: ({ row }) => (
        <div className="capitalize">
          {getUserGroupName(row.getValue("groups") as UserGroup[])}
        </div>
      ),
    },
    {
      accessorKey: "email",
      header: ({ column }) => (
        <Button
          variant="ghost"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          Dirección de correo
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </Button>
      ),
      cell: ({ row }) => (
        <div className="lowercase">{row.getValue("email")}</div>
      ),
    },
    {
      id: "actions",
      cell: ({ row }) => {
        const user = row.original;

        return (
          <AlertDialog>
            <AlertDialogTrigger asChild>
              <div className="flex justify-end">
                <Button
                  className="hover:text-red-500"
                  variant="ghost"
                  size="icon"
                >
                  <Trash2 className="h-4 w-4" />
                </Button>
              </div>
            </AlertDialogTrigger>
            <AlertDialogContent>
              <AlertDialogHeader>
                <AlertDialogTitle>¿Estás seguro?</AlertDialogTitle>
                <AlertDialogDescription>
                  ¿Estás seguro de que quieres borrar el usuario {user.email}?
                  Esta acción es irreversible.
                </AlertDialogDescription>
              </AlertDialogHeader>
              <AlertDialogFooter>
                <AlertDialogCancel>Cancelar</AlertDialogCancel>
                <AlertDialogAction
                  onClick={async () => {
                    let csrfCookie = document.cookie
                      .split("; ")
                      .find((row) => row.startsWith("csrftoken="));

                    csrfCookie = csrfCookie?.split("=")[1] || "";

                    try {
                      await axios.delete(`/api/users/${user.id}/`, {
                        headers: { "X-CSRFToken": csrfCookie },
                      });
                      toast({
                        title: "Usuario Eliminado",
                        description: (
                          <p>El usuario {user.email} ha sido eliminado.</p>
                        ),
                      });
                      availableUserCallback();
                    } catch (exception) {
                      toast({
                        title: "Error",
                        description: "No se ha podido eliminar el usuario.",
                      });
                      console.log(exception);
                    }
                  }}
                >
                  Confirmar
                </AlertDialogAction>
              </AlertDialogFooter>
            </AlertDialogContent>
          </AlertDialog>
        );
      },
    },
  ];
};

const UsersTable: React.FC = () => {
  const [sorting, setSorting] = React.useState<SortingState>([]);
  const [users, setUsers] = React.useState<User[]>([]);

  const fetchUserData = async () => {
    let nextUrl = "/api/users/";
    let newUsers: User[] = [];
    while (nextUrl) {
      const response = await axios.get(nextUrl);
      const fetchedUsers = response.data.results;
      newUsers = newUsers.concat(fetchedUsers);
      nextUrl = response.data.next;
    }
    setUsers(newUsers);
  };

  React.useEffect(() => {
    fetchUserData();
  }, []);

  const { toast } = useToast();
  const table = useReactTable({
    data: users,
    columns: columns(fetchUserData, toast),
    onSortingChange: setSorting,
    getCoreRowModel: getCoreRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
    getSortedRowModel: getSortedRowModel(),
    getFilteredRowModel: getFilteredRowModel(),
    state: {
      sorting,
    },
  });

  return (
    <div className="w-full">
      <div className="flex items-center py-4 justify-between">
        <Input
          placeholder="Buscar direcciones..."
          value={(table.getColumn("email")?.getFilterValue() as string) ?? ""}
          onChange={(event) =>
            table.getColumn("email")?.setFilterValue(event.target.value)
          }
          className="max-w-sm"
        />
        <UserCreationDialog usersUpdateCallback={fetchUserData} />
      </div>
      <div className="rounded-md border">
        <Table>
          <TableHeader>
            {table.getHeaderGroups().map((headerGroup) => (
              <TableRow key={headerGroup.id}>
                {headerGroup.headers.map((header) => {
                  return (
                    <TableHead key={header.id}>
                      {header.isPlaceholder
                        ? null
                        : flexRender(
                            header.column.columnDef.header,
                            header.getContext()
                          )}
                    </TableHead>
                  );
                })}
              </TableRow>
            ))}
          </TableHeader>
          <TableBody>
            {table.getRowModel().rows?.length ? (
              table.getRowModel().rows.map((row) => (
                <TableRow key={row.id}>
                  {row.getVisibleCells().map((cell) => (
                    <TableCell key={cell.id}>
                      {flexRender(
                        cell.column.columnDef.cell,
                        cell.getContext()
                      )}
                    </TableCell>
                  ))}
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell
                  colSpan={columns.length}
                  className="h-24 text-center"
                >
                  No hay usuarios.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>
      <div className="flex items-center justify-end space-x-2 py-4">
        <div className="space-x-2">
          <Button
            variant="outline"
            size="sm"
            onClick={() => table.previousPage()}
            disabled={!table.getCanPreviousPage()}
          >
            Anterior
          </Button>
          <Button
            variant="outline"
            size="sm"
            onClick={() => table.nextPage()}
            disabled={!table.getCanNextPage()}
          >
            Siguiente
          </Button>
        </div>
      </div>
    </div>
  );
};

export default UsersTable;
