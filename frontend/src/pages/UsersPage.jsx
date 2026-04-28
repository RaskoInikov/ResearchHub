import { useState } from "react";
import { useUsers } from "../hooks/useUsers";
import { useAllArticles } from "../hooks/useAllArticles";
import { createUser, deleteUser } from "../api/users";

import Button from "../components/ui/Button";
import { useToast } from "../components/ui/Toast";
import ArticleModal from "../components/articles/ArticleModal";
import UserForm from "../components/users/UserForm";

const UsersPage = () => {
  const { data: users = [], refetch } = useUsers();
  const { data: articles = [] } = useAllArticles();

  const { show } = useToast();

  const [selected, setSelected] = useState(null);
  const [modalOpen, setModalOpen] = useState(false);

  // ===== GROUP ARTICLES BY USER =====
  const articlesByUser = (userId) =>
    articles.filter(a => a.authorId === userId);

  // ===== ACTIONS =====
  const handleCreate = async (data) => {
    try {
      await createUser(data);
      show("User created");
      setModalOpen(false);
      refetch();
    } catch (e) {
      console.error("CREATE USER ERROR:", e.response?.data);
      show(e.response?.data?.message || "Create failed");
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteUser(id);
      show("User deleted");
      if (selected?.id === id) setSelected(null);
      refetch();
    } catch (e) {
      console.error("DELETE ERROR:", e.response?.data);

      if (e.response?.data?.message?.includes("foreign key")) {
        show("User cannot be deleted (has articles)");
      } else {
        show("Delete failed");
      }
    }
  };

  return (
    <div className="articles-container">
      {/* HEADER */}
      <div className="articles-header">
        <div className="page-title">
          <h1>Users</h1>
          <span>Manage users and their articles</span>
        </div>

        <Button onClick={() => setModalOpen(true)}>
          + Create User
        </Button>
      </div>

      <div className="articles-layout list-view">
        {/* USERS LIST */}
        <div className="articles-list">
          {users.map(user => (
            <div
              key={user.id}
              className={`list-row ${selected?.id === user.id ? "active" : ""}`}
              onClick={() => setSelected(user)}
            >
              <div className="list-main">
                <div className="list-title">{user.username}</div>
                <div className="list-summary">
                  {user.email} • {articlesByUser(user.id).length} articles
                </div>
              </div>

              <div className="list-meta">
                <span className="status">
                  {user.isActive ? "ACTIVE" : "INACTIVE"}
                </span>
              </div>

              <div
                className="list-actions"
                onClick={(e) => e.stopPropagation()}
              >
                <Button
                  variant="danger"
                  onClick={() => {
                    const userArticles = articlesByUser(user.id);

                    if (userArticles.length > 0) {
                      show("Cannot delete user with existing articles");
                      return;
                    }

                    handleDelete(user.id);
                  }}
                >
                  Delete
                </Button>
              </div>
            </div>
          ))}
        </div>

        {/* DETAILS */}
        {selected && (
          <div className="details-panel compact">
            <div className="details-header">
              <h2>{selected.username}</h2>
              <Button className="close-btn" onClick={() => setSelected(null)}>✕</Button>
            </div>

            <div className="details-block">
              <label>Email</label>
              <p>{selected.email}</p>
            </div>

            <div className="details-block">
              <label>Status</label>
              <span className="status">
                {selected.isActive ? "ACTIVE" : "INACTIVE"}
              </span>
            </div>

            <div className="details-block">
              <label>Articles</label>

              {articlesByUser(selected.id).length === 0 && (
                <p style={{ color: "#888" }}>No articles</p>
              )}

              <div className="user-articles">
                {articlesByUser(selected.id).map(a => (
                  <div key={a.id} className="user-article">
                    <strong>{a.title}</strong>
                    <span className="status">{a.status}</span>
                  </div>
                ))}
              </div>
            </div>
          </div>
        )}
      </div>

      {/* MODAL */}
      <ArticleModal open={modalOpen} onClose={() => setModalOpen(false)}>
        <UserForm onSubmit={handleCreate} />
      </ArticleModal>
    </div>
  );
};

export default UsersPage;