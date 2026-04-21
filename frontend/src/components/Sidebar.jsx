import { NavLink } from "react-router-dom";

const Sidebar = () => {
  return (
    <aside className="sidebar">
      {/* LOGO / TITLE */}
      <div className="sidebar-header">
        <h2>ResearchHub</h2>
        <span className="subtitle">Knowledge Base</span>
      </div>

      {/* NAVIGATION */}
      <nav className="sidebar-nav">
        <NavLink to="/" className="nav-item">
          <span>📄</span>
          <span>Articles</span>
        </NavLink>

        <NavLink to="/users" className="nav-item">
          <span>👤</span>
          <span>Users</span>
        </NavLink>

        <NavLink to="/tags" className="nav-item">
          <span>🏷</span>
          <span>Tags</span>
        </NavLink>
      </nav>

      {/* FOOTER */}
      <div className="sidebar-footer">
        <span>v1.0</span>
      </div>
    </aside>
  );
};

export default Sidebar;