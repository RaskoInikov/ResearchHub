import { useState, useMemo } from "react";
import { useTags } from "../hooks/useTags";
import { useAllArticles } from "../hooks/useAllArticles";
import { createTag, updateTag, deleteTag } from "../api/tags";
import { updateArticleTags } from "../api/articleTags";

import Button from "../components/ui/Button";
import { useToast } from "../components/ui/Toast";
import ArticleModal from "../components/articles/ArticleModal";
import TagForm from "../components/tags/TagForm";

const TagsPage = () => {
  const { data: tags = [], refetch } = useTags();
  const { data: rawArticles = [], refetch: refetchArticles } = useAllArticles();

  const { show } = useToast();

  const [articles, setArticles] = useState([]);

  const [selected, setSelected] = useState(null);
  const [modalOpen, setModalOpen] = useState(false);
  const [editing, setEditing] = useState(null);

  useMemo(() => {
    if (rawArticles.length) {
      setArticles(prev =>
        rawArticles.map(a => {
          const existing = prev.find(p => p.id === a.id);
          return existing || { ...a, tagIds: [] };
        })
      );
    }
  }, [rawArticles]);

  // ===== RELATION =====
  const articlesByTag = (tagId) =>
    articles.filter(a => a.tagIds.includes(tagId));

  // ===== TOGGLE =====
  const toggleArticleTag = async (article, tagId) => {
    try {
      const exists = article.tagIds.includes(tagId);

      const updated = exists
        ? article.tagIds.filter(id => id !== tagId)
        : [...article.tagIds, tagId];

      await updateArticleTags(article.id, updated);

      // 🔥 UPDATE LOCAL STATE
      setArticles(prev =>
        prev.map(a =>
          a.id === article.id ? { ...a, tagIds: updated } : a
        )
      );

      show("Updated");
    } catch (e) {
      console.error(e.response?.data);
      show("Update failed");
    }
  };

  // ===== CRUD =====
  const handleCreate = async (data) => {
    try {
      await createTag(data);
      show("Tag created");
      setModalOpen(false);
      refetch();
    } catch {
      show("Create failed");
    }
  };

  const handleUpdate = async (data) => {
    try {
      await updateTag(editing.id, data);
      show("Tag updated");
      setEditing(null);
      setModalOpen(false);
      refetch();
    } catch {
      show("Update failed");
    }
  };

  const handleDelete = async (tagId) => {
    try {
      await deleteTag(tagId);

      // 🔥 REMOVE FROM ALL ARTICLES
      setArticles(prev =>
        prev.map(a => ({
          ...a,
          tagIds: a.tagIds.filter(id => id !== tagId)
        }))
      );

      show("Tag deleted");
      setSelected(null);
      refetch();
    } catch (e) {
      console.error(e.response?.data);
      show("Delete failed");
    }
  };

  return (
    <div className="articles-container">
      {/* HEADER */}
      <div className="articles-header">
        <div className="page-title">
          <h1>Tags</h1>
          <span>Group and manage article tags</span>
        </div>

        <Button onClick={() => {
          setEditing(null);
          setModalOpen(true);
        }}>
          + Create Tag
        </Button>
      </div>

      <div className="articles-layout list-view">
        {/* TAG LIST */}
        <div className="articles-list">
          {tags.map(tag => (
            <div
              key={tag.id}
              className={`list-row ${selected?.id === tag.id ? "active" : ""}`}
              onClick={() => setSelected(tag)}
            >
              <div className="list-main">
                <div className="list-title">#{tag.name}</div>
                <div className="list-summary">
                  {tag.description || "No description"}
                </div>
              </div>

              <div className="list-meta">
                <span className="status">
                  {articlesByTag(tag.id).length} articles
                </span>
              </div>

              <div
                className="list-actions"
                onClick={(e) => e.stopPropagation()}
              >
                <Button
                  variant="secondary"
                  onClick={() => {
                    setEditing(tag);
                    setModalOpen(true);
                  }}
                >
                  Edit
                </Button>

                <Button
                  variant="danger"
                  onClick={() => handleDelete(tag.id)}
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
              <h2>#{selected.name}</h2>
              <Button onClick={() => setSelected(null)}>✕</Button>
            </div>

            <div className="details-block">
              <label>Articles (toggle)</label>

              <div className="tag-articles">
                {articles.map(a => {
                  const hasTag = a.tagIds.includes(selected.id);

                  return (
                    <div
                      key={a.id}
                      className={`tag-article ${hasTag ? "active" : ""}`}
                      onClick={() => toggleArticleTag(a, selected.id)}
                    >
                      <strong>{a.title}</strong>
                      <span>{hasTag ? "✓" : "+"}</span>
                    </div>
                  );
                })}
              </div>
            </div>
          </div>
        )}
      </div>

      {/* MODAL */}
      <ArticleModal open={modalOpen} onClose={() => setModalOpen(false)}>
        <TagForm
          initialData={editing}
          onSubmit={editing ? handleUpdate : handleCreate}
        />
      </ArticleModal>
    </div>
  );
};

export default TagsPage;