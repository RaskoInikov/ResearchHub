import { useEffect, useState } from "react";
import { getCommentsByArticle } from "../../../api/comments";
import { getReviewsByArticle } from "../../../api/reviews";

const ArticleRelations = ({ articleId }) => {
  const [comments, setComments] = useState([]);
  const [reviews, setReviews] = useState([]);

  useEffect(() => {
    const load = async () => {
      const [c, r] = await Promise.all([
        getCommentsByArticle(articleId),
        getReviewsByArticle(articleId),
      ]);

      setComments(c.data);
      setReviews(r.data);
    };

    load();
  }, [articleId]);

  return (
    <div style={{ padding: 10, background: "#fafafa" }}>
      <h4>Comments</h4>
      {comments.length === 0 && <p>No comments</p>}
      {comments.map((c) => (
        <div key={c.id}>
          {c.text}
        </div>
      ))}

      <h4 style={{ marginTop: 10 }}>Reviews</h4>
      {reviews.length === 0 && <p>No reviews</p>}
      {reviews.map((r) => (
        <div key={r.id}>
          ⭐ {r.score} — {r.comment}
        </div>
      ))}
    </div>
  );
};

export default ArticleRelations;