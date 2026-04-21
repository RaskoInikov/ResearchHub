import { BrowserRouter, Routes, Route } from "react-router-dom";
import Sidebar from "./components/Sidebar.jsx";
import ArticlesPage from "./pages/ArticlesPage.jsx";
import UsersPage from "./pages/UsersPage.jsx";
import TagsPage from "./pages/TagsPage.jsx";

function App() {
  return (
    <BrowserRouter>
      <div className="container">
        <Sidebar />
        <div className="content">
          <Routes>
            <Route path="/" element={<ArticlesPage />} />
            <Route path="/users" element={<UsersPage />} />
            <Route path="/tags" element={<TagsPage />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;