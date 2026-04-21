import API from "./index";

export const getCommentsByArticle = (articleId) =>
  API.get("/comments/by-article", { params: { articleId } });