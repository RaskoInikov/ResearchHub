import { useState } from "react";
import API from "../../../api/index";

const ReviewForm = ({ articleId, onSuccess }) => {
  const [score, setScore] = useState(5);
  const [comment, setComment] = useState("");
  const [reviewerId, setReviewerId] = useState("");

  const submit = async () => {
    await API.post("/reviews", { score, comment, articleId, reviewerId });
    onSuccess();
  };

  return (
    <div>
      <input type="number" value={score} onChange={e => setScore(e.target.value)} />
      <input value={comment} onChange={e => setComment(e.target.value)} />
      <input value={reviewerId} onChange={e => setReviewerId(e.target.value)} />
      <button onClick={submit}>Add Review</button>
    </div>
  );
};

export default ReviewForm;