import { useState } from "react";
import { useTags } from "../hooks/useTags";
import { createTag, updateTag, deleteTag } from "../api/tags";

import Button from "../components/ui/Button";
import { useToast } from "../components/ui/Toast";
import ArticleModal from "../components/articles/ArticleModal";
import TagForm from "../components/tags/TagForm";

const TagsPage = () => {
  const { data: tags = [], refetch } = useTags();
  const { show } = useToast();

  const [modalOpen, setModalOpen] = useState(false);
  const [editing, setEditing] = useState(null);

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
      show("Tag deleted");
      refetch();
    } catch {
      show("Delete failed");
    }
  };

  return (
    <div className="articles-container">
      <div className="articles-header">
        <div className="page-title">
          <h1>Tags</h1>
          <span>Create and manage tags</span>
        </div>

        <Button onClick={() => {
          setEditing(null);
          setModalOpen(true);
        }}>
          + Create Tag
        </Button>
      </div>

      <div className="articles-list">
        {tags.map(tag => (
          <div key={tag.id} className="list-row">
            <div className="list-main">
              <div className="list-title">#{tag.name}</div>
              <div className="list-summary">
                {tag.description || "No description"}
              </div>
            </div>

            <div className="list-actions">
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