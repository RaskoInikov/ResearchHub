import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { getArticles, createArticle, updateArticle, deleteArticle } from "../api/articles";

export const useArticles = (filters) => {
  return useQuery({
    queryKey: ["articles", filters],
    queryFn: () => getArticles(filters).then(res => res.data),
  });
};

export const useCreateArticle = () => {
  const qc = useQueryClient();

  return useMutation({
    mutationFn: createArticle,
    onSuccess: () => qc.invalidateQueries(["articles"]),
  });
};

export const useUpdateArticle = () => {
  const qc = useQueryClient();

  return useMutation({
    mutationFn: ({ id, data }) => updateArticle(id, data),
    onSuccess: () => qc.invalidateQueries(["articles"]),
  });
};

export const useDeleteArticle = () => {
  const qc = useQueryClient();

  return useMutation({
    mutationFn: deleteArticle,
    onSuccess: () => qc.invalidateQueries(["articles"]),
  });
};