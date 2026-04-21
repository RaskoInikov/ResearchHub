const ArticleModal = ({ open, onClose, children }) => {
  if (!open) return null;

  return (
    <div className="modal-overlay">
      <div className="modal">
        {children}

        <div className="modal-footer">
          <button onClick={onClose}>Close</button>
        </div>
      </div>
    </div>
  );
};

export default ArticleModal;