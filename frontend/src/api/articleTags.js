import API from "./index";

export const updateArticleTags = (articleId, tagIds) =>
  API.put(`/article-tags/${articleId}`, tagIds);