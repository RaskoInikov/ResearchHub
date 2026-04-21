import { useEffect, useState } from "react";
import { getCommentsByArticle } from "../../../api/comments";

const CommentsList = ({ articleId }) => {
  const [comments, setComments] = useState([]);

  useEffect(() => {
    const load = async () => {
      const res = await getCommentsByArticle(articleId);
      setComments(res.data);
    };

    load();
  }, [articleId]);

  return (
    <div>
      <h4>Comments</h4>
      {comments.map((c) => (
        <div key={c.id} style={{ borderBottom: "1px solid #ccc" }}>
          <p>{c.text}</p>
          <small>Author: {c.authorId}</small>
        </div>
      ))}
    </div>
  );
};

export default CommentsList;