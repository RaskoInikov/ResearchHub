import API from "./index";

export const getReviewsByArticle = (articleId) =>
  API.get("/reviews/by-article", { params: { articleId } });

// SAFE VERSION (strict JSON)
export const rateArticle = ({ articleId, score, reviewerId }) =>
  API.post("/reviews", {
    articleId: String(articleId),
    reviewerId: String(reviewerId),
    score: Number(score),
    comment: ""
  });