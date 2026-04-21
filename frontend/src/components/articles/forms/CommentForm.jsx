import { useState } from "react";
import API from "../../../api/index";

const CommentForm = ({ articleId, onSuccess }) => {
  const [text, setText] = useState("");
  const [authorId, setAuthorId] = useState("");

  const submit = async () => {
    await API.post("/comments", { text, articleId, authorId });
    setText("");
    onSuccess();
  };

  return (
    <div>
      <input placeholder="Comment" value={text} onChange={e => setText(e.target.value)} />
      <input placeholder="Author ID" value={authorId} onChange={e => setAuthorId(e.target.value)} />
      <button onClick={submit}>Add</button>
    </div>
  );
};

export default CommentForm;