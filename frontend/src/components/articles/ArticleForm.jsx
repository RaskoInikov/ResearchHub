import { useState, useEffect } from "react";
import { useUsers } from "../../hooks/useUsers";
import Button from "../ui/Button";

const ArticleForm = ({ onSubmit, initialData }) => {
  const { data: users = [] } = useUsers();

  const [form, setForm] = useState({
    title: "",
    abstractText: "",
    content: "",
    summary: "",
    authorId: "",
    status: "DRAFT",
  });

  useEffect(() => {
    if (initialData) {
      setForm(initialData);
    }
  }, [initialData]);

  const change = e =>
    setForm({ ...form, [e.target.name]: e.target.value });

  return (
    <div className="form">
      <h2>{initialData ? "Edit Article" : "Create Article"}</h2>

      <input name="title" placeholder="Title" value={form.title} onChange={change} />
      <textarea name="abstractText" placeholder="Abstract" value={form.abstractText} onChange={change} />
      <textarea name="content" placeholder="Content" value={form.content} onChange={change} />
      <textarea name="summary" placeholder="Summary" value={form.summary} onChange={change} />

      <select name="authorId" value={form.authorId} onChange={change}>
        <option value="">Select Author</option>
        {users.map(u => (
          <option key={u.id} value={u.id}>{u.username}</option>
        ))}
      </select>

      <select name="status" value={form.status} onChange={change}>
        <option>DRAFT</option>
        <option>PUBLISHED</option>
      </select>

      <Button onClick={() => onSubmit(form)}>
        {initialData ? "Update" : "Create"}
      </Button>
    </div>
  );
};

export default ArticleForm;