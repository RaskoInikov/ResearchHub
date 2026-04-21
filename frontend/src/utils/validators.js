export const validateArticle = (data) => {
  if (!data.title) return "Title required";
  if (!data.content) return "Content required";
  if (!data.authorId) return "Author required";
  return null;
};