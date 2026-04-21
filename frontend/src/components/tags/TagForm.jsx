import { useState, useEffect } from "react";
import Button from "../ui/Button";

const TagForm = ({ onSubmit, initialData }) => {
  const [form, setForm] = useState({
    name: "",
    description: ""
  });

  useEffect(() => {
    if (initialData) setForm(initialData);
  }, [initialData]);

  const change = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = () => {
    if (!form.name.trim()) return alert("Name required");

    onSubmit({
      name: form.name.trim(),
      description: form.description
    });
  };

  return (
    <div className="form">
      <h2>{initialData ? "Edit Tag" : "Create Tag"}</h2>

      <input
        name="name"
        placeholder="Tag name"
        value={form.name}
        onChange={change}
      />

      <textarea
        name="description"
        placeholder="Description"
        value={form.description}
        onChange={change}
      />

      <Button onClick={handleSubmit}>
        {initialData ? "Update" : "Create"}
      </Button>
    </div>
  );
};

export default TagForm;