import { useState } from "react";
import Button from "../ui/Button";

const UserForm = ({ onSubmit }) => {
  const [form, setForm] = useState({
    username: "",
    email: "",
    password: ""
  });

  const change = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = () => {
    // ===== FRONTEND VALIDATION =====
    if (!form.username.trim()) return alert("Username required");
    if (!form.email.includes("@")) return alert("Valid email required");
    if (form.password.length < 6) return alert("Password must be at least 6 characters");

    onSubmit({
      username: form.username.trim(),
      email: form.email.trim(),
      password: form.password
    });
  };

  return (
    <div className="form">
      <h2>Create User</h2>

      <input
        name="username"
        placeholder="Username"
        value={form.username}
        onChange={change}
      />

      <input
        name="email"
        placeholder="Email"
        value={form.email}
        onChange={change}
      />

      <input
        name="password"
        placeholder="Password (min 6 chars)"
        type="password"
        value={form.password}
        onChange={change}
      />

      <Button onClick={handleSubmit}>
        Create
      </Button>
    </div>
  );
};

export default UserForm;