import { useState, useEffect } from "react";
import { useArticles } from "../hooks/useArticles";
import {
  createArticle,
  updateArticle,
  deleteArticle
} from "../api/articles";
import { rateArticle } from "../api/reviews";

import {
  getCommentsByArticle,
  createComment,
  deleteComment
} from "../api/comments";

import Button from "../components/ui/Button";
import Rating from "../components/ui/Rating";
import { useToast } from "../components/ui/Toast";

import ArticleForm from "../components/articles/ArticleForm";
import ArticleModal from "../components/articles/ArticleModal";

import { useTags } from "../hooks/useTags";
import { updateArticleTags } from "../api/articleTags";

const USER_ID = "ac897eb7-56d5-46da-a2de-32b45f999ad3";

const ArticlesPage = () => {
  const { data, refetch } = useArticles({});
  const rawArticles = data?.content || [];

  const { data: tags = [] } = useTags();
  const { show } = useToast();

  const [articles, setArticles] = useState([]);
  const [selected, setSelected] = useState(null);

  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState("");

  const [search, setSearch] = useState("");
  const [page, setPage] = useState(0);

  const [modalOpen, setModalOpen] = useState(false);
  const [editing, setEditing] = useState(null);

  const pageSize = 9;

  useEffect(() => {
    refetch();
  }, []);

  useEffect(() => {
    setArticles(rawArticles);

    if (selected) {
      const updated = rawArticles.find(a => a.id === selected.id);
      if (updated) setSelected(updated);
    }
  }, [rawArticles]);

  useEffect(() => {
    if (!selected) return;

    getCommentsByArticle(selected.id)
      .then(res => setComments(res.data))
      .catch(() => show("Failed to load comments"));
  }, [selected]);

  const filtered = articles.filter(a =>
    a.title.toLowerCase().includes(search.toLowerCase())
  );

  const paginated = filtered.slice(page * pageSize, (page + 1) * pageSize);

  const toggleTag = async (article, tagId) => {
    const current = article.tags?.map(t => t.id) || [];

    const updatedIds = current.includes(tagId)
      ? current.filter(id => id !== tagId)
      : [...current, tagId];

    const updatedTags = tags.filter(t => updatedIds.includes(t.id));

    setArticles(prev =>
      prev.map(a =>
        a.id === article.id ? { ...a, tags: updatedTags } : a
      )
    );

    setSelected(prev =>
      prev?.id === article.id ? { ...prev, tags: updatedTags } : prev
    );

    try {
      await updateArticleTags(article.id, updatedIds);

      await refetch();

      show("Tags updated");
    } catch {
      show("Tag update failed");
    }
  };

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
      if (selected?.id === id) setSelected(null);
      refetch();
    } catch {
      show("Delete failed");
    }
  };

  const handleRate = async (articleId, score) => {
    setArticles(prev =>
      prev.map(a =>
        a.id === articleId ? { ...a, rating: score } : a
      )
    );

    setSelected(prev =>
      prev?.id === articleId ? { ...prev, rating: score } : prev
    );

    try {
      await rateArticle({
        articleId: String(articleId),
        score: Number(score),
        reviewerId: USER_ID
      });

      await refetch();
      show("Rating saved");
    } catch {
      show("Rating failed");
    }
  };

  const handleAddComment = async () => {
    if (!newComment.trim()) return;

    try {
      const res = await createComment({
        text: newComment,
        articleId: selected.id,
        authorId: USER_ID
      });

      setComments(prev => [...prev, res.data]);
      setNewComment("");
      show("Comment added");
    } catch {
      show("Failed to add comment");
    }
  };

  const handleDeleteComment = async (id) => {
    try {
      await deleteComment(id);
      setComments(prev => prev.filter(c => c.id !== id));
      show("Comment deleted");
    } catch {
      show("Failed to delete comment");
    }
  };

  const shouldScrollComments = comments.length > 2;

  return (
    <div className="articles-container no-scroll">

      {/* HEADER */}
      <div className="articles-header improved">
        <div className="page-title">
          <h1>Articles</h1>
          <span>Manage and explore research content</span>
        </div>

        <div className="actions improved">
          <input
            className="search-input"
            placeholder="Search articles..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />

          <Button
            className="create-btn"
            onClick={() => {
              setEditing(null);
              setModalOpen(true);
            }}
          >
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
          <div className="details-panel compact fixed">

            <div className="details-header">
              <h2>{selected.title}</h2>

              <button
                className="close-btn"
                onClick={() => setSelected(null)}
              >
                ✕
              </button>
            </div>

            {/* TAGS WITH SCROLL */}
            <div className="details-block">
              <label>Tags</label>

              <div
                className="tag-articles"
                style={{ maxHeight: "120px", overflowY: "auto" }}
              >
                {tags.map(tag => {
                  const has = selected.tags?.some(t => t.id === tag.id);

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

            {/* COMMENTS */}
            <div className="details-block">
              <label>Comments</label>

              <div
                className="comments"
                style={
                  shouldScrollComments
                    ? { maxHeight: "180px", overflowY: "auto" }
                    : {}
                }
              >
                {comments.map(c => (
                  <div key={c.id} className="comment">
                    <span>{c.text}</span>

                    <button
                      className="delete-comment-btn"
                      onClick={() => handleDeleteComment(c.id)}
                    >
                      ✕
                    </button>
                  </div>
                ))}
              </div>

              <div className="comment-input">
                <input
                  placeholder="Write a comment..."
                  value={newComment}
                  onChange={(e) => setNewComment(e.target.value)}
                />
                <Button onClick={handleAddComment}>Add</Button>
              </div>
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
          ←
        </Button>

        <span>Page {page + 1}</span>

        <Button
          variant="ghost"
          disabled={(page + 1) * pageSize >= filtered.length}
          onClick={() => setPage(p => p + 1)}
        >
          →
        </Button>
      </div>

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