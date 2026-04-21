import API from "./index";

// GET paginated + filtered
export const getArticles = (params) =>
  API.get("/articles/search", { params });

// GET by id
export const getArticleById = (id) =>
  API.get(`/articles/${id}`);

// CREATE
export const createArticle = (data) =>
  API.post("/articles", data);

// UPDATE
export const updateArticle = (id, data) =>
  API.put(`/articles/${id}`, data);

// DELETE
export const deleteArticle = (id) =>
  API.delete(`/articles/${id}`);

// SEARCH by title
export const searchByTitle = (title) =>
  API.get("/articles/by-title", { params: { title } });