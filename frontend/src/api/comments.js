import API from "./index";

export const getCommentsByArticle = (articleId) =>
  API.get("/comments/by-article", { params: { articleId } });

export const createComment = (data) =>
  API.post("/comments", data);

export const deleteComment = (id) =>
  API.delete(`/comments/${id}`);