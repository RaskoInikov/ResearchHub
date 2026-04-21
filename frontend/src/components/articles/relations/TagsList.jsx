import { useEffect, useState } from "react";
import { getTags } from "../../../api/tags";

const TagsList = () => {
  const [tags, setTags] = useState([]);

  useEffect(() => {
    const load = async () => {
      const res = await getTags();
      setTags(res.data);
    };

    load();
  }, []);

  return (
    <div>
      <h4>Tags</h4>
      {tags.map((t) => (
        <span key={t.id} style={{ marginRight: "10px" }}>
          #{t.name}
        </span>
      ))}
    </div>
  );
};

export default TagsList;