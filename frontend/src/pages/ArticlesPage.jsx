import { useState, useEffect } from "react";
import { useArticles } from "../hooks/useArticles";
import {
  createArticle,
  updateArticle,
  deleteArticle
} from "../api/articles";
import { rateArticle } from "../api/reviews";

import Button from "../components/ui/Button";
import Rating from "../components/ui/Rating";
import { useToast } from "../components/ui/Toast";

import ArticleForm from "../components/articles/ArticleForm";
import ArticleModal from "../components/articles/ArticleModal";

import { useTags } from "../hooks/useTags";
import { updateArticleTags } from "../api/articleTags";

const USER_ID = "ac897eb7-56d5-46da-a2de-32b45f999ad3";

const STORAGE_KEY = "article_tags_map";

const ArticlesPage = () => {
  const { data, refetch } = useArticles({});
  const rawArticles = data?.content || [];

  const { data: tags = [] } = useTags();
  const { show } = useToast();

  const [articles, setArticles] = useState([]);

  const [selected, setSelected] = useState(null);
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(0);

  const [modalOpen, setModalOpen] = useState(false);
  const [editing, setEditing] = useState(null);

  const pageSize = 8;

  // 🔥 LOAD TAGS FROM LOCAL STORAGE
  useEffect(() => {
    const saved = JSON.parse(localStorage.getItem(STORAGE_KEY) || "{}");

    const enriched = rawArticles.map(a => ({
      ...a,
      tagIds: saved[a.id] || []
    }));

    setArticles(enriched);
  }, [rawArticles]);

  const saveTags = (articleId, tagIds) => {
    const saved = JSON.parse(localStorage.getItem(STORAGE_KEY) || "{}");
    saved[articleId] = tagIds;
    localStorage.setItem(STORAGE_KEY, JSON.stringify(saved));
  };

  const filtered = articles.filter(a =>
    a.title.toLowerCase().includes(search.toLowerCase())
  );

  const paginated = filtered.slice(page * pageSize, (page + 1) * pageSize);

  const toggleTag = async (article, tagId) => {
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

      // 🔥 PERSIST
      saveTags(article.id, updated);

      show("Tags updated");
    } catch (e) {
      console.error(e.response?.data);
      show("Tag update failed");
    }
  };

  // ===== CRUD =====
  const handleCreate = async (data) => {
    try {
      await createArticle(data);
      show("Article created");
      setModalOpen(false);
      refetch();
    } catch {
      show("Create failed");
    }
  };

  const handleUpdate = async (data) => {
    try {
      await updateArticle(editing.id, data);
      show("Article updated");
      setEditing(null);
      setModalOpen(false);
      refetch();
    } catch {
      show("Update failed");
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteArticle(id);
      show("Article deleted");

      // 🔥 CLEAN STORAGE
      const saved = JSON.parse(localStorage.getItem(STORAGE_KEY) || "{}");
      delete saved[id];
      localStorage.setItem(STORAGE_KEY, JSON.stringify(saved));

      if (selected?.id === id) setSelected(null);
      refetch();
    } catch {
      show("Delete failed");
    }
  };

  const handleRate = async (articleId, score) => {
    try {
      await rateArticle({
        articleId: String(articleId),
        score: Number(score),
        reviewerId: USER_ID
      });

      show("Rating saved");
      refetch();
    } catch (e) {
      console.error(e.response?.data);
      show("Rating failed");
    }
  };

  return (
    <div className="articles-container">
      {/* HEADER */}
      <div className="articles-header">
        <div className="page-title">
          <h1>Articles</h1>
          <span>Manage and explore research content</span>
        </div>

        <div className="actions">
          <input
            placeholder="Search articles..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />

          <Button onClick={() => {
            setEditing(null);
            setModalOpen(true);
          }}>
            + Create
          </Button>
        </div>
      </div>

      <div className="articles-layout list-view">
        {/* LIST */}
        <div className="articles-list">
          {paginated.map(article => (
            <div
              key={article.id}
              className={`list-row ${selected?.id === article.id ? "active" : ""}`}
              onClick={() => setSelected(article)}
            >
              <div className="list-main">
                <div className="list-title">{article.title}</div>
                <div className="list-summary">
                  {article.summary?.slice(0, 80)}
                </div>
              </div>

              <div className="list-meta">
                <span className="status">{article.status}</span>

                <Rating
                  value={article.rating || 0}
                  onChange={(val) => handleRate(article.id, val)}
                />
              </div>
            </div>
          ))}
        </div>

        {/* DETAILS */}
        {selected && (
          <div className="details-panel compact">
            <div className="details-header">
              <h2>{selected.title}</h2>
              <Button onClick={() => setSelected(null)}>✕</Button>
            </div>

            <div className="details-block">
              <label>Tags</label>

              <div className="tag-articles">
                {tags.map(tag => {
                  const has = selected.tagIds?.includes(tag.id);

                  return (
                    <div
                      key={tag.id}
                      className={`tag-article ${has ? "active" : ""}`}
                      onClick={() => toggleTag(selected, tag.id)}
                    >
                      #{tag.name}
                    </div>
                  );
                })}
              </div>
            </div>

            <div className="details-block">
              <label>Summary</label>
              <p>{selected.summary}</p>
            </div>

            <div className="details-block">
              <label>Content</label>
              <p>{selected.content}</p>
            </div>

            <div className="details-actions">
              <Rating
                value={selected.rating || 0}
                onChange={(val) => handleRate(selected.id, val)}
              />

              <Button
                variant="secondary"
                onClick={() => {
                  setEditing(selected);
                  setModalOpen(true);
                }}
              >
                Edit
              </Button>

              <Button
                variant="danger"
                onClick={() => handleDelete(selected.id)}
              >
                Delete
              </Button>
            </div>
          </div>
        )}
      </div>

      {/* PAGINATION */}
      <div className="pagination sticky">
        <Button
          variant="ghost"
          disabled={page === 0}
          onClick={() => setPage(p => Math.max(0, p - 1))}
        >
          ← Previous
        </Button>

        <span>Page {page + 1}</span>

        <Button
          variant="ghost"
          disabled={(page + 1) * pageSize >= filtered.length}
          onClick={() => setPage(p => p + 1)}
        >
          Next →
        </Button>
      </div>

      {/* MODAL */}
      <ArticleModal open={modalOpen} onClose={() => setModalOpen(false)}>
        <ArticleForm
          initialData={editing}
          onSubmit={editing ? handleUpdate : handleCreate}
        />
      </ArticleModal>
    </div>
  );
};

export default ArticlesPage;