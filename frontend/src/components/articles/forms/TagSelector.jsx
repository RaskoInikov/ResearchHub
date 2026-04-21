import { useTags } from "../../../hooks/useTags";

const TagSelector = ({ selected, setSelected }) => {
  const { data: tags = [] } = useTags();

  const toggle = (id) => {
    if (selected.includes(id)) {
      setSelected(selected.filter(t => t !== id));
    } else {
      setSelected([...selected, id]);
    }
  };

  return (
    <div>
      <h4>Tags</h4>
      {tags.map(t => (
        <label key={t.id} style={{ marginRight: "10px" }}>
          <input
            type="checkbox"
            checked={selected.includes(t.id)}
            onChange={() => toggle(t.id)}
          />
          {t.name}
        </label>
      ))}
    </div>
  );
};

export default TagSelector;