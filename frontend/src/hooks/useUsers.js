import { useQuery } from "@tanstack/react-query";
import { getUsers } from "../api/users";

export const useUsers = () =>
  useQuery({
    queryKey: ["users"],
    queryFn: () => getUsers().then(res => res.data),
  });