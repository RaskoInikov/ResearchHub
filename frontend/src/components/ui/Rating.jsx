const Rating = ({ value = 0, onChange }) => {
  return (
    <div className="rating">
      {[1, 2, 3, 4, 5].map(n => (
        <span
          key={n}
          className={n <= value ? "active" : ""}
          onClick={() => onChange(n)}
        >
          ★
        </span>
      ))}
    </div>
  );
};

export default Rating;