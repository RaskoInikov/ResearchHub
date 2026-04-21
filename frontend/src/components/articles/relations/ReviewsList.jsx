import { useEffect, useState } from "react";
import { getReviewsByArticle } from "../../../api/reviews";

const ReviewsList = ({ articleId }) => {
  const [reviews, setReviews] = useState([]);

  useEffect(() => {
    const load = async () => {
      const res = await getReviewsByArticle(articleId);
      setReviews(res.data);
    };

    load();
  }, [articleId]);

  return (
    <div>
      <h4>Reviews</h4>
      {reviews.map((r) => (
        <div key={r.id}>
          <p>Score: {r.score}</p>
          <p>{r.comment}</p>
        </div>
      ))}
    </div>
  );
};

export default ReviewsList;