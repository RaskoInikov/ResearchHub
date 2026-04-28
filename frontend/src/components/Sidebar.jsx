import { NavLink } from "react-router-dom";

const Sidebar = () => {
  return (
    <aside
      style={{
        width: "240px",
        background: "#f0e9dc",
        padding: "20px",
        display: "flex",
        flexDirection: "column"
      }}
    >
      <div style={{ marginBottom: "20px" }}>
        <h3 style={{ margin: 0 }}>ResearchHub</h3>
        <small style={{ color: "#777" }}>Knowledge Base</small>
      </div>

      <nav style={{ display: "flex", flexDirection: "column", gap: "6px" }}>
        <NavLink to="/" className="nav-item">📄 Articles</NavLink>
        <NavLink to="/users" className="nav-item">👤 Users</NavLink>
        <NavLink to="/tags" className="nav-item">🏷 Tags</NavLink>
      </nav>

      <div style={{ marginTop: "auto", fontSize: "12px", color: "#888" }}>
        v1.0
      </div>
    </aside>
  );
};

export default Sidebar;