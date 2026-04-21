import { useQuery } from "@tanstack/react-query";
import { getArticles } from "../api/articles";

export const useAllArticles = () =>
  useQuery({
    queryKey: ["all-articles"],
    queryFn: () =>
      getArticles({ size: 1000 }).then(res => res.data.content),
  });